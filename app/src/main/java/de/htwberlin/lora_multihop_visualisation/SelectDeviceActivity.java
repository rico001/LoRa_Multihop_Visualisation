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
import android.widget.Toast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

/**
 * possibility to select a discovered Bluetoothdevice (in our case MobANet)
 *
 */
public class SelectDeviceActivity extends AppCompatActivity implements Runnable{

    private BluetoothAdapter bluetoothAdapter;
    private ConnectThread tryConnect; //TODO: later handle exceptions and success

    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();    //contains alls currently accessable devices and fills listadapter
    private ArrayAdapter<BluetoothDevice> listAdapter = null;       //fills listView
    ListView listView;

    /**
     * sucht nach verfügbaren neuen Geräten und fügt sie zu den bluetoothDevices hinzu->listadapter for listview wird geupdatet
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);//neues bluetooth device erkannt
                bluetoothDevices.add(device);//neues device hinzufügen
                listAdapter.notifyDataSetChanged(); //listadapter über neues device benachrichtigen um folglich auch listview zu updaten

            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bluetooth_device);

        //contains all accessable devices for s possible connection
        listView= (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bluetoothAdapter.cancelDiscovery();
                tryConnect= new ConnectThread(bluetoothDevices.get(position));
                tryConnect.run();   //starts the BT connection
            }
        });

        // is observed from listview
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

        initBluetoothAdapter();

        showPairedDevices();
    }

    @Override
    protected void onDestroy() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bluetoothoptions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.scanForNewDevices) {
            Toast.makeText(this, "scan for new Devices", Toast.LENGTH_LONG).show();
            scanForNewDevices();
            return true;
        }
        if (id == R.id.showPairedDevices) {
            showPairedDevices();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void scanForNewDevices(){
        bluetoothDevices.clear();
        listAdapter.notifyDataSetChanged();
        bluetoothAdapter.startDiscovery();
    }

    @Override
    public void run() {
        //TODO if connnection success-> onDestroy and back to mainactivity, thread starts in on create
    }

    private void showPairedDevices(){
        bluetoothAdapter.cancelDiscovery();
        bluetoothDevices.clear();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                bluetoothDevices.add(device);
            }
        }
        listAdapter.notifyDataSetChanged();
    }
}
