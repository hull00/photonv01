package com.example.marcin.myapplication;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Marcin on 2014-11-17.
 */
public class DiscoverBluetoothDevice {
    public String name, address;
    public int bond;


    public DiscoverBluetoothDevice(String name, String address, int bond){
        this.name = name;
        this.address = address;
        this.bond = bond;
    }

    @Override
    public String toString() {
        String bondText="";
        if(bond == BluetoothDevice.BOND_NONE)
            bondText = "niesparowane";
        else if(bond == BluetoothDevice.BOND_BONDING)
            bondText = "parowanie w toku";
        else if(bond == BluetoothDevice.BOND_BONDED)
            bondText = "sparowane";
        return name +"\n"+ address +"\n"+ bondText;
    }
}
