package com.example.somesh.locateall;

import android.location.Location;

/**
 * Created by somesh on 4/30/17.
 */

public class ARPoint {
    Location location;
    String name;

    public ARPoint(String name, double lat, double lon, double altitude) {
        this.name = name;
        location = new Location("ARPoint");
        location.setLatitude(lat);
        location.setLongitude(lon);
        location.setAltitude(altitude);
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return name+" "+getLocation().getLatitude()+","+getLocation().getLongitude();
    }
}


