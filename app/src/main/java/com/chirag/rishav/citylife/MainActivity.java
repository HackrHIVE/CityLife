package com.chirag.rishav.citylife;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    Toolbar mainToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        mAuth = FirebaseAuth.getInstance();
        mainToolbar = findViewById( R.id.MainActivityToolbar );
        BottomNavigationView botnav = findViewById(R.id.botom_nav);
        botnav.setOnNavigationItemSelectedListener(navListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homeFragment()).commit();

        setSupportActionBar( mainToolbar );
        getSupportActionBar().setTitle( "Home" );
        //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LoginColorPrimaryDark));
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedfragment=null;

            switch(menuItem.getItemId())
            {
                case R.id.home:
                    selectedfragment=new homeFragment();
                    break;
                    default:
                        Toast.makeText( MainActivity.this, "Yay! You clicked it", Toast.LENGTH_SHORT ).show();
                        //Daalna pada ,
                        break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
            return true;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            sendToLoginPage();
        }
    }

    private void sendToLoginPage() {
        Intent LoginIntent = new Intent( this,Login.class );
        startActivity( LoginIntent );
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.main_menu , menu);

        return true;
    }

    public void logout(){
        mAuth.signOut();
        sendToLoginPage();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutAcc:
                logout();
                return true;
            case R.id.user:
                Intent intent = new Intent( this,UserProfile.class );
                startActivity( intent );
                return true;
            default:
                return false;
        }
    }

    public void push(MenuItem item) {
        Intent i=new Intent(getApplicationContext(), UserProfile.class);
        startActivity(i);
    }
}
