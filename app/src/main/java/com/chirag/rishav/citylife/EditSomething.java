package com.chirag.rishav.citylife;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditSomething extends AppCompatActivity {

    ProgressBar ChangeProgressbar;
    String hint_edit,updatedDetail;
    TextInputEditText hintEdit,hintConfirm;
    TextInputLayout hintNewPassword,hintHEAD;
    FirebaseAuth mAuth;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        mAuth = FirebaseAuth.getInstance();
        setContentView( R.layout.activity_edit_something );
        hintEdit = findViewById( R.id.hint_NAME );
        hintNewPassword = findViewById( R.id.hint_TEMP );
        hintHEAD = findViewById( R.id.hint_HEAD );
        hintConfirm = findViewById( R.id.hint_TEMP_child );
        submit = findViewById( R.id.SubmitBTN );
        ChangeProgressbar = findViewById( R.id.EditSomethingProgressbar );
        hint_edit = getIntent().getStringExtra( "hint_name" );
        hintEdit.setHint( hint_edit );
        Toast.makeText( this, "hint_edit : "+hint_edit, Toast.LENGTH_SHORT ).show();
        if(hint_edit.equals( "Password" )){
            hintNewPassword.setVisibility( View.VISIBLE );
            hintEdit.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
            hintConfirm.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
            hintConfirm.setHint( "Confirm Password" );
            submit.setText( "Change Password" );
            hintHEAD.setPasswordVisibilityToggleEnabled( true );
            hintNewPassword.setPasswordVisibilityToggleEnabled( true );
        }
        if(hint_edit.equals( "Email" )){
            hintNewPassword.setVisibility( View.VISIBLE );
            hintConfirm.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
            hintConfirm.setHint( "Current Password" );
            hintNewPassword.setPasswordVisibilityToggleEnabled( true );
        }
    }

    public void gotoMain(){
        Intent intent = new Intent( this,MainActivity.class );
        startActivity( intent );
        finish();
    }

    public void ChangeIt(View view) {
        ChangeProgressbar.setVisibility( View.VISIBLE );
        updatedDetail = hintEdit.getText().toString();
        if(hint_edit.equals( "Name" )){
            FirebaseUser user = mAuth.getCurrentUser();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(updatedDetail).build();
            user.updateProfile( profileUpdates ).addOnCompleteListener( new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        ChangeProgressbar.setVisibility( View.GONE );
                        Toast.makeText( EditSomething.this, "Name updated!", Toast.LENGTH_SHORT ).show();
                        gotoMain();
                    }
                    else{
                        ChangeProgressbar.setVisibility( View.GONE );
                        Toast.makeText( EditSomething.this, task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                }
            } );
        }
        else
            if(hint_edit.equals( "Email" )){

                FirebaseUser user = mAuth.getCurrentUser();
                String currentPassword = hintConfirm.getText().toString();
                AuthCredential authCredential = EmailAuthProvider
                        .getCredential( user.getEmail(), currentPassword );
                user.reauthenticate( authCredential )
                        .addOnCompleteListener( new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                user1.updateEmail(updatedDetail)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    ChangeProgressbar.setVisibility( View.GONE );
                                                    Toast.makeText( EditSomething.this, "Email updated!", Toast.LENGTH_SHORT ).show();
                                                    gotoMain();
                                                }
                                                else {
                                                    ChangeProgressbar.setVisibility( View.GONE );
                                                    Toast.makeText( EditSomething.this, task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                                                }
                                            }
                                        });
                            }
                        } );
                ChangeProgressbar.setVisibility( View.GONE );
            }
            else
                if(hint_edit.equals( "Password" )){
                    String confirmPassword = hintConfirm.getText().toString();
                    if(!TextUtils.isEmpty( updatedDetail ) && !TextUtils.isEmpty( confirmPassword )){
                        if(updatedDetail.equals( confirmPassword )){
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.updatePassword( updatedDetail ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        ChangeProgressbar.setVisibility( View.GONE );
                                        Toast.makeText( EditSomething.this, "Password changed!", Toast.LENGTH_SHORT ).show();
                                        gotoMain();
                                    }
                                    else {
                                        ChangeProgressbar.setVisibility( View.GONE );
                                        Toast.makeText( EditSomething.this, task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            } );
                        }
                        else{
                            hintConfirm.setError( "Passwords don't match!" );
                        }
                    }
                    else{
                        if(TextUtils.isEmpty( updatedDetail )){
                            hintEdit.setError( "Empty" );
                        }
                        if(TextUtils.isEmpty( confirmPassword )){
                            hintConfirm.setError( "Empty" );
                        }
                    }
                    ChangeProgressbar.setVisibility( View.GONE );
                }
    }

}
