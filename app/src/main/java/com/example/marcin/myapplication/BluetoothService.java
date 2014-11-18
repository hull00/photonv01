package com.example.marcin.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BluetoothService extends Service {

    private final IBinder myBinder = new ServiceBinder(); //ServiceBinder
    private Boolean isConnected = false;

    @Override
    public IBinder onBind(Intent intent) {
        // Zwracamy interfejs do Activity aby moc poslugiwac sie metodami
        // zawartymi w serwisie
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "Create service", Toast.LENGTH_SHORT ).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Service destroy", Toast.LENGTH_SHORT ).show();
    }

    // klasa ServiceBinder pozwala "spiąć" Acitivity z Servicem
    public class ServiceBinder extends Binder {
        BluetoothService getService() {
            // ZWRACAMY INSTANCJE SERWISU, PRZEZ NIĄ ODWOŁAMY SIĘ DO METOD
            return BluetoothService.this;
        }
    }

    /*
            METODY KTÓRE UDOSTĘPNIA SERVICE
    */
    //region SERVICE_PUBLIC_METHOD
    public void connect() {
        //CONNECT
    }

    public void disconnect() {
        //DISCONNECT
    }

    public Boolean getState() {
        return isConnected;
    }

    //endregion SERVICE_PUBLIC_METHOD



}
