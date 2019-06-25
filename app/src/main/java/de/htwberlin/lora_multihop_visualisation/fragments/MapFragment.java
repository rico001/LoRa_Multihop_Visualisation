package de.htwberlin.lora_multihop_visualisation.fragments;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
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

import java.util.HashMap;
import java.util.Map;

import de.htwberlin.lora_multihop_logic.NeighbourSetDataHandler;
import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_logic.enums.EFragments;
import de.htwberlin.lora_multihop_visualisation.LoRaApplication;
import de.htwberlin.lora_multihop_visualisation.MainActivity;
import de.htwberlin.lora_multihop_visualisation.R;

public class MapFragment extends Fragment implements OnMapReadyCallback, NeighbourSetDataHandler.INeighbourSetData {

    private static final String TAG = "MapFragment";

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private MapView mapView;
    private Button terminalButton;
    private LatLng location;

    private Map<String, Marker> markers;
    private Map<String, Circle> circles;

    private IMapListener listener;

    public MapFragment() {
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
        // getDeviceLocation();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(getLocation()));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));

        // onMapReady() is called after onResume() therefore the mMap variable is
        // overwritten with a new map. We have to call the onSetUpMap() here so that
        // the markers are again on the map
        this.listener.onSetUpMap();

        // We ask for permission in the main activity
        // mMap.setMyLocationEnabled(true);
    }

    /*private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        Log.d("MapFragment", "Getting the location");

        try {
            final Task locationTask = fusedLocationProviderClient.getLastLocation();

            locationTask.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        //location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        location = new LatLng(50.232, 40.232);
                        // setCurrentLocation(location);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                    }
                }
            });
        } catch (SecurityException e) {
            Log.d("MapFragment", "Getting the location failed");
        }
    }*/

    /**
     * Returns the actual location, default 50.000 - 50.000
     *
     * @return
     */
    public LatLng getLocation() {
        LocationManager lm = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        /*Log.d("Main", Double.toString(location.getLatitude()));
        if (this.location == null) {
            getDeviceLocation();
        } else {
            return this.location;
        }*/
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    private void addMarker(LatLng location, String id, int radius, String title, String description, int markerBitmap, int circleFillColor, int circleStrokeColor) {
        if (mMap != null && this.markers.get(id) == null) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(title)
                    .snippet(description)
                    .icon(BitmapDescriptorFactory.fromResource(markerBitmap)));

            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(location)
                    .radius(radius)
                    .strokeWidth(3)
                    .fillColor(circleFillColor)
                    .strokeColor(circleStrokeColor));

            this.markers.put(id, marker);
            this.circles.put(id, circle);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        }
    }

    /**
     * Adds a purple marker to the map
     *
     * @param location
     * @param id
     * @param radius
     */
    public void addHostMarker(LatLng location, String id, int radius) {
        addHostMarker(location, id, radius, "", "");
    }

    /**
     * Adds a purple marker to the map
     *
     * @param location
     * @param id
     * @param radius
     * @param title
     * @param description
     */
    public void addHostMarker(LatLng location, String id, int radius, String title, String description) {
        // Get map null
        addMarker(location, id, radius, title, description, R.drawable.host_marker, Color.argb(30, 98, 2, 238), Color.rgb(98, 2, 238));

    }

    /**
     * Adds a green marker to the map
     *
     * @param location
     * @param id
     * @param radius
     */
    public void addNeighbourMarker(LatLng location, String id, int radius) {
        addNeighbourMarker(location, id, radius, "", "");
    }

    /**
     * Adds a green marker to the map
     *
     * @param location
     * @param id
     * @param radius
     * @param title
     * @param description
     */
    public void addNeighbourMarker(LatLng location, String id, int radius, String title, String description) {
        // Get map null
        addMarker(location, id, radius, title, description, R.drawable.neighbour_marker, Color.argb(30, 1, 163, 153), Color.rgb(1, 163, 153));

    }

    /**
     * Removes a marker from the map
     *
     * @param id
     */
    public void removeMarker(String id) {
        if (this.markers.containsKey(id)) {
            this.markers.remove(id);
            this.circles.remove(id);
        }
    }

    public void removeAll() {
        this.markers.clear();
        this.circles.clear();
    }

    /**
     * Returns all the markers on the map
     *
     * @return
     */
    public Map<String, Marker> getMarkers() {
        return this.markers;
    }

    /**
     * Called when the Fragment is attached to the main activity
     * and sets up the listener
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof IMapListener) {
            this.listener = (IMapListener) context;
        } else {
            throw new RuntimeException(context.toString() + " has to implement the IMapListener interface.");
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        LoRaApplication.getDbRepo().getAllNeighbourSets(this);
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
        this.markers.clear();
        this.circles.clear();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveNeighbourSet(NeighbourSet neighbourSet) {
        LatLng location = new LatLng(neighbourSet.getLatitude(), neighbourSet.getLongitude());
        addHostMarker(location, String.valueOf(neighbourSet.getUid()), LoRaApplication.RADIUS);
    }

    @Override
    public void onUpdateNeighbourSet(NeighbourSet neighbourSet) {

    }

    @Override
    public void onDeleteNeighbourSet(int uid) {

    }

    @Override
    public void onClearTable() {
        this.markers.clear();
        this.circles.clear();
    }

    /**
     * Interface to communicate with the activity
     */
    public interface IMapListener {
        void onSetUpMap();
    }
}
