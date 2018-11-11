package com.chirag.rishav.citylife;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class SignupContinued extends AppCompatActivity {


    private FirebaseFirestore firebaseFirestore;
    private ProgressBar continueProgressBar;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextInputEditText userName,userPhone,userDOB;
    private FirebaseAuth mAuth;
    private String emailByIntent,passwordByIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup_continued );

        mAuth = FirebaseAuth.getInstance();
        userName = findViewById( R.id.userName );
        userPhone = findViewById( R.id.userPhone );
        userDOB = findViewById( R.id.userDOB );
        continueProgressBar = findViewById( R.id.SignUpProgressBarContinue );
        firebaseFirestore = FirebaseFirestore.getInstance();
        emailByIntent = getIntent().getStringExtra( "emailID" );
        passwordByIntent = getIntent().getStringExtra( "Password" );

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

    public void ContinueFurther(View view) {
        continueProgressBar.setVisibility( View.VISIBLE );
        String userNameString = userName.getText().toString();
        String userPhoneString = userPhone.getText().toString();
        String userDOBString = userDOB.getText().toString();

        if(!TextUtils.isEmpty( userNameString ) && !TextUtils.isEmpty( userPhoneString ) && !TextUtils.isEmpty( userDOBString )){

            FirebaseUser user = mAuth.getCurrentUser();

            Map<String,String> userData = new HashMap<>(  );
            userData.put( "name" , userNameString );
            userData.put( "email" , emailByIntent );
            userData.put( "password" , passwordByIntent);
            userData.put( "date_birth" , userDOBString );
            userData.put( "phone" , userPhoneString );

            firebaseFirestore.collection( "users" ).document(user.getEmail()).set( userData ).addOnCompleteListener( new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                        Toast.makeText( SignupContinued.this, "Data stored at our side!", Toast.LENGTH_SHORT ).show();
                    else
                        Toast.makeText( SignupContinued.this, task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                }
            } );

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(userNameString).build();
            user.updateProfile( profileUpdates ).addOnCompleteListener( new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        continueProgressBar.setVisibility( View.GONE );
                        Intent gotoMain = new Intent( SignupContinued.this,MainActivity.class );
                        startActivity(gotoMain);
                        finish();
                    }
                    else{
                        continueProgressBar.setVisibility( View.GONE );
                        Toast.makeText( SignupContinued.this, task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                }
            } );


        }
        else{
            continueProgressBar.setVisibility( View.GONE );
            Toast.makeText( this, "Fill Details", Toast.LENGTH_SHORT ).show();
        }
    }
}
