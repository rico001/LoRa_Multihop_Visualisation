package de.htwberlin.lora_multihop_implementation.interfaces;

import com.google.android.gms.maps.model.LatLng;

public interface IMapFragmentListener {
    LatLng getLocation();
    void addHostMarker(LatLng location, String id, int radius);
}
