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

import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final static String AT_POSTFIX= "\r\n";
    private final static int sendColor= Color.BLUE;
    private final static int readColor= Color.RED;
    private MyBluetoothService btService = null;
    private GoogleMap mMap;
    private EditText editText_messages;
    private LinearLayout linearLayout_messages;

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case MessageConstants.STATE_CONNECTING:
                    update_LinearLayout_messages(readColor,"Verbindung mit "+SingletonDevice.getBluetoothDevice().getName()+" wird aufgebaut",true);
                    break;
                case MessageConstants.STATE_CONNECTED:
                    update_LinearLayout_messages(readColor,"Verbindung ist aufgebaut",true);
                    break;
                case MessageConstants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    Log.d("blue","send:     "+writeMessage);
                    update_LinearLayout_messages(readColor,writeMessage,false);
                    break;
                case MessageConstants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Log.d("blue","read:     "+readMessage);
                    update_LinearLayout_messages(sendColor,readMessage,true);
                    break;
                case MessageConstants.MESSAGE_ERROR:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        editText_messages= (EditText) findViewById(R.id.editText_messages);
        linearLayout_messages= (LinearLayout) findViewById(R.id.linearLayout_messages);

        try{
            btService = new MyBluetoothService(this,mHandler,SingletonDevice.getBluetoothDevice());
            btService.connectWithBluetoothDevice();
        }catch(NullPointerException e){
            update_LinearLayout_messages(sendColor,"Wählen Sie ein Device in den Settings", true);
        }


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng brln1 = new LatLng(52.5001, 13.5002);
        mMap.addMarker(new MarkerOptions()
                .position(brln1).title("A: 00-80-41-ae-fd-7e"))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        LatLng brln2 = new LatLng(52.501, 13.5002);
        mMap.addMarker(new MarkerOptions()
                .position(brln2).title("B: 23-80-41-ae-fd-7e"))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        LatLng brln3 = new LatLng(52.5004, 13.5001);
        mMap.addMarker(new MarkerOptions()
                .position(brln3).title("C: 24-33-55-aa-fd-7e"))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(brln3));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    public void buttonHandling(View v){
        Button b = (Button) v;

        if(v.getId()==R.id.bttn_sendMessage){

            try{
                if(btService.isConnected()){
                    String messageString = editText_messages.getText().toString();
                    byte[] messageByte = (messageString+AT_POSTFIX).getBytes();
                    btService.write(messageByte);
                }

            }catch(NullPointerException e){
                update_LinearLayout_messages(sendColor,"Wählen Sie ein Device in den Settings", true);
            }
        }
        else{
            Toast.makeText(this, b.getText()+" AT-Routine", Toast.LENGTH_LONG).show();
        }

    }

    private void startAnotherActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    private boolean update_LinearLayout_messages(int color, String message, boolean isSendMessage){

        String symbols="<<";
        String time=getCurrentTime()+"Uhr:  ";

        if(isSendMessage){
            symbols=">>";
        }

        TextView textView= new TextView(this);
        textView.setText(time+symbols+message);
        textView.setTextColor(color);

        linearLayout_messages.addView(textView);

        //TODO auto. scrolling nach unten bei update
        return true;
    }

    private String getCurrentTime() {
        Date date = new Date();
        String strDateFormat = "hh:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }


}
