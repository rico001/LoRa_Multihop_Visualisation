package de.htwberlin.lora_multihop_visualisation;

import android.bluetooth.BluetoothDevice;


public class SingletonDevice {
    private static BluetoothDevice device =null;

    public static synchronized BluetoothDevice getBluetoothDevice() throws NullPointerException{
        if(device==null){
            throw new NullPointerException();
        }
        return device;
    }

    public static synchronized void setBluetoothDevice(BluetoothDevice d){
        SingletonDevice.device = d;
    }
}

