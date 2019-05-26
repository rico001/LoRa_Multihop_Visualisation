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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

import de.htwberlin.lora_multihop_implementation.enums.EFragments;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private MapView mapView;
    private Button terminalButton;
    private LatLng location;

    private Map<String, Marker> markers;
    private Map<String, Circle> circles;

    public MapFragment() {
        // Required empty public constructor
        markers = new HashMap<>();
        circles = new HashMap<>();
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
                ((MainActivity) getActivity()).setViewPager(EFragments.TERMINAL_FRAGMENT.get());
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();

        // We ask for permission in the main activity
        // mMap.setMyLocationEnabled(true);
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
                        addHostMarker(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), "me", 1000, "This is a title", "This is a description");
                    }
                }
            });
        } catch (SecurityException e) {
            Log.d("MapFragment", "Getting the location failed");
        }
    }

    /**
     * Returns the actual location, default 50.000 - 50.000
     * @return
     */
    public LatLng getLocation() {
        if (this.location == null) {
            getDeviceLocation();
        } else {
            return this.location;
        }
        return new LatLng(50.000, 50.000);
    }

    /**
     * Helper method to set the current location
     * @param latLng
     */
    private void setCurrentLocation(LatLng latLng) {
        this.location = latLng;
    }

    /**
     * Adds a purple marker to the map
     * @param location
     * @param id
     * @param radius
     */
    public void addHostMarker(LatLng location, String id, int radius) {
        addHostMarker(location, id, radius, "", "");
    }

    /**
     * Adds a purple marker to the map
     * @param location
     * @param id
     * @param radius
     * @param title
     * @param description
     */
    public void addHostMarker(LatLng location, String id, int radius, String title, String description) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title(title)
                            .snippet(description)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.host_marker)));

        Circle circle = mMap.addCircle(new CircleOptions()
                            .center(location)
                            .radius(radius)
                            .strokeWidth(3)
                            .fillColor(Color.argb(30, 98, 2, 238))
                            .strokeColor(Color.rgb(98, 2, 238)));

        this.markers.put(id, marker);
        this.circles.put(id, circle);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
    }

    /**
     * Removes a marker from the map
     * @param id
     */
    public void removeMarker(String id) {
        if (this.markers.containsKey(id)) {
            this.markers.remove(id);
            this.circles.remove(id);
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
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
