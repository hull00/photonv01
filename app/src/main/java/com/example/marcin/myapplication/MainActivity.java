package com.example.marcin.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    boolean connectionState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tekst na przycisku polaczenia z photonem
        final TextView connectionText = (TextView) findViewById(R.id.connectionText);


        //klikalny obszar layoutu - Adventure
        final RelativeLayout adventureButton = (RelativeLayout) findViewById(R.id.adventureLayout);
        adventureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdventureActivity.class);
                startActivity(intent);
            }
        });
        //zablokowanie adventure do czasu połączenia z Photonem
        this.disable(adventureButton);
        adventureButton.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 0));

        //klikalny obszar layoutu - PhotonConnection
        final RelativeLayout photonConnectionButton = (RelativeLayout) findViewById(R.id.photonConnectionLayout);
        photonConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Tutaj otworzy sie kolejne okno do polaczenia z Photonem", Toast.LENGTH_SHORT).show();
                if(connectionState){
                    disable(adventureButton);
                    adventureButton.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0f));
                    photonConnectionButton.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 66.67f));
                    connectionText.setText("Połącz z Photonem");
                }else {
                    


                    enable(adventureButton);
                    adventureButton.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 33.33f));
                    photonConnectionButton.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 33.33f));
                    connectionText.setText("Rozłącz z Photonem");
                }

                connectionState = !connectionState;
            }
        });
        //ustawienie szerokosci po starcie
        photonConnectionButton.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 66.67f));

        //klikalny obszar layoutu - Preferences
        RelativeLayout preferencesButton = (RelativeLayout) findViewById(R.id.preferencesLayout);
        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Tutaj otworzy sie kolejne okno do ustawień", Toast.LENGTH_SHORT).show();
            }
        });

        //klikalny obszar layoutu - Exit
        RelativeLayout exitButton = (RelativeLayout) findViewById(R.id.exitLayout);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }

    private void disable(RelativeLayout layout){
        layout.setEnabled(false);
    }

    private void enable(RelativeLayout layout){
        layout.setEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about_photon) {
            Toast.makeText(getApplicationContext(),"Tutaj pojawi się okno lub dialog o projekcie Photon", Toast.LENGTH_SHORT).show();
           return true;
        }

        if (id == R.id.action_about) {
            Toast.makeText(getApplicationContext(),"Tutaj pojawi się okno lub dialog o aplikacji", Toast.LENGTH_SHORT).show();
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
