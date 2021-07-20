package util;

import com.google.android.gms.maps.model.LatLng;

import model.Premises;

public class Risk {

    public Premises findClosestInfected(LatLng latLng) {
        // TODO: implement find closest infected premise
        return new Premises();
    }

    public double getDistance(LatLng latLng) {
        Premises premise = findClosestInfected(latLng);
        // TODO: implement get distance from the premise
        return 0.00;
    }

    public double calculateRisk(LatLng latLng) {

        double distance = getDistance(latLng);

        return (1 - distance/500) * 100;
    }
}