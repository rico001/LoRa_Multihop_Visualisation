package de.htwberlin.lora_multihop_visualisation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import de.htwberlin.lora_multihop_implementation.components.lora.LoraCommandsExecutor;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_implementation.interfaces.IMapFragmentListener;
import de.htwberlin.lora_multihop_implementation.interfaces.ITerminalFragmentListener;
import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;

/**
 * Visualisation and building a multihop wireless network
 */
public class MainActivity extends AppCompatActivity implements MessageConstants {

    private final static int sendColor = Color.RED;
    private final static int readColor = Color.BLUE;

    private MainFragmentsAdapter mainFragmentsAdapter;
    private ViewPager viewPager;

    private ITerminalFragmentListener terminalListener;
    private IMapFragmentListener mapListener;

    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private ILoraCommands loraCommandsExecutor;
    public BluetoothService btService = null;

    /**
     * Handler to update the terminal
     */
    private final Handler msgHandler = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_CONNECTING:
                    terminalListener.updateTerminalMessages(readColor, "Verbindung mit " + SingletonDevice.getBluetoothDevice().getName() + " wird aufgebaut", false);
                    break;
                case STATE_CONNECTED:
                    terminalListener.updateTerminalMessages(readColor, "Verbindung ist aufgebaut", false);
                    break;
                case MESSAGE_READ:
                    String message = (String) msg.obj;
                    terminalListener.updateTerminalMessages(readColor, message, false);
                    break;
                case MESSAGE_ERROR:
                    System.out.println("MSG ERROR");
                    break;
            }
        }
    };

    /**
     * init elements of this Class -> https://developer.android.com/guide/components/activities/activity-lifecycle
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Checks if the app has the location permissions, if not it requests them
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(LOCATION_PERMS, 1337);
        } else {
            init();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBluetoothService();
    }

    /**
     * Callback for the location permissions
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1337: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    requestPermissions(LOCATION_PERMS, 1337);
                }
                return;
            }
        }
    }

    /**
     * Sets up the activity
     */
    private void init() {
        mainFragmentsAdapter = new MainFragmentsAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.main_container);

        setUpViewPager(viewPager);

        loraCommandsExecutor = new LoraCommandsExecutor(btService);
    }

    /**
     * Sets the terminal listener to our fragment so we can call terminalListener.updateTerminalMessages
     * @param listener
     */
    public void setTerminalListener(ITerminalFragmentListener listener) {
        this.terminalListener = listener;
    }

    public void setMapListener(IMapFragmentListener listener) {
        this.mapListener = listener;
    }


    /**
     * Sets the fragment container and adds the map and terminal fragments
     * @param viewPager
     */
    private void setUpViewPager(ViewPager viewPager) {
        MainFragmentsAdapter adapter = new MainFragmentsAdapter(getSupportFragmentManager());

        MapFragment mapFragment = new MapFragment();
        TerminalFragment terminalFragment = new TerminalFragment();
        ProtocolFragment logicFragment = new ProtocolFragment();

        // The order in which the fragments are added is very important!
        adapter.addFragment(mapFragment, "MapFragment");
        adapter.addFragment(terminalFragment, "TerminalFragment");
        adapter.addFragment(logicFragment, "LogicFragment");

        setMapListener(mapFragment);
        setTerminalListener(terminalFragment);
        viewPager.setAdapter(adapter);
    }

    /**
     * Changes the active fragment
     * @param position
     */
    public void setViewPager(int position) {
        viewPager.setCurrentItem(position);
    }

    private void initBluetoothService() {
        try {
            btService = new BluetoothService(this, msgHandler, SingletonDevice.getBluetoothDevice());
            btService.connectWithBluetoothDevice();
        } catch (NullPointerException e) {
            Log.d("initBluetoothService", "Choose a device!");
            // This is broken
            //terminalListener.updateTerminalMessages(readColor, "Wählen Sie ein Device in den Settings", false);
        }
    }

    /**
     * Create a menu on actionbar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handle button click on actionbar
     *
     * @param item of menu
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_LoraKonfig) {
            startAnotherActivity(LoraSettingsActivity.class);
            Toast.makeText(this, "Lat: " + mapListener.getLocation().latitude + "\nLon: " + mapListener.getLocation().longitude, Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.item_bluetooth) {
            //Toast.makeText(this, "Bluetooth geklickt", Toast.LENGTH_LONG).show();
            startAnotherActivity(SelectDeviceActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handling of all Button in mainactivity (onClickHandler
     * is init in mainactivity.xml file/ android:onClick="buttonHandling")
     *
     * @param v as button
     */
    /*public void buttonHandling(View v) {
        Button button = (Button) v;
        int id = v.getId();

        try {

            switch (id) {
                case R.id.bttn_test:
                    //TODO @Gruppe Rountinen mit kurzem warten zwischen einzelnen Befehle sonst "ERR:BUSY" ggf Warten auf Response/CMD (Queue?)
                    loraCommandsExecutor.test();//just a test
                    //BSP:
                    //loraCommandsExecutor.setAddress("EEEE");
                    //kurz Warten (ggf. response verarbeiten, wenn notwendig!)
                    //loraCommandsExecutor.getAddress();
                    break;
                case R.id.bttn_setAddr:
                    loraCommandsExecutor.getVersion();//just a test
                    break;
                default:
                    Toast.makeText(this, button.getText() + " AT-Routine", Toast.LENGTH_LONG).show();
            }
        } catch (NullPointerException e) {
            updateTerminalMessages(readColor, "Wählen Sie ein Device in den Settings", false);
        }

    }*/

    /**
     * Starts a new activity
     * @param c is Class of this Activity
     */
    private void startAnotherActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (btService == null) {
            return;
        } else {
            btService.diconnect();
        }
    }

}