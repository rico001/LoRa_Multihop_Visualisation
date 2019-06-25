package de.htwberlin.lora_multihop_visualisation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import de.htwberlin.lora_multihop_logic.SetupManager;
import de.htwberlin.lora_multihop_logic.interfaces.MessageConstants;
import de.htwberlin.lora_multihop_visualisation.custom.NeighbourSetTableRow;
import de.htwberlin.lora_multihop_visualisation.fragments.MainFragmentsAdapter;
import de.htwberlin.lora_multihop_visualisation.fragments.MapFragment;
import de.htwberlin.lora_multihop_visualisation.fragments.NeighbourSetTableFragment;
import de.htwberlin.lora_multihop_visualisation.fragments.TerminalFragment;

/**
 * Visualisation and building a multihop wireless network
 */
public class MainActivity extends AppCompatActivity implements MessageConstants, MapFragment.IMapListener, NeighbourSetTableFragment.ITableListener {

    private final static String TAG = "mainactivity";

    private MainFragmentsAdapter mainFragmentsAdapter;
    private ViewPager viewPager;

    private MapFragment mapFragment;
    private TerminalFragment terminalFragment;
    private ProtocolFragment protocolFragment;
    private NeighbourSetTableFragment neighbourSetTableFragment;

    private SetupManager setupManager;

    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
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
            initLoraSetup();
        }
    }

    /**
     * Sets up the activity
     */
    private void init() {
        mainFragmentsAdapter = new MainFragmentsAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.main_container);

        // Sets up the ViewPager with all the fragments
        setUpViewPager(viewPager);
    }

    /**
     * Sets up the connection between android & lora related stuff
     */
    private void initLoraSetup() {
        this.setupManager = new SetupManager(mapFragment, terminalFragment, neighbourSetTableFragment);
    }

    /**
     * Callback for the location permissions
     *
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
     * Sets the fragment container and adds the map and terminal fragments
     *
     * @param viewPager
     */
    private void setUpViewPager(ViewPager viewPager) {
        MainFragmentsAdapter adapter = new MainFragmentsAdapter(getSupportFragmentManager());

        this.mapFragment = new MapFragment();
        this.terminalFragment = new TerminalFragment();
        this.neighbourSetTableFragment = new NeighbourSetTableFragment();
        this.protocolFragment = new ProtocolFragment();

        // The order in which the fragments are added is very important!
        adapter.addFragment(this.mapFragment, "MapFragment");
        adapter.addFragment(this.terminalFragment, "TerminalFragment");
        adapter.addFragment(this.neighbourSetTableFragment, "NeighbourSetTableFragment");
        adapter.addFragment(this.protocolFragment, "LogicFragment");

        viewPager.setAdapter(adapter);
    }

    /**
     * Changes the active fragment
     *
     * @param position
     */
    public void setViewPager(int position) {
        viewPager.setCurrentItem(position);
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

        try {
            this.setupManager.disconnectBluetooth();
        } catch (NullPointerException e) {
            Log.d(TAG, "btService is null");
        }

        int id = item.getItemId();

        if (id == R.id.item_LoraKonfig) {
            startAnotherActivity(LoraSettingsActivity.class);
            return true;
        }
        if (id == R.id.item_bluetooth) {
            //Toast.makeText(this, "Bluetooth geklickt", Toast.LENGTH_LONG).show();
            startAnotherActivity(SelectDeviceActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.setupManager.getNeighbourSetData().clear();
    }

    /**
     * Starts a new activity
     *
     * @param c is Class of this Activity
     */
    private void startAnotherActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public SetupManager getSetupManager() {
        return setupManager;
    }

    public MapFragment getMapFragment() {
        return mapFragment;
    }

    @Override
    public void onSetUpMap() {

    }

    @Override
    public void onRowAdded(NeighbourSetTableRow row) {

    }

    @Override
    public void onRowUpdated(NeighbourSetTableRow row) {

    }

    @Override
    public void onRowRemoved(String id) {

    }

    @Override
    public void onRemoveAll() {

    }
}