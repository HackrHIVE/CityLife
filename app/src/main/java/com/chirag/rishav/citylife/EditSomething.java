package com.chirag.rishav.citylife;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditSomething extends AppCompatActivity {

    String hint_edit,updatedDetail;
    TextInputEditText hintEdit;
    FirebaseAuth mAuth;
    TextInputLayout hintHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        mAuth = FirebaseAuth.getInstance();
        setContentView( R.layout.activity_edit_something );
        hintEdit = findViewById( R.id.hint_NAME );
        hint_edit = getIntent().getStringExtra( "hint_name" );
        hintEdit.setHint( hint_edit );

    }

    public void ChangeIt(View view) {
        updatedDetail = hintEdit.getText().toString();
        if(hint_edit.equals( "Name" )){
            FirebaseUser user = mAuth.getCurrentUser();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(updatedDetail).build();
            user.updateProfile( profileUpdates );
        }
        else
            if(hint_edit.equals( "emailID" )){
            FirebaseUser user = mAuth.getCurrentUser();
            user.updateEmail( updatedDetail );
            }
    }
}
