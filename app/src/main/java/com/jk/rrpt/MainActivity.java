package com.jk.rrpt;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.jk.rrpt.login_pages.SignInActivity;
import com.jk.rrpt.navi_drawer_items.AboutActivity;
import com.jk.rrpt.navi_drawer_items.FavouriteActivity;
import com.jk.rrpt.navi_drawer_items.HomeActivity;
import com.jk.rrpt.navi_drawer_items.NotificationActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private TextView user_name;
    private TextView user_email;

    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_l_r);
        //drawerLayout.setAnimation(animation);

        navigationView.setNavigationItemSelectedListener(this);

        //Animation animation= AnimationUtils(getApplicationContext(),R.anim.fade_l_r);

        //Log.w(TAG, "fragment");

        displayFragment(R.id.nav_home);

        user_email = (TextView) findViewById(R.id.user_email);
        user_name = (TextView) findViewById(R.id.user_name);

        firebaseAuth = FirebaseAuth.getInstance();
        //this is where we start the Auth state Listener to listen for whether the user is signed in or not
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Get signedIn user
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //if user is signed in, we call a helper method to save the user details to Firebase
                if (user == null) {
                    Intent i = new Intent(MainActivity.this, SignInActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    // Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_exit:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                finish();
                break;


            //TODO(): Add more cases on requirements
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayFragment(int id) {
        Fragment f = null;

        switch (id) {
            case R.id.nav_home:
                f = new HomeActivity();
                break;
            case R.id.nav_notification:
                f = new NotificationActivity();
                break;
            case R.id.nav_favourite:
                f = new FavouriteActivity();
                break;
            case R.id.nav_about:
                f = new AboutActivity();
                break;
            case R.id.nav_share:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String body = "this is RRPT application";
                String sub = "subject";
                i.putExtra(Intent.EXTRA_TEXT, body);
                i.putExtra(Intent.EXTRA_SUBJECT, sub);
                startActivity(Intent.createChooser(i, "Share using"));
                break;

            case R.id.nav_rate_us:
                Toast.makeText(getApplicationContext(), "rate us", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                break;

        }
        if (f != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //   ft.setCustomAnimations(R.anim.fade_l_r,R.anim.fade_r_l);
            ft.replace(R.id.frames_layout, f);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayFragment(item.getItemId());

        return true;
    }

}



