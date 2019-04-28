package de.htwberlin.lora_multihop_visualisation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
            Toast.makeText(this, "Vorschlag 1 geklickt!", Toast.LENGTH_LONG).show();
            startAnotherActivity(LoraSettingsActivity.class);
            return true;
        }

        if (id == R.id.item_LoraKonfig2) {
            Toast.makeText(this, "Vorschlag 2 geklickt!", Toast.LENGTH_LONG).show();
            //startAnotherActivity(LoraSettingsActivity.class);
            return true;
        }

        if (id == R.id.item_bluetooth) {
            Toast.makeText(this, "Bluetooth geklickt", Toast.LENGTH_LONG).show();
            //startAnotherActivity(LoraSettingsActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonHandling(View v){
        Button b = (Button) v;
        Toast.makeText(this, b.getText()+" geklickt!", Toast.LENGTH_LONG).show();
    }

    private void startAnotherActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
