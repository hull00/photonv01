package com.example.marcin.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class CompetitionActivity extends Activity {

    LinearLayout DistanceAttackButton;
    RelativeLayout CompetitonForwardButton, CompetitonBackwardButton, CompetitionHitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

        DistanceAttackButton = (LinearLayout) findViewById(R.id.DistanceHitLayout);
        DistanceAttackButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundColor(0xFF7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xff302853);
                }

                return true;
            }
        });


        CompetitonForwardButton = (RelativeLayout) findViewById(R.id.CompetitionForwardLayout);
        CompetitonForwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    MainActivity.getBluetoothService().send("w");
                    v.setBackgroundColor(0xFF7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xff9ea119);
                    MainActivity.getBluetoothService().send("s");
                }

                return true;
            }
        });


        CompetitonBackwardButton = (RelativeLayout) findViewById(R.id.CompetitionBackwardLayout);
        CompetitonBackwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    MainActivity.getBluetoothService().send("x");
                    v.setBackgroundColor(0xFF7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    MainActivity.getBluetoothService().send("s");
                    v.setBackgroundColor(0xff148b8e);
                }

                return true;
            }
        });


        CompetitionHitButton= (RelativeLayout) findViewById(R.id.CompetitionHitLayout);
        CompetitionHitButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    MainActivity.getBluetoothService().send("r");
                    v.setBackgroundColor(0xFF7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xffa1135b);
                }

                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.competition, menu);



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
