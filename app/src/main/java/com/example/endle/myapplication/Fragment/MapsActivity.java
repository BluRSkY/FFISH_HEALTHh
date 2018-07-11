package com.example.endle.myapplication.Fragment;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.endle.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    private static final LatLng Mount_Elizabeth_Hosptial = new LatLng(1.304871, 103.835504);
    private static final LatLng Changi_General_hosptial = new LatLng(1.340717, 103.949749);
    private static final LatLng Tan_Tock_Seng_Hospital = new LatLng(1.320966, 103.846625);
    private static final LatLng Saint_Andrews_Community_Hospital = new LatLng(1.341687, 103.949116);
    private static final LatLng Bright_Vision_Hospital = new LatLng(1.372148, 103.878110);
    private Marker mMount_Elizabeth_Hosptial;
    private Marker mChangi_General_hosptial;
    private Marker mTan_Tock_Seng_Hospital;
    private Marker mSaint_Andrews_Community_Hospital;
    private Marker mBright_Vision_Hospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mChangi_General_hosptial = mMap.addMarker(new MarkerOptions()
                .position(Changi_General_hosptial)
                .title("Changi General hosptial")
                .snippet("Monday - Friday 8:30am - 7:00pm")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMount_Elizabeth_Hosptial = mMap.addMarker(new MarkerOptions()
                .position(Mount_Elizabeth_Hosptial)
                .title("Mount Elizabeth Hosptial")
                .snippet("Monday - Friday 8:30am - 7:00pm")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mTan_Tock_Seng_Hospital = mMap.addMarker(new MarkerOptions()
                .position(Tan_Tock_Seng_Hospital)
                .title("Tan Tock Seng Hosptial")
                .snippet("Monday - Friday 8:30am - 7:00pm")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mSaint_Andrews_Community_Hospital = mMap.addMarker(new MarkerOptions()
                .position(Saint_Andrews_Community_Hospital)
                .title("Saint Andrew's Community Hospital")
                .snippet("Open 24 HOURS")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mBright_Vision_Hospital = mMap.addMarker(new MarkerOptions()
                .position(Bright_Vision_Hospital)
                .title("Bright Vision Hospital")
                .snippet("Monday - Friday 9pm - 9pm")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mChangi_General_hosptial.setTag(0);
        mTan_Tock_Seng_Hospital.setTag(0);
        mMount_Elizabeth_Hosptial.setTag(0);
        mSaint_Andrews_Community_Hospital.setTag(0);
        mBright_Vision_Hospital.setTag(0);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Tan_Tock_Seng_Hospital, 13));

    }
}
