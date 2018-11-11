package com.chirag.rishav.citylife;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerification extends AppCompatActivity {

    FirebaseAuth mAuth;
    ProgressBar verificationBar;
    Button gotoMain;
    TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_email_verification );
        verificationBar = findViewById( R.id.ProgressbarVerification );
        output = findViewById( R.id.output);
        gotoMain = findViewById( R.id.homecoming );
        mAuth = FirebaseAuth.getInstance();
    }

    public void SendVerificationEmail(View view) {

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(EmailVerification.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EmailVerification.this,
                                    task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void Recheck(View view) {
        mAuth.getCurrentUser().reload().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user.isEmailVerified()){
                    output.setText( "True" );
                    gotoMain.setVisibility( View.VISIBLE );
                }
                else
                if(!user.isEmailVerified()){
                    output.setText( "False" );
                }
            }
        });

    }

    public void GotoMain(View view) {
        Intent intent = new Intent( this,MainActivity.class );
        startActivity( intent );
    }
}
