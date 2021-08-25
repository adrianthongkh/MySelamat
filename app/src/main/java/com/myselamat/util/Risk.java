package com.myselamat.util;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import com.myselamat.database.PremisesDatabase;
import com.myselamat.model.Premises;

import java.util.ArrayList;

public class Risk {

    public Premises findClosestInfected(LatLng latLng, ArrayList<Premises> premises) {

        Location start = new Location("");
        start.setLatitude(latLng.latitude);
        start.setLongitude(latLng.longitude);

        double distance = 500;      // define radius of 500m
        Premises premise = new Premises();

        for (Premises p : premises) {

            if (!p.isStatus())
                continue;

            Location end = new Location("");
            end.setLatitude(p.getPosition().getLatitude());
            end.setLongitude(p.getPosition().getLongitude());

            double result = start.distanceTo(end);

            if (result < distance) {
                distance = result;
                premise = p;
            }
        }

        return premise;
    }

    public double getDistance(LatLng latLng, Premises premises) {

        Location start = new Location("");
        start.setLatitude(latLng.latitude);
        start.setLongitude(latLng.longitude);

        Location end = new Location("");
        end.setLatitude(premises.getPosition().getLatitude());
        end.setLongitude(premises.getPosition().getLongitude());


        return start.distanceTo(end);
    }

    public double calculateRisk(LatLng latLng, Premises p) {

        double distance = 500;

        if (p.getPosition() != null)
            distance = getDistance(latLng, p);
        else
            return 0;

        return (1 - distance/500) * 100;
    }
}