package com.example.marcin.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
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

    //zmienne
    BluetoothAdapter myBluetoothAdapter;
    static final int REQUEST_BLUETOOTH_CONNECT = 1;
    boolean connectionState = false;
    RelativeLayout adventureButton;
    RelativeLayout photonConnectionButton;
    TextView connectionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tekst na przycisku polaczenia z photonem
        connectionText = (TextView) findViewById(R.id.connectionText);

        //klikalny obszar layoutu - Adventure
        adventureButton = (RelativeLayout) findViewById(R.id.adventureLayout);
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
        photonConnectionButton = (RelativeLayout) findViewById(R.id.photonConnectionLayout);
        photonConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(connectionState){
                    disable(adventureButton);
                    adventureButton.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0f));
                    photonConnectionButton.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 66.67f));
                    connectionText.setText("Połącz z Photonem");
                    CoreDevice.activeDevice = null;
                }else {

                    myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                    // urządzenie nie obsługuje Bluetooth
                    if(myBluetoothAdapter == null){
                       Toast.makeText(getApplicationContext(),"Twoje urządzenie nie obsługuje Bluetooth.", Toast.LENGTH_LONG).show();
                        return;
                    }


                    // urządzenie obsługuje Bluetooth ale nie jest wlaczony
                    if(!myBluetoothAdapter.isEnabled()){
                        Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBluetoothIntent, REQUEST_BLUETOOTH_CONNECT);
                    } else {
                        startActivity(new Intent(getApplicationContext(), PhotonDeviceListActivity.class));
                    }
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
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // Add the buttons
                builder.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                // 2. Chain together various setter methods to set the dialog
                // characteristics
                builder.setMessage("Czy chcesz opuścić aplikację?").setTitle(
                        "Wyjście z aplikacji");

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();

                dialog.show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_BLUETOOTH_CONNECT){
            if(resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(),"Moduł Bluetooth został włączony", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), PhotonDeviceListActivity.class));
            } else {
                Toast.makeText(getApplicationContext(),"Moduł Bluetooth nie został włączony", Toast.LENGTH_SHORT).show();
            }
        }
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
    protected void onResume() {
        super.onResume();

        try {
            if (CoreDevice.activeDevice == null) {

            } else {

                enable(adventureButton);
                adventureButton.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 33.33f));
                photonConnectionButton.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 33.33f));
                connectionText.setText("Rozłącz z Photonem");

                /*if (myBluetoothService.getState())
                    Log.i("onResume", "Połączony");
                else {
                    Log.i("onResume", "Niepołączony2");
                    myBluetoothService.connect();
                */

                }

        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Add the buttons
        builder.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("NIE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // 2. Chain together various setter methods to set the dialog
        // characteristics
        builder.setMessage("Czy chcesz opuścić aplikację?").setTitle(
                "Wyjście z aplikacji");

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
