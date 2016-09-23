package com.intumwayase.android.silverback;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {

            FrameLayout noInternetLayout = (FrameLayout) findViewById(R.id.main_container);
            TextView noInternetTextView = new TextView(MainActivity.this);

            noInternetTextView.setText("No Internet connection. Please connect your device.");
            noInternetLayout.addView(noInternetTextView);

        }

        SilverbackFragment silverbackFragment = new SilverbackFragment();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, silverbackFragment)
                    .commit();
        }
    }
}
