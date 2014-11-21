package com.example.marcin.myapplication;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothService extends Service {

    private static final int SUCCESS_CONNECT = 0;
    private static final int MESSAGE_READ = 1;
    private final IBinder myBinder = new ServiceBinder(); //ServiceBinder
    private Boolean isConnected = false;
    private ConnectThread myConnectThread;
    private ConnectedThread connectedThread;
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.i("Handle","I'm in");

            super.handleMessage(msg);

            Log.i("Handle","Step two");


            switch (msg.what){
                case SUCCESS_CONNECT:
                    Log.i("Handle","Connect which "+ CoreDevice.activeDevice.getName().toString());
                    connectedThread = new ConnectedThread((BluetoothSocket)msg.obj);

                    Toast.makeText(getApplicationContext(), "Connect which "+ CoreDevice.activeDevice.getName().toString(), Toast.LENGTH_LONG).show();

                    String s = "Successfully connected!";
                    connectedThread.write(s.getBytes());
                    break;

                case MESSAGE_READ:
                    byte[] readBuf = (byte[])msg.obj;
                    String sBuf = new String(readBuf);
                    Log.i("Message",sBuf);

                    Toast.makeText(getApplicationContext(), sBuf, Toast.LENGTH_LONG).show();

                    break;

                default:

                    Toast.makeText(getApplicationContext(), "COS INNEGO", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

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
        Log.i("ServiceBT","connectMethod");

        //CONNECT
        myConnectThread = new ConnectThread(CoreDevice.activeDevice);
        myConnectThread.start();
    }

    public void disconnect() {
        //DISCONNECT
        myConnectThread.cancel();
    }

    public void send(String string){
        //SEND BLUETOOTH DATA
        connectedThread.write(string.getBytes());
    }

    public Boolean getState() {
        return isConnected;
    }

    //endregion SERVICE_PUBLIC_METHOD



    /*
        WĄTEK DO POŁĄCZENIA BLUETOOTH
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                Log.i("connectThread","connectTry1");
                mmSocket.connect();
                Log.i("connectThread","connect1Success");
                isConnected = true;
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                Log.i("connectThread","connect1Fail");
               try{
                   this.sleep(1000);
                   Log.i("connectThread","connect2Try");
                   mmSocket.connect();
                   Log.i("connectThread","connect2Success");
               }catch (IOException connectException2) {
                   try {
                       Log.i("connectThread","connect2fail");
                       isConnected = false;
                       mmSocket.close();
                   } catch (IOException closeException) {
                   }
                   return;
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
            }

            // Do work to manage the connection (in a separate thread)
            mHandler.obtainMessage(SUCCESS_CONNECT, mmSocket).sendToTarget();
            Log.i("ServiceBT","obtainMessage");

        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                isConnected=false;
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
                Log.i("connectedThread","StreamSuccess");

            } catch (IOException e) {
                Log.i("connectedThread","StreamFail");
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer;  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    buffer = new byte[1024];
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                //Log.i("write", "try: "+ new String(bytes));
                mmOutStream.write(bytes);
                //Log.i("write", new String(bytes));
            } catch (IOException e) {
                Log.i("write", "fail");
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

}
