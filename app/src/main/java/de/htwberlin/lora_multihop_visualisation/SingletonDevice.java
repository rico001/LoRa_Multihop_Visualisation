package de.htwberlin.lora_multihop_visualisation;

import android.bluetooth.BluetoothDevice;

import de.htwberlin.lora_multihop_implementation.components.model.LocalHop;

/**
 * unique Access to device for BTcommunication (important for init BluetoothService)
 */
public class SingletonDevice {

    private static BluetoothDevice device =null;
    private static LocalHop localHop;

    public static synchronized BluetoothDevice getBluetoothDevice() throws NullPointerException{
        if(device==null){
            throw new NullPointerException();
        }
        return device;
    }

    public static synchronized void setBluetoothDevice(BluetoothDevice d){
        SingletonDevice.device = d;
    }

    public static LocalHop getLocalHop() {
        return localHop;
    }

    public void setLocalHop(LocalHop localHop) {
        this.localHop = localHop;
    }
}

