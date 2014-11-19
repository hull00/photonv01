package com.example.marcin.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    /*
        zmienne potrzebne do Bluetooth
    */
    static final int REQUEST_BLUETOOTH_CONNECT = 1;     //stała dla uruchomienia Bluetooth
    BluetoothAdapter myBluetoothAdapter;    //adapter Bluetooth
    public static BluetoothService myBluetoothService;     // instancja Service'u do oblusgi połączenia bluetooth
    protected Boolean isBound = false;  // zmienna okreslajaca czy dane Activity jest "spięte" z Servicem


    //zmienne widoku
    RelativeLayout adventureButton;
    RelativeLayout photonConnectionButton;
    TextView connectionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tekst na przycisku polaczenia z photonem
        connectionText = (TextView) findViewById(R.id.connectionText);

        /*
            KWADRAT ADVENTURE
        */
        //region ADVENTURE

        adventureButton = (RelativeLayout) findViewById(R.id.adventureLayout); //klikalny obszar layoutu - Adventure
        adventureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdventureActivity.class);
                startActivity(intent);
            }
        });

        //endregion ADVENTURE

        /*
            KWADRAT PHOTON CONNECTION
         */
        //region PHOTON_CONNECTION


        photonConnectionButton = (RelativeLayout) findViewById(R.id.photonConnectionLayout); //klikalny obszar layoutu - PhotonConnection
        photonConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isBound) {

                    myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                    // urządzenie nie obsługuje Bluetooth
                    if (myBluetoothAdapter == null) {
                        Toast.makeText(getApplicationContext(), "Twoje urządzenie nie obsługuje Bluetooth.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    // urządzenie obsługuje Bluetooth ale nie jest wlaczony
                    if (!myBluetoothAdapter.isEnabled()) {
                        Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBluetoothIntent, REQUEST_BLUETOOTH_CONNECT);
                    } else {
                        startActivity(new Intent(getApplicationContext(), PhotonDeviceListActivity.class));
                    }

                } else {
                    // jezeli service jest spiety z naszym activity to go rozpinamy
                    //myBluetoothService.disconnect();
                    unbindService(myServiceConnection);
                    isBound = false;

                    disable(adventureButton);
                    CoreDevice.activeDevice = null;
                }
            }
        });

        //endregion PHOTON_CONNECTION


        /*
            KWADRAT PREFERENCES
         */
        //region PREFERENCES

        RelativeLayout preferencesButton = (RelativeLayout) findViewById(R.id.preferencesLayout); //klikalny obszar layoutu - Preferences
        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Tutaj otworzy sie kolejne okno do ustawień", Toast.LENGTH_SHORT).show();
            }
        });

        //endregion PREFERENCES

        /*
            KWADRAT EXIT
         */
        //region EXIT

        RelativeLayout exitButton = (RelativeLayout) findViewById(R.id.exitLayout); //klikalny obszar layoutu - Exit
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

        //endregion EXIT

        //zablokowanie adventure do czasu połączenia z Photonem
        this.disable(adventureButton);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            if (CoreDevice.activeDevice == null) {

            } else {
                //jeżeli obsługuje utwórz Service
                Intent intent = new Intent(MainActivity.this, BluetoothService.class);
                bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE);

                myBluetoothService.connect();

                //odblokowanie przycisku Adventure
                this.enable(adventureButton);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_BLUETOOTH_CONNECT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Moduł Bluetooth został włączony", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), PhotonDeviceListActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Moduł Bluetooth nie został włączony", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "Tutaj pojawi się okno lub dialog o projekcie Photon", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_about) {
            Toast.makeText(getApplicationContext(), "Tutaj pojawi się okno lub dialog o aplikacji", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static BluetoothService getBluetoothService() {
        return myBluetoothService;
    }

    private ServiceConnection myServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }

        @Override
        // automatyczna metoda wywoływana przez system, aby dostarczyć nam
        // IBinder z serwisu
        public void onServiceConnected(ComponentName name, IBinder service) {
            BluetoothService.ServiceBinder binder = (BluetoothService.ServiceBinder) service;
            myBluetoothService = binder.getService();
            isBound = true;
        }
    };

    //region ENABLE_DISABLE_ADVENTURE

    private void disable(RelativeLayout layout) {
        layout.setEnabled(false);
        layout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0f));
        photonConnectionButton.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 66.67f));
        connectionText.setText("Połącz z Photonem");
    }

    private void enable(RelativeLayout layout) {
        layout.setEnabled(true);
        layout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 33.33f));
        photonConnectionButton.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 33.33f));

        connectionText.setText("Rozłącz z Photonem"); //przypisanie nowego tekstu
    }

    //endregion



}
