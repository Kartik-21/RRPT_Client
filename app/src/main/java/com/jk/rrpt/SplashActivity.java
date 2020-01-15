package com.jk.rrpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.jk.rrpt.login_pages.SignInActivity;

public class SplashActivity extends AppCompatActivity {

    private static int TIME = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME);

    }


    @Override
    protected void onStart() {
        super.onStart();
        cheakConnection();
    }


    protected boolean isOnline() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void cheakConnection() {

        if (isOnline()) {
          //  Toast.makeText(getApplicationContext(), "You are conneted to Internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Network error..", Toast.LENGTH_SHORT).show();
        }

    }

}
