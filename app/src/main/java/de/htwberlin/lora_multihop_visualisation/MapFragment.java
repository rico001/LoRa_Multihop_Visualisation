package de.htwberlin.lora_multihop_visualisation;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import de.htwberlin.lora_multihop_implementation.enums.EFragments;
import de.htwberlin.lora_multihop_implementation.interfaces.IMapFragmentListener;

public class MapFragment extends Fragment implements IMapFragmentListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private MapView mapView;
    private Button terminalButton;
    private LatLng location;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        terminalButton = view.findViewById(R.id.return_to_terminal);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        // Button to return to the terminal
        terminalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setViewPager(EFragments.TERMINAL_FRAGMENT.get());
                //just a test
                addMarker(location,BitmapDescriptorFactory.HUE_YELLOW);
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng brln3 = new LatLng(52.5004, 13.5001);
        getDeviceLocation();
    }

    public void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        Log.d("MapFragment", "Getting the location");
        try {
            final Task location = fusedLocationProviderClient.getLastLocation();

            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        setCurrentLocation(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                    }
                }
            });
        } catch (SecurityException e) {
            Log.d("MapFragment", "Getting the location failed");
        }
    }

    @Override
    public LatLng getLocation() {
        if (this.location == null) {
            getDeviceLocation();
        } else {
            return this.location;
        }
        return new LatLng(50.000, 50.000);
    }

    private void setCurrentLocation(LatLng latLng) {
        location = latLng;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    public void addMarker(LatLng location, float color){
        mMap.addMarker(new MarkerOptions()
                .position(location).title("Icke"))
                .setIcon(BitmapDescriptorFactory.defaultMarker(color));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
