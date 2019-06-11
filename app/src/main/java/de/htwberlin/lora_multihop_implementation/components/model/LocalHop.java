package de.htwberlin.lora_multihop_implementation.components.model;

public class LocalHop {

    private String address;
    private double latitude;
    private double longitude;
    public static LocalHop instance = null;

    public static LocalHop getInstance() {
        if (instance == null) instance = new LocalHop();
        return instance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LocalHop{" +
                "address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
