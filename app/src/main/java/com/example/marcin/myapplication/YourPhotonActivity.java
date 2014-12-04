package com.example.marcin.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


public class YourPhotonActivity extends Activity {

    private RelativeLayout WhiteButton, RedButton, YellowButton, GreenButton, BlueButton, PurpleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_photon);


        //region Deklaracja przyciskow i ich akcji
        WhiteButton = (RelativeLayout) findViewById(R.id.WhiteLayout);
        WhiteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    SendToPhoton("G");
                    v.setBackgroundColor(0xFF7A0223);
                } else {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.setBackgroundColor(getResources().getColor(android.R.color.white));

                    }
                }

                return true;
            }
        });
        RedButton = (RelativeLayout) findViewById(R.id.RedLayout);
        RedButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    SendToPhoton("H");
                    v.setBackgroundColor(0xFF7A0223);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xffa12616);

                }

                return true;
            }
        });
        YellowButton = (RelativeLayout) findViewById(R.id.YellowLayout);
        YellowButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    SendToPhoton("I");
                    v.setBackgroundColor(0xFF7A0223);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xffe8c52b);

                }

                return true;
            }
        });
        GreenButton = (RelativeLayout) findViewById(R.id.GreenLayout);
        GreenButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    SendToPhoton("J");
                    v.setBackgroundColor(0xFF7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xff35a116);

                }

                return true;
            }
        });
        BlueButton = (RelativeLayout) findViewById(R.id.BlueLayout);
        BlueButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    SendToPhoton("K");
                    v.setBackgroundColor(0xFF7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xff2429a1);

                }

                return true;
            }
        });
        PurpleButton = (RelativeLayout) findViewById(R.id.PurpleLayout);
        PurpleButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    SendToPhoton("L");
                    v.setBackgroundColor(0xFF7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xff6415a1);

                }

                return true;
            }
        });
        //endregion

    }

    private void SendToPhoton(String string){
        if(MainActivity.getBluetoothService().getState()) {
            MainActivity.getBluetoothService().send(string);
        } else{
            return;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.your_photon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
