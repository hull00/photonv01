package com.example.marcin.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class FunActivity extends Activity {
    RelativeLayout ForwardButton;
    RelativeLayout BackwardButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun);

        ForwardButton = (RelativeLayout) findViewById(R.id.ForwardLayout);
        ForwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(BackwardButton.isPressed()){
                        Toast.makeText(getApplicationContext(),"Chyba nie srałeś, nie wciskaj dwóch na raz", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    v.setBackgroundColor(0x7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xff6415a1);
                }

                return false;
            }
        });

        BackwardButton = (RelativeLayout) findViewById(R.id.BackwardLayout);
        BackwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(ForwardButton.isPressed()){
                        Toast.makeText(getApplicationContext(),"Chyba nie srałeś, nie wciskaj dwóch na raz", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    v.setBackgroundColor(0x7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xff7a388e);
                }

                return false;
            }
        });

        RelativeLayout HitButton = (RelativeLayout) findViewById(R.id.HitLayout);
        HitButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                   v.setBackgroundColor(0x7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0x432453);
                }

                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
