package com.chirag.rishav.citylife;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.TimeZone;

public class Signup extends AppCompatActivity{

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseAuth mAuth;
    private ProgressBar SignUpProgress;
    private TextInputEditText userName,userPhone,userEmailID,userPassword,userConfirmPassword,userDOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup );
        userName = findViewById( R.id.userName );
        userPhone = findViewById( R.id.userPhone );
        userEmailID = findViewById( R.id.userEmailID );
        userPassword = findViewById( R.id.userPassword );
        userConfirmPassword = findViewById( R.id.userConfirmPassword );
        userDOB = findViewById( R.id.userDOB );
        SignUpProgress = findViewById( R.id.SignUpProgressBar );
        mAuth = FirebaseAuth.getInstance();


        Calendar calendar = Calendar.getInstance( TimeZone.getDefault() );
        int Year = calendar.get( Calendar.YEAR );
        int Month = calendar.get( Calendar.MONTH );
        int Day = calendar.get( Calendar.DAY_OF_MONTH );

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                userDOB.setText( day+"/"+month+"/"+year );
            }
        };

        datePickerDialog = new DatePickerDialog( this , mDateSetListener , Year , Month , Day);

        userDOB.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        } );

    }

    public void gotoMain(){
        Intent gotoMain = new Intent( Signup.this,MainActivity.class );
        startActivity( gotoMain );
        finish();
    }

    public void RegisterUser(View view) {
        SignUpProgress.setVisibility( View.VISIBLE );
        String emailID = userEmailID.getText().toString();
        String userNameString = userName.getText().toString();
        String Password = userPassword.getText().toString();
        String ConfirmPassword = userConfirmPassword.getText().toString();
        String Phone = userPhone.getText().toString();
        String DOB = userDOB.getText().toString();

        if(!TextUtils.isEmpty( emailID ) && !TextUtils.isEmpty( userNameString ) && !TextUtils.isEmpty( Password ) && !TextUtils.isEmpty( ConfirmPassword ) && !TextUtils.isEmpty( Phone ) && !TextUtils.isEmpty( DOB )){
            if(!Password.equals( ConfirmPassword )){
                //Password doesn't match with Confirm Password
                CharSequence errorMessage = "Doesn't Match";
                userConfirmPassword.setError( errorMessage );
                SignUpProgress.setVisibility( View.GONE );
            }
            else{
                //Everything Alright
                mAuth.createUserWithEmailAndPassword( emailID , Password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            gotoMain();
                            SignUpProgress.setVisibility( View.GONE );
                        }
                        else {
                            String error = task.getException().getMessage();
                            Toast.makeText( Signup.this, "Error : "+error, Toast.LENGTH_SHORT ).show();
                            SignUpProgress.setVisibility( View.GONE );
                        }
                    }
                } );

            }
        }
        else {
            Toast.makeText( this, "Fill all the details", Toast.LENGTH_SHORT ).show();
        }
    }


}
