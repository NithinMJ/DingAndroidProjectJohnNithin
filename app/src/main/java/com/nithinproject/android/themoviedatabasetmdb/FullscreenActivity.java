
package com.nithinproject.android.themoviedatabasetmdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/*Developed by Nithin John*/

public class FullscreenActivity extends Activity {
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Code to not display Title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen);

        loadPage();
    }

    //Check if Internet is available on App open and display toast if no internet connectivity
    //Moves to MainActivity only when internet is available
    protected void loadPage(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(!isNetworkAvailable()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getBaseContext(),"No Internet Connectivity. Please make sure that you are connected to the internet",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Intent in=new Intent(FullscreenActivity.this,MainActivity.class);
                            startActivity(in);
                            check = false;
                        }
                    });
                }
                if(check) {
                    handler.postDelayed(this, 3000);
                }

            }

        }, 2000);
    }

    //Handling onRestart
    @Override
    protected void onRestart() {
        loadPage();
        super.onRestart();

    }

    //Handling onResume
    @Override
    protected void onResume(){
        loadPage();
        super.onResume();
    }

    //Method to check Internet Connectivity
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
