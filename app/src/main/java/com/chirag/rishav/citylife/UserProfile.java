package com.chirag.rishav.citylife;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfile extends AppCompatActivity {

    TextView emailuserProfile,nameProfile;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user_profile );
        mAuth = FirebaseAuth.getInstance();
        emailuserProfile = findViewById( R.id.userProfileEmail );
        nameProfile = findViewById( R.id.name );
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
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            String email = firebaseUser.getEmail();
            emailuserProfile.setText( email );
            String name = firebaseUser.getDisplayName();
            nameProfile.setText( name );
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


}
