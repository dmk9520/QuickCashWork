package com.example.findmyschool.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.findmyschool.Common.Constant;
import com.example.findmyschool.Common.CurrentLocationListener;
import com.example.findmyschool.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class Location_Get_Activity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, CurrentLocationListener, LocationListener {


    private SupportMapFragment mapFragment;
    GoogleMap googleMap;
    private LatLng sydney;
    private ImageView current_location;
    private TextView select_location;
    private LocationManager locationManager;
    private Location currentLocation;
    private double latitude;
    private double longitude;
    String country;
    String city;
    String state;
    CurrentLocationListener currentLocationListener;
    private static final int LOCATION_MIN_TIME = 30 * 1000;
    private static final int LOCATION_MIN_DISTANCE = 10;
    private ImageView map_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_location_get);
        getLocation();
        initView();
        initListener();
        setCurrentLocationListener(this);
    }

    private void initListener() {
        current_location.setOnClickListener(this);
        select_location.setOnClickListener(this);
        map_back.setOnClickListener(this);
    }

    private void initView() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        current_location = (ImageView) findViewById(R.id.current_location);
        map_back = (ImageView) findViewById(R.id.map_back);
        select_location = (TextView) findViewById(R.id.select_location);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sydney = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(sydney).anchor(0.5f, 1f));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));
            }
        }, 200);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Address address = getAddress(latLng);
                if (address != null) {
                    latitude = latLng.latitude;
                    longitude = latLng.longitude;
                    country = address.getCountryName();
                    state = address.getAdminArea();
                    city = address.getLocality();
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(latLng).anchor(0.5f, 1f));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                }
            }
        });
    }

    public Address getAddress(LatLng latLng) {
        Address address = null;
        Geocoder geocoder = new Geocoder(Location_Get_Activity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            address = addresses.get(0);
        }

        return address;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.current_location:
                getLocation();
                break;
            case R.id.select_location:
                captureScreen();
                break;
            case R.id.map_back:
                onBackPressed();
                break;
        }
    }

    public void captureScreen() {
        Intent intent = new Intent();
        intent.putExtra("lat_long", String.valueOf(new DecimalFormat("##.####").format(latitude)) + "," + String.valueOf(new DecimalFormat("##.####").format(longitude)));
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            /*city = addresses.get(0).getAddressLine(0);
            state = addresses.get(0).getAddressLine(1);*/
            String address = addresses.get(0).getAddressLine(0);
            intent.putExtra("city", city);
            intent.putExtra("state", state);
            intent.putExtra("country", country);
            intent.putExtra("address", address);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void setCurrentLocationListener(Location_Get_Activity currentLocationListener) {
        this.currentLocationListener = currentLocationListener;
    }

    public void getLocation() {
        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                && checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, false);
            locationManager.requestLocationUpdates(bestProvider, LOCATION_MIN_TIME, LOCATION_MIN_DISTANCE, this);

            Location gpsLocation = locationManager.getLastKnownLocation(bestProvider);
            Location networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (gpsLocation != null) {
                currentLocation = gpsLocation;
                onLocationChanged(currentLocation);
            } else if (networkLocation != null) {
                currentLocation = networkLocation;
                onLocationChanged(currentLocation);
            } else {
                showLocationOnDialog();
            }
        } else {
            Toast.makeText(Location_Get_Activity.this, "Permission not allowed", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkPermission(String permissionName) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(Location_Get_Activity.this, permissionName);
    }

    public void onLocationChanged(Location location) {
        try {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Address address = getAddress(new LatLng(latitude, longitude));
            country = address.getCountryName();
            state = address.getAdminArea();
            city = address.getLocality();
            if (currentLocationListener != null) {
                currentLocationListener.currentLocationLatLong(new LatLng(latitude, longitude));
            }
        } catch (NullPointerException e) {
            currentLocationListener.getCurrentLocationFaild();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void showLocationOnDialog() {
        final Dialog locationOnDialog = new Dialog(Location_Get_Activity.this, R.style.PauseDialog);
        locationOnDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        locationOnDialog.setContentView(R.layout.dialog_location_on);
        locationOnDialog.setCancelable(false);

        TextView actionOnLocation = locationOnDialog.findViewById(R.id.actionOnLocation);

        actionOnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), Constant.REQUEST_LOCATION_ON);
                locationOnDialog.dismiss();
            }
        });

        locationOnDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_LOCATION_ON && resultCode == RESULT_OK) {
            getLocation();
        }
    }

    @Override
    public void currentLocationLatLong(LatLng latLng) {
        googleMap.clear();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
        googleMap.addMarker(new MarkerOptions().position(latLng).anchor(0.5f, 1f));
    }

    @Override
    public void getCurrentLocationFaild() {

    }
}
