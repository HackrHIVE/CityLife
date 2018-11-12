package com.chirag.rishav.citylife;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Signup extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private TextInputEditText userEmailID,userPassword,userConfirmPassword;
    private ProgressBar SignUpProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup );
        userEmailID = findViewById( R.id.userEmailID );
        userPassword = findViewById( R.id.userPassword );
        userConfirmPassword = findViewById( R.id.userConfirmPassword );
        //userDOB = findViewById( R.id.userDOB );
        mAuth = FirebaseAuth.getInstance();
        SignUpProgress = findViewById( R.id.SignUpProgressBar );

    }

    public void Continue(View view) {
        SignUpProgress.setVisibility( View.VISIBLE );
        final String emailID = userEmailID.getText().toString();
        final String Password = userPassword.getText().toString();
        String ConfirmPassword = userConfirmPassword.getText().toString();

        if (!TextUtils.isEmpty( emailID ) && !TextUtils.isEmpty( Password ) && !TextUtils.isEmpty( ConfirmPassword )) {
            if (Password.equals( ConfirmPassword )) {

                mAuth.createUserWithEmailAndPassword( emailID, Password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent continueToSecondPage = new Intent( Signup.this, SignupContinued.class );
                            continueToSecondPage.putExtra( "emailID", emailID );
                            continueToSecondPage.putExtra( "Password", Password );
                            startActivity( continueToSecondPage );
                            finish();
                            SignUpProgress.setVisibility( View.GONE );
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText( Signup.this, "Error : " + error, Toast.LENGTH_SHORT ).show();
                            SignUpProgress.setVisibility( View.GONE );
                        }
                    }
                } );


            } else {
                Toast.makeText( this, "Passwords not matching!", Toast.LENGTH_SHORT ).show();
                SignUpProgress.setVisibility( View.GONE );
            }
        } else {
            Toast.makeText( this, "Fill all the details!", Toast.LENGTH_SHORT ).show();
            SignUpProgress.setVisibility( View.GONE );
        }
    }



}
