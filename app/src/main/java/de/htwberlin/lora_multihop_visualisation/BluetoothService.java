package de.htwberlin.lora_multihop_visualisation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;

public class BluetoothService implements MessageConstants{
    private static final String TAG = "blue";
    private Handler handler; // handler that gets info from Bluetooth service
    private BluetoothAdapter adapter;
    private BluetoothDevice bluetoothDevive;
    private AcceptThread acceptThread;
    private ConnectedThread connectThread;

    private boolean connected=false;

    public BluetoothService(Context context, Handler handler, BluetoothDevice device) {
        adapter = BluetoothAdapter.getDefaultAdapter();
        this.handler = handler;
        bluetoothDevive=device;
    }


    public void connectWithBluetoothDevice(){
        acceptThread = new AcceptThread(bluetoothDevive);
        acceptThread.start();
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public synchronized void write(byte[] out) {

        ConnectedThread r;

        synchronized (this) {
            if (!isConnected()) return;
            r = connectThread;
        }
        // Perform the write unsynchronized
        r.write(out);
    }

    public void diconnect() {
        if (!isConnected()) return;
        connectThread.cancel();
    }

    /**
     * init a Bluetooth Connection
     */
    private class AcceptThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        public AcceptThread(BluetoothDevice device) {
            Message readMsg = handler.obtainMessage(STATE_CONNECTING);
            readMsg.sendToTarget();

            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                if (mmDevice != null)
                {
                    Log.d(TAG, "Device Name: " + mmDevice.getName());
                    Log.d(TAG, "Device UUID: " + mmDevice.getUuids()[0].getUuid());
                    tmp = device.createRfcommSocketToServiceRecord(mmDevice.getUuids()[0].getUuid());

                }
            }
            catch (NullPointerException e)
            {
                Log.d(TAG, " UUID from device is null, Using Default UUID, Device name: " + device.getName());
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
            adapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
                Message readMsg = handler.obtainMessage(STATE_CONNECTED);
                readMsg.sendToTarget();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.d(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            connectThread = new ConnectedThread(mmSocket);
            connectThread.start();


        }

        //Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.d(TAG, "Could not close the client socket", e);
            }

        }
    }

    /**
     * Handle Bluetooth connection with in- and output
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;

        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        private final DataInputStream dataInStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating output stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            dataInStream = new DataInputStream(tmpIn);
        }

        public void run() {
            setConnected(true);

            Log.d(TAG, "connected thread start");

            mmBuffer = new byte[512];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                        numBytes = dataInStream.read(mmBuffer);
                        String readMessage = new String(mmBuffer, 0, numBytes)
                                .replace(System.lineSeparator(),"!");

                        Log.d(TAG,readMessage);

                        if(!readMessage.isEmpty())
                            handler.obtainMessage(MESSAGE_READ, numBytes, -1, readMessage)
                                    .sendToTarget();
                        //TODO is reading to fast und interpretiert "\r\n"
                } catch (IOException e) {
                    Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }

            Log.d(TAG, "connected thread ends");
        }

        // Call this from the main activity to send data to the remote device.
        public void write(byte[] bytes) {
            try {
                    mmOutStream.write(bytes);
                // Share the sent message with the UI activity.
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                Message writeErrorMsg =
                        handler.obtainMessage(MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                handler.sendMessage(writeErrorMsg);
            }

        }

        // Call this method from the main activity to shut down the connection.
        public void cancel() {
            try {
                mmSocket.close();
                setConnected(false);
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }
    }

}

