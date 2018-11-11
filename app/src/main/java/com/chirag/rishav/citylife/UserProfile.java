package com.chirag.rishav.citylife;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import javax.annotation.Nullable;

public class UserProfile extends AppCompatActivity {

    TextView emailuserProfile,nameProfile,phoneProfile,date_birthProfile;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    TextInputLayout currentPasswordTextInput;
    Button changePassword,continueChangePassword,cancelOperation;
    TextInputEditText currentPasswordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user_profile );
        mAuth = FirebaseAuth.getInstance();
        emailuserProfile = findViewById( R.id.userProfileEmail );
        nameProfile = findViewById( R.id.name );
        phoneProfile = findViewById( R.id.phone );
        date_birthProfile = findViewById( R.id.dob );
        changePassword= findViewById( R.id.change_password_btn );
        continueChangePassword = findViewById( R.id.change_password_btn_continue );
        currentPasswordTextInput = findViewById( R.id.currentPasswordTextInput );
        currentPasswordEditText = findViewById( R.id.currentPassword );
        cancelOperation = findViewById( R.id.cancel_action );
        db = FirebaseFirestore.getInstance();
        //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.WhiteColorAccentAlt));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.botnav , menu);

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){

            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .build();
            db.setFirestoreSettings(settings);

            db.collection( "users" ).document(firebaseUser.getEmail()).addSnapshotListener( new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                    if( documentSnapshot.getString( "email" ).equals( firebaseUser.getEmail() ) ){
                        String username = documentSnapshot.getString( "name" );
                        String phone = documentSnapshot.getString( "phone" );
                        String email = documentSnapshot.getString( "email" );
                        String date_birth = documentSnapshot.getString( "date_birth" );
                        emailuserProfile.setText( email );
                        nameProfile.setText( username );
                        phoneProfile.setText( phone );
                        date_birthProfile.setText( date_birth );
                    }
                }
            } );

        }
    }

    public void EditDateofBirth(View view) {
        Toast.makeText( this, "DOB Clicked", Toast.LENGTH_SHORT ).show();
        //new Activity will be launched to edit DOB
    }


    public void EditEmail(View view) {
        Intent JustEditIt = new Intent( this,EditSomething.class );
        JustEditIt.putExtra( "hint_name" , "Email");
        startActivity(JustEditIt);
        finish();
        Toast.makeText( this, "Email Clicked", Toast.LENGTH_SHORT ).show();
        //new Activity will be launched to edit Email
    }

    public void EditName(View view) {
        Intent JustEditIt = new Intent( this,EditSomething.class );
        JustEditIt.putExtra( "hint_name" , "Name");
        startActivity(JustEditIt);
        finish();
        Toast.makeText( this, "Name Clicked", Toast.LENGTH_SHORT ).show();
        //new Activity will be launched to edit Name
    }

    public void EditPhone(View view) {
        Toast.makeText( this, "Phone Clicked", Toast.LENGTH_SHORT ).show();
        //new Activity will be launched to edit Phone
    }

    public void goBack(View view) {
        Toast.makeText( this, "Go BACK", Toast.LENGTH_SHORT ).show();
        onBackPressed();
    }

    public void EditDP(View view) {
        Toast.makeText( this, "DP Clicked", Toast.LENGTH_SHORT ).show();
    }

    public void ChangePassword(View view) {
        changePassword.setVisibility( View.GONE );
        cancelOperation.setVisibility( View.VISIBLE );
        currentPasswordEditText.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        currentPasswordTextInput.setPasswordVisibilityToggleEnabled( true );
        currentPasswordTextInput.setVisibility( View.VISIBLE );
        continueChangePassword.setVisibility( View.VISIBLE );
    }

    public void ContinueFurtherForChangePassword(View view) {
        String currentPassword = currentPasswordEditText.getText().toString();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            String email = firebaseUser.getEmail();
            mAuth.signInWithEmailAndPassword( email , currentPassword ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText( UserProfile.this, "Password Verified!", Toast.LENGTH_SHORT ).show();
                        Intent JustEditIt = new Intent( UserProfile.this,EditSomething.class );
                        JustEditIt.putExtra( "hint_name" , "Password");
                        startActivity(JustEditIt);
                        finish();
                    }
                    else{
                        Toast.makeText( UserProfile.this, task.getException().toString(), Toast.LENGTH_SHORT ).show();
                    }
                }
            } );
        }
    }

    public void CancelChangePassword(View view) {
        changePassword.setVisibility( View.VISIBLE );
        cancelOperation.setVisibility( View.GONE );
        currentPasswordTextInput.setVisibility( View.GONE );
        continueChangePassword.setVisibility( View.GONE );
    }
}
