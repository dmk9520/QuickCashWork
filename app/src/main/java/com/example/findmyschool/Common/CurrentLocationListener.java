package com.example.findmyschool.Common;

import com.google.android.gms.maps.model.LatLng;

public interface CurrentLocationListener {

    void currentLocationLatLong(LatLng latLng);

    void getCurrentLocationFaild();
}
