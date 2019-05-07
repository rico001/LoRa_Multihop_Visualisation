package de.htwberlin.lora_multihop_visualisation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

public class SelectDeviceActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;

    TextView deviceslist;
    TextView discoverdevices;
    List<BluetoothDevice> bluetootchDevices;

   //sucht nach verfügbaree  geräte
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                discoverdevices.append(deviceName+":"+deviceHardwareAddress+"\n");
            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bluetooth_device);

        initTextViews();
        initBluetoothAdapter();
        getAllPairedDevices();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        bluetoothAdapter.startDiscovery();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }

    //listet alle gepairte devices
    private void getAllPairedDevices(){
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        String devices= new String();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                devices += device.getName()+"!!!!:"+device.getAddress()+"\n";
            }
        }

        deviceslist.setText(devices);
    }

    private void initTextViews(){
        deviceslist = (TextView) findViewById(R.id.bluetoothdevices);
        deviceslist.setText("hallo");
        discoverdevices = (TextView) findViewById(R.id.discoverdevices);
    }

    //verbindet sich mit bluetoothschnittstelle im smartphone
    private void initBluetoothAdapter(){
        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //Anfrage ob verbinden falls nicht aktiverit
            startActivityForResult(enableBtIntent,3);
        }
    }

}
