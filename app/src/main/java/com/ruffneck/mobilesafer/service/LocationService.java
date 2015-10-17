package com.ruffneck.mobilesafer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

public class LocationService extends Service {


    private SharedPreferences mPref;
    private LocationManager manager;
    private MyListener myListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mPref = getSharedPreferences("config", MODE_PRIVATE);
        System.out.println("location");

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = manager.getAllProviders();

        System.out.println(providers);

        myListener = new MyListener();

        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        String bestProvider = manager.getBestProvider(criteria, true);

        manager.requestLocationUpdates(bestProvider, 0, 0, myListener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.removeUpdates(myListener);
    }

    class MyListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            StringBuilder sb = new StringBuilder();

            sb.append("纬度:").append(location.getLatitude()).append("\n");
            sb.append("经度:").append(location.getLongitude()).append("\n");
            sb.append("精确度:").append(location.getAccuracy()).append("\n");
            sb.append("海拔:").append(location.getAltitude());

            System.out.println(sb.toString());

            mPref.edit().putString("location",sb.toString()).apply();

            stopSelf();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            System.out.println("State has change!");
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
