package de.htwberlin.lora_multihop_visualisation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

/**
 * possibility to select a discovered Bluetoothdevice (in our case MobANet)
 *
 */
public class SelectDeviceActivity extends AppCompatActivity implements Runnable{

    private int selectedDeviceColor = Color.YELLOW;

    /**
     * Access to to paired and new bt devices
     */
    private BluetoothAdapter bluetoothAdapter;
    /**
     * contains all devices
     */
    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();    //contains alls currently accessable devices and fills listadapter

    /**
     *
     */
    private ArrayAdapter<BluetoothDevice> listAdapter = null;       //fills listView
    /**
     * contains all accessable devices for s possible connection (on Itemclick set SingleDevice)
     */
    ListView listView;

    /**
     * feedback for device scanning on Userinterface
     */
    ProgressBar progressBar;


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

                if(!isInBluetoothDevices(device.getAddress())){
                    bluetoothDevices.add(device);//neues device hinzufügen wenn nicht bereits vorhanden
                    listAdapter.notifyDataSetChanged(); //listadapter über neues device benachrichtigen um folglich auch listview zu updaten
                }
            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bluetooth_device);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_scanForDevices);

        //contains all accessable devices for s possible connection
        listView= (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bluetoothAdapter.cancelDiscovery();
                SingletonDevice.setBluetoothDevice(bluetoothDevices.get(position));  //a device is accessable for mainactivity
                view.setBackgroundColor(selectedDeviceColor);
                //TODO only one selected device pssible
            }
        });

        // is observed from listview
        listAdapter = new ArrayAdapter<BluetoothDevice>(this,0, bluetoothDevices){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                BluetoothDevice device = bluetoothDevices.get(position);
                if (convertView == null)
                    convertView = getLayoutInflater().inflate(R.layout.device_item, parent, false);

                TextView textView_devicename = convertView.findViewById(R.id.device);
                TextView textView_macAddr = convertView.findViewById(R.id.deviceMac);

                String deviceName= device.getName();
                String mac= device.getAddress();

                if (deviceName != null && !deviceName.isEmpty()) {
                    textView_devicename.setText(deviceName);
                }else{
                    textView_devicename.setText("Unknown");
                }

                textView_macAddr.setText(mac);

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
            progressBar.setVisibility(View.VISIBLE);
            scanForNewDevices();
            return true;
        }
        if (id == R.id.showPairedDevices) {
            progressBar.setVisibility(View.GONE);
            showPairedDevices();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void scanForNewDevices(){
        bluetoothAdapter.cancelDiscovery();
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

    private boolean isInBluetoothDevices(String adress){
        for (BluetoothDevice device : bluetoothDevices) {

            if(adress.equals(device.getAddress())){
                return true;
            }

        }
        return false;
    }
}
