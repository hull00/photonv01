package com.example.marcin.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;


public class PhotonDeviceListActivity extends Activity {

    private ArrayList<BluetoothDevice> pairedBluetoothDevicesList = new ArrayList<BluetoothDevice>();
    private ArrayList<BluetoothDevice> discoverBluetoothDevicesList = new ArrayList<BluetoothDevice>();
    private ArrayList<DiscoverBTDevice> discoverViewBluetoothDeviceList = new ArrayList<DiscoverBTDevice>();
    private ListView photonDeviceListView;
    private TextView findText, stateDiscoverLabel;
    private RelativeLayout findButton;
    private IntentFilter filter;
    private BluetoothAdapter myBluetoothAdapter;
    ArrayAdapter<DiscoverBTDevice> deviceListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photon_device_list);
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        photonDeviceListView = (ListView) findViewById(R.id.photonDeviceListView);

        findButton = (RelativeLayout) findViewById(R.id.findLayout);
        RelativeLayout exitButton = (RelativeLayout) findViewById(R.id.exitBluetoothLayout);

        stateDiscoverLabel = (TextView) findViewById(R.id.stateDiscoverLabel);
        findText = (TextView) findViewById(R.id.findText);

        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findButton.setEnabled(false);
                //wysylamy rozgloszenie z akcja ACTION_FOUND, ktore odbiera BroadcastReceiver - mReceiver
                registerReceiver(mReceiver, filter);

                //czyscimy liste urzadzen i rozpoczynamy na nowo
                discoverBluetoothDevicesList.clear();
                ArrayAdapter<BluetoothDevice> adapter = new ArrayAdapter<BluetoothDevice>(PhotonDeviceListActivity.this, android.R.layout.simple_list_item_1, discoverBluetoothDevicesList);
                photonDeviceListView.setAdapter(adapter);
                stateDiscoverLabel.setText("Trwa wyszukiwanie...");
                findText.setText("Wyszukiwanie w toku...");

                //zapobiegawczo zatrzymujemy aktualne wyszukiwanie i rozpoczynamy nowe

                myBluetoothAdapter.cancelDiscovery();
                myBluetoothAdapter.startDiscovery();
            }
        });

        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        pairedBluetoothDevicesList.clear();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                pairedBluetoothDevicesList.add(device);
            }
        }

        photonDeviceListView.setClickable(true);

        photonDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(myBluetoothAdapter.isDiscovering()) {
                    myBluetoothAdapter.cancelDiscovery();
                }

                if(deviceListAdapter.getItem(position).getName().contains("NEW")){
                    Toast.makeText(getApplicationContext(), "TRZEBA SPAROWAć OOOO", Toast.LENGTH_SHORT).show();
                }else{
                //    CoreDevice.activeDevice = discoverBluetoothDevicesList.get(position);
                  //  finish();
                }

                //TO DO WYWALENIA BEDZIE Z CZASEM
                CoreDevice.activeDevice = discoverBluetoothDevicesList.get(position);
                finish();

            }
        });


        //wysylamy rozgloszenie z akcja ACTION_FOUND, ktore odbiera BroadcastReceiver - mReceiver
        registerReceiver(mReceiver, filter);

        //czyscimy liste urzadzen i rozpoczynamy na nowo
        discoverBluetoothDevicesList.clear();
        ArrayAdapter<BluetoothDevice> adapter = new ArrayAdapter<BluetoothDevice>(PhotonDeviceListActivity.this, android.R.layout.simple_list_item_1, discoverBluetoothDevicesList);
        photonDeviceListView.setAdapter(adapter);
        stateDiscoverLabel.setText("Trwa wyszukiwanie...");
        findText.setText("Wyszukiwanie w toku...");

        //zapobiegawczo zatrzymujemy aktualne wyszukiwanie i rozpoczynamy nowe
        myBluetoothAdapter.cancelDiscovery();
        myBluetoothAdapter.startDiscovery();

    }

    @Override
    protected void onStop() {
        myBluetoothAdapter.cancelDiscovery();
        unregisterReceiver(mReceiver);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photon_device_list, menu);
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
        finish();
    }

    // Tworzymy BroadcastReceiver dla ACTION_FOUND czyli szukania urzadzen
    final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // Add the name and address to an array adapter to show in a
                // ListView
                String state = "(NEW)  ";
                for (int i=0; i< pairedBluetoothDevicesList.size(); i++){
                    if(device.getName().equals(pairedBluetoothDevicesList.get(i).getName())){
                        state="";
                        break;
                    }
                }

                discoverBluetoothDevicesList.add(device);
                discoverViewBluetoothDeviceList.add(new DiscoverBTDevice(state+device.getName(), device.getAddress()));
                deviceListAdapter = new ArrayAdapter<DiscoverBTDevice>(PhotonDeviceListActivity.this, android.R.layout.simple_list_item_1, discoverViewBluetoothDeviceList);
                photonDeviceListView.setAdapter(deviceListAdapter);

                stateDiscoverLabel.setText("Wyszukiwanie zakończone.");
                findText.setText("Wyszukaj ponownie");
                findButton.setEnabled(true);
            }
        }
    };
}
