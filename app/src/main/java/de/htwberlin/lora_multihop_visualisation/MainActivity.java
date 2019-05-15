package de.htwberlin.lora_multihop_visualisation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.htwberlin.lora_multihop_implementation.components.lora.LoraCommandsExecutor;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;

/**
 * Visualisation and building a multihop wireless network
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, MessageConstants {

    /**
     * shows nodes of ouer Multihop network
     */
    private GoogleMap mMap;

    private final static int sendColor = Color.RED;
    private final static int readColor = Color.BLUE;

    /**
     * Terminal for Feedback of succes LoraCMD Routines
     */
    private LinearLayout terminalMessages;

    private ILoraCommands loraCommandsExecutor;
    private BluetoothService btService = null;
    /**
     * handler updates terminal
     */
    private final Handler msgHandler = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_CONNECTING:
                    updateTerminalMessages(readColor, "Verbindung mit " + SingletonDevice.getBluetoothDevice().getName() + " wird aufgebaut", false);
                    break;
                case STATE_CONNECTED:
                    updateTerminalMessages(readColor, "Verbindung ist aufgebaut", false);
                    break;
                case MESSAGE_READ:
                    String message = (String) msg.obj;
                    updateTerminalMessages(readColor, message, false);
                    break;
                case MESSAGE_ERROR:
                    System.out.println("MSG ERROR");
                    break;
            }
        }
    };

    /**
     * init elements of this Class -> https://developer.android.com/guide/components/activities/activity-lifecycle
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMap();
        initWidgets();
        initBluetoothService();
        loraCommandsExecutor = new LoraCommandsExecutor(btService);
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initWidgets(){
        terminalMessages = (LinearLayout) findViewById(R.id.linearLayout_feedback);
    }

    private void initBluetoothService()	{
        try {
            btService = new BluetoothService(this, msgHandler, SingletonDevice.getBluetoothDevice());
            btService.connectWithBluetoothDevice();
        } catch (NullPointerException e) {
            updateTerminalMessages(readColor, "Wählen Sie ein Device in den Settings", false);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng brln3 = new LatLng(52.5004, 13.5001);
        mMap.addMarker(new MarkerOptions()
                .position(brln3).title("C: 24-33-55-aa-fd-7e"))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(brln3));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    /**
     * create a menu on actionbar
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
     * handle buttonclick on actionbar
     * @param item of menu
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_terminal) {
            startAnotherActivity(TerminalActivity.class);
            return true;
        }

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

    /**
     * Handling of all Button in mainactivity (onClickHandler
     * is init in mainactivity.xml file/ android:onClick="buttonHandling")
     * @param v as button
     */
    public void buttonHandling(View v) {
        Button button = (Button) v;
        int id=v.getId();

        try {

            switch(id){
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

    }

    /**
     * starts a new Activity
     * @param c is Class of this Activity
     */
    private void startAnotherActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    /**
     * put new Massages into Terminal
     * @param color of text
     * @param message
     * @param isSendMessage
     * @return
     */
    private synchronized boolean updateTerminalMessages(int color, String message, boolean isSendMessage) {

        String symbols = "<< ";
        String time = getCurrentTime() + " ";

        if (isSendMessage) {
            symbols = ">> ";
        }

        TextView textView = new TextView(this);
        textView.setText(time + symbols + message);
        textView.setTextColor(color);

        terminalMessages.addView(textView);

        //TODO auto. scrolling nach unten bei update
        return true;
    }

    private String getCurrentTime() {
        Date date = new Date();
        String strDateFormat = "hh:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(btService==null){
            return;
        }else{
            btService.diconnect();
        }
    }

}