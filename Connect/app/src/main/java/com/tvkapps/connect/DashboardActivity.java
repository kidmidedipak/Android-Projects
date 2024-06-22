package com.tvkapps.connect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tvkapps.connect.fragment.ChatListFragment;
import com.tvkapps.connect.fragment.HomeFragment;
import com.tvkapps.connect.fragment.NotificationsFragment;
import com.tvkapps.connect.fragment.ProfileFragment;
import com.tvkapps.connect.fragment.UsersFragment;
import com.tvkapps.connect.notification.Token;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    String mUID;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Profile");
        }

        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                }




                if (id == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }


                if (id == R.id.nav_users) {
                    selectedFragment = new UsersFragment();
                }
                if (id == R.id.nav_chat) {
                    selectedFragment = new ChatListFragment();
                }

                /*
                if (id == R.id.nav_notiification) {
                    selectedFragment = new NotificationsFragment();
                }



                 */
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                return true;
            }
        });

        // Set the default selected fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new HomeFragment()).commit();


        checkUserStatus();

      /*  //update token
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("FCM token", "Fetching FCM registration token failed", task.getException());
                return;
            }

            // Get new FCM registration token
            String token = task.getResult();
            Log.d("FCM token", "FCM Registration token: " + token);

            // Update token
//            updateToken(token);
        });

        */

    }

    public  void updateToken(String token){
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken=new Token(token);
        ref.child(mUID).setValue(mToken);
    }

    @Override
    protected void onResume() {
        checkUserStatus();
        super.onResume();
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){

        mUID=user.getUid();

            SharedPreferences sp=getSharedPreferences("SP_USER",MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("Current_USERID",mUID);
            editor.apply();


            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.w("FCM token", "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Get new FCM registration token
                String token = task.getResult();
                Log.d("FCM token", "FCM Registration token: " + token);

                // Update token
//            updateToken(token);
            });







        }else {
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }





    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }

        return super.onOptionsItemSelected(item);
    }
  */


}