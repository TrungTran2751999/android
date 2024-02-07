package com.huewaco.cskh.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.huewaco.cskh.helper.CommonHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AMap extends AParent implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    //ArrayList<MarkerOptions> mArrMakers = new ArrayList<MarkerOptions>();
    private GoogleMap myMap;
    public static String title="";
    public static double kinhDo = 0;
    public static double vido = 0;
    public static boolean ALLOW_ADD_MARKER = false;
    private  MarkerOptions currentMarkerOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ABaoCaoSuCo.COME_BACK_FROM_AMAP = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initComponent();
        addListener();

        if(ALLOW_ADD_MARKER){
            CommonHelper.showWarning(AMap.this,getString(R.string.kh_changed_address));
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AMap.ALLOW_ADD_MARKER = false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        if(ALLOW_ADD_MARKER){
            myMap.setOnMapClickListener(this);
        }
        /*
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {

                if(!marker.getTitle().equalsIgnoreCase(title)){
                    marker.remove();
                }

            }
        });
        */
//        myMap.setOnMapLongClickListener(this);
        showMap();
    }
    private void showMap(){
        LatLng latLng = new LatLng(vido, kinhDo);
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)             // Sets the center of the map to location user
                .zoom(15)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        // Thêm Marker cho Map:
        currentMarkerOptions = new MarkerOptions();
        currentMarkerOptions.title(title);
        currentMarkerOptions.position(latLng);
        Marker currentMarker = myMap.addMarker(currentMarkerOptions);
        currentMarker.showInfoWindow();
        //mArrMakers.add(currentMarkerOptions);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(ALLOW_ADD_MARKER){
            showAddress_kh_chon(latLng);
        }

    }
    private void showAddress_kh_chon(LatLng latLng){
        //mArrMakers.clear();
        myMap.clear();//xoa ca mobile + user
        myMap.addMarker(currentMarkerOptions); //add mobile
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {

            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            //CommonHelper.showWarning(AMap.this,address);
            MarkerOptions option = new MarkerOptions();
            option.title(address);
            option.position(latLng);
            Marker currentMarker = myMap.addMarker(option);//add user
            //mArrMakers.add(option);
            //mArrMakers.add(currentMarkerOptions);

            currentMarker.showInfoWindow();
            ABaoCaoSuCo.DIACHI_KH_CHON = address;
            ABaoCaoSuCo.KINHDO_KH_CHON = latLng.longitude;
            ABaoCaoSuCo.VIDO_KH_CHON = latLng.latitude;

//            for(MarkerOptions m : mArrMakers){
//                myMap.addMarker(option);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //CommonHelper.showWarning(AMap.this,String.valueOf(latLng.latitude));

        showAddress_kh_chon(latLng);
    }
}
