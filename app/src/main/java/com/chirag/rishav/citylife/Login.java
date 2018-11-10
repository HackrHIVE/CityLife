package com.chirag.rishav.citylife;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

   // private TextView CityLifeTitle;
    private EditText email,userPass;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        //CityLifeTitle = findViewById( R.id.CityLifeTitle );
        mAuth = FirebaseAuth.getInstance();
        email = findViewById( R.id.emailUser );
        userPass = findViewById( R.id.userPass );
        loginProgress = findViewById( R.id.loginProgress );
        //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.YellowColorAccent));
        }
    }

    public void gotoMain(){
        Intent gotoMain = new Intent( Login.this,MainActivity.class );
        startActivity( gotoMain );
        finish();
    }

    public void LoginUser(View view) {
        String emailID = email.getText().toString();
        String userPassword = userPass.getText().toString();

        if(!TextUtils.isEmpty( emailID ) && !TextUtils.isEmpty( userPassword )){
            loginProgress.setVisibility( View.VISIBLE );
            mAuth.signInWithEmailAndPassword( emailID,userPassword ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        gotoMain();
                    }
                    else{
                        String e = task.getException().getMessage();
                        Toast.makeText( Login.this, "Error : "+e, Toast.LENGTH_SHORT ).show();
                        loginProgress.setVisibility( View.INVISIBLE );
                    }
                }
            } );

        }
        else{
            Toast.makeText( this, "Fill Details", Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null){

            gotoMain();

        }
    }

    public void SignupUser(View view) {
        Intent gotoSignUpScreen = new Intent( this,Signup.class );
        startActivity( gotoSignUpScreen );
    }
}
