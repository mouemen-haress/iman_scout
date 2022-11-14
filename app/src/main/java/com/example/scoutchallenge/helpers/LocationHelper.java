package com.example.scoutchallenge.helpers;

import static android.content.Context.LOCATION_SERVICE;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.util.List;
import java.util.Locale;

public class LocationHelper implements LocationListener {
    double homeX;
    double homeY;

    public void container() {
        homeX = 34.4335299;
        homeY = 35.8496285;


//        try {
//            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
//        try {
//            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            String address = addresses.get(0).getAddressLine(0);
//
//
//            text.setText(location.getLatitude() + "");
//
//            if (distance(homeX, homeY, location.getLatitude(), location.getLongitude()) < 0.00310686) { // if distance < 0.1 miles we take locations as equal
//                //do what you want to do...
//                text.setText("inside");
//            } else {
//
//                text.setText("outside");
//            }
//
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {

    }

    @Override
    public void onFlushComplete(int requestCode) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @NonNull
    public CreationExtras getDefaultViewModelCreationExtras() {
        return null;
    }

    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }
}
