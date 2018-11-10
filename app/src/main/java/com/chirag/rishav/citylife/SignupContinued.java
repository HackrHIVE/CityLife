package com.chirag.rishav.citylife;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Calendar;
import java.util.TimeZone;

public class SignupContinued extends AppCompatActivity {


    private ProgressBar continueProgressBar;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextInputEditText userName,userPhone,userDOB;
    private FirebaseAuth mAuth;


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
        String userNameString = userName.getText().toString();
        String userPhoneString = userPhone.getText().toString();
        String userDOBString = userDOB.getText().toString();

        FirebaseUser user = mAuth.getCurrentUser();


        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userNameString).build();
        user.updateProfile( profileUpdates );

        Intent gotoMain = new Intent( this,MainActivity.class );
        startActivity(gotoMain);
        finish();
    }
}
