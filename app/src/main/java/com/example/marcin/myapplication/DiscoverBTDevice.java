package com.example.marcin.myapplication;

/**
 * Created by Marcin on 2014-11-19.
 */
public class DiscoverBTDevice {
    private String name;
    private String address;

    public DiscoverBTDevice(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return this.getName() + "\n" + this.getAddress();
    }
}
