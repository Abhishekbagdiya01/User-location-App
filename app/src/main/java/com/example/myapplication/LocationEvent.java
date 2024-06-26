package com.example.myapplication;

public class LocationEvent {
    private final double lat;
    private final double lng;

    private final String address;

    public LocationEvent(double lat, double lng, String address) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getAddress() {
        return address;
    }
}
