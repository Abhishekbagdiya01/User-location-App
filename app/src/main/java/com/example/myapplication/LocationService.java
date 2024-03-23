package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.IBinder;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

public class LocationService extends Service {
    FusedLocationProviderClient fusedLocationProviderClient = null;
    LocationCallback locationCallback = null;
    LocationRequest locationRequest= null;
   Location location=null;
    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
              locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000).setIntervalMillis(500).build();
              locationCallback = new LocationCallback() {
                  @Override
                  public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                      super.onLocationAvailability(locationAvailability);
                  }

                  @Override
                  public void onLocationResult(@NonNull LocationResult locationResult) {
                      super.onLocationResult(locationResult);
                      onNewLocation(locationResult);
                  }
              };
    }

    @SuppressWarnings("MissingPermission")
    void createLocationRequest(){
        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,locationCallback,null
        );
    }
    void removeLocationRequest(){
        fusedLocationProviderClient.removeLocationUpdates(
                locationCallback
        );
    }
    private void onNewLocation(LocationResult locationResult) {

        location = locationResult.getLastLocation();
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        try{
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(lat,lng ,1);
            String address = addressList.get(0).getAddressLine(0);

            LocationEvent event =  new LocationEvent(lat,lng,address);
            EventBus.getDefault().post(event);
        }catch (Exception e){
            e.printStackTrace();
        }


//        LocationEvent event =  new LocationEvent(lat,lng,);
//        EventBus.getDefault().post(event);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        createLocationRequest();
        return  START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeLocationRequest();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}