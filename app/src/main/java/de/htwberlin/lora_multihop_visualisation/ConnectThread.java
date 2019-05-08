package de.htwberlin.lora_multihop_visualisation;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
import java.util.UUID;

/**
 * Connects Bluetooth Socket
 */
public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public ConnectThread(BluetoothDevice device) {

        BluetoothSocket tmp = null;
        mmDevice = device;

        try {
            if (mmDevice != null)
            {
                Log.d("blue", "Device Name: " + mmDevice.getName());
                Log.d("blue", "Device UUID: " + mmDevice.getUuids()[0].getUuid());
                tmp = device.createRfcommSocketToServiceRecord(mmDevice.getUuids()[0].getUuid());

            }
            else Log.d("blue", "Device is null.");
        }
    catch (NullPointerException e)
        {
            Log.d("blue", " UUID from device is null, Using Default UUID, Device name: " + device.getName());
            try {
                tmp = device.createRfcommSocketToServiceRecord(DEFAULT_UUID);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    catch (IOException e) { }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
       // bluetoothAdapter.cancelDiscovery();

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.d("blue", "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        //manageMyConnectedSocket(mmSocket);
    }

    //Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.d("blue", "Could not close the client socket", e);
        }
    }
}

