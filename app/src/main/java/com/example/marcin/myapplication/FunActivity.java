package com.example.marcin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class FunActivity extends Activity implements SensorEventListener{

    private RelativeLayout ForwardButton;
    private RelativeLayout BackwardButton;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);


        ForwardButton = (RelativeLayout) findViewById(R.id.ForwardLayout);
        ForwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Photon.setForward();
                   v.setBackgroundColor(0xFF7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xff6415a1);
                    Photon.setStop();
                }

                return true;
            }
        });

        BackwardButton = (RelativeLayout) findViewById(R.id.BackwardLayout);
        BackwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Photon.setBackward();
                    v.setBackgroundColor(0xFF7A0223);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xff7a388e);
                    Photon.setStop();
                }

                return true;
            }
        });

        RelativeLayout HitButton = (RelativeLayout) findViewById(R.id.HitLayout);
        HitButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                   v.setBackgroundColor(0xFF7A0223);
                    Log.i("Hit", "Down");

                SendToPhoton("r");

                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(0xFF432453);
                }

                return true;
            }
        });

        if(!moveManager.isAlive()){
            moveManager.start();
        }
    }

    private void SendToPhoton(String string){
        if(MainActivity.getBluetoothService().getState()) {
            MainActivity.getBluetoothService().send(string);
        } else{
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(moveManager.isAlive())
            moveManager.interrupt();
        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float azimuth_angle = event.values[0];
        float pitch_angle = event.values[1];
        float roll_angle = event.values[2];
        // Do something with these orientation angles.


            if (pitch_angle < -15) {
                Photon.setRight();
            } else if(pitch_angle > 15){
                Photon.setLeft();
            } else {
                Photon.setAhead();
            }


        //Log.i("Fun", "Azimuth: "+azimuth_angle + " Pitch: "+pitch_angle+" Roll: "+roll_angle);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        // You must implement this callback in your code.
    }

    Thread moveManager = new Thread() {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                    String photonMessage = null;

                    if(Photon.isForward()){
                        if(Photon.isRight()){
                            photonMessage = "a";
                        } else if (Photon.isLeft()){
                            photonMessage = "d";
                        } else {
                            photonMessage = "w";
                        }
                    } else if (Photon.isBackward()) {
                            photonMessage = "x";
                    } else {
                        if(Photon.isRight()){
                            photonMessage = "q";
                        } else if (Photon.isLeft()){
                            photonMessage = "e";
                        } else {
                            photonMessage = "s";
                        }
                    }

                    if(MainActivity.getBluetoothService().getState()){
                        if(photonMessage != null)
                        MainActivity.getBluetoothService().send(photonMessage);
                        else
                            Log.e("PhotonMove", "Non reaction");
                    }

                } catch (InterruptedException e) {
                }


            }
        }
    };
}
