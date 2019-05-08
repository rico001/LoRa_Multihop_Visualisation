package de.htwberlin.lora_multihop_visualisation;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * possibility to select a discovered Bluetoothdevice (in our case MobANet)
 *
 */
public class SelectDeviceActivity extends AppCompatActivity implements Runnable{

    private BluetoothAdapter bluetoothAdapter;
    private ConnectThread tryConnect; //TODO: later handle exceptions and success

    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    private ArrayAdapter<BluetoothDevice> listAdapter;
    ListView listView;

   //sucht nach verfügbaree  geräte
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothDevices.add(device);//TODO dont add own device!!!
                listAdapter.notifyDataSetChanged();
            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bluetooth_device);
        listView= (ListView) findViewById(R.id.listView);

        //itemclick initiates a BT connection
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bluetoothAdapter.cancelDiscovery();
                tryConnect= new ConnectThread(bluetoothDevices.get(position));
                tryConnect.run();   //starts the BTconnection
            }
        });

        initBluetoothAdapter();

        /**
         * updated all accessible devices in list
         */
        listAdapter = new ArrayAdapter<BluetoothDevice>(this,0, bluetoothDevices){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                BluetoothDevice device = bluetoothDevices.get(position);
                if (convertView == null)
                    convertView = getLayoutInflater().inflate(R.layout.device_item, parent, false);

                TextView devicename = convertView.findViewById(R.id.device);
                TextView macAddr = convertView.findViewById(R.id.deviceMac);

                devicename.setText(device.getName());
                macAddr.setText(device.getAddress());

                return convertView;
            }
        };
        listView.setAdapter(listAdapter);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        bluetoothAdapter.startDiscovery();


    }

    @Override
    protected void onDestroy() {
        bluetoothDevices.clear();
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
        bluetoothAdapter.cancelDiscovery();

    }


    private void initBluetoothAdapter(){
        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //Anfrage ob verbinden falls nicht aktiverit
            startActivityForResult(enableBtIntent,3);
        }
    }

    @Override
    public void run() {
        //TODO if connnection success-> onDestroy and back to mainactivity, thread starts in on create
    }
}
