package com.example.mapsapp;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    SearchView searchView1;
    SearchView searchView2;
    SearchView searchView3;
    Button confirmButton;

    Polygon polygon = null;
    List<LatLng> latLngList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        // Find all the views
        searchView1 = findViewById(R.id.sv_location1);
        searchView2 = findViewById(R.id.sv_location2);
        searchView3 = findViewById(R.id.sv_location3);
        confirmButton = findViewById(R.id.confirm);

        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView1.getQuery().toString();
                List<Address> addressList1 = null;

                if(location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try{
                        addressList1 = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert addressList1 != null;
                    Address address1 = addressList1.get(0);
                    LatLng latLng1 = new LatLng(address1.getLatitude(), address1.getLongitude());
                    latLngList.add(latLng1);

                    mMap.addMarker(new MarkerOptions().position(latLng1).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 15));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //addressList1.remove(0);
                //onQueryTextSubmit(newText);
                return false;
            }
        });

        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView2.getQuery().toString();
                List<Address> addressList2 = null;
                if(location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try{
                        addressList2 = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert addressList2 != null;
                    Address address2 = addressList2.get(0);
                    LatLng latLng2 = new LatLng(address2.getLatitude(), address2.getLongitude());
                    latLngList.add(latLng2);

                    mMap.addMarker(new MarkerOptions().position(latLng2).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 15));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //addressList2.remove(0);
                //onQueryTextSubmit(newText);
                return false;
            }
        });

        searchView3.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView3.getQuery().toString();
                List<Address> addressList3 = null;

                if(location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try{
                        addressList3 = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    assert addressList3 != null;
                    Address address3 = addressList3.get(0);
                    LatLng latLng3 = new LatLng(address3.getLatitude(), address3.getLongitude());
                    latLngList.add(latLng3);

                    mMap.addMarker(new MarkerOptions().position(latLng3).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng3, 15));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //addressList3.remove(0);
                //onQueryTextSubmit(newText);
                return false;
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PolygonOptions polygonOptions= new PolygonOptions().addAll(latLngList).clickable(true);
                polygon = mMap.addPolygon(polygonOptions);

                // Colours specified by hex digits A(FF), R(00), G(FF), B(00)
                // To make it translucent, divide alpha channel by 2
                polygon.setFillColor(0x7F00FF00);
                polygon.setStrokeColor(0x7F00FF00);

                // Calculated centre point
                LatLng point1 = latLngList.get(0);
                LatLng point2 = latLngList.get(1);
                LatLng point3 = latLngList.get(2);
                double centreLat = (point1.latitude + point2.latitude + point3.latitude)/3;
                double centreLng = (point1.longitude + point2.longitude + point3.longitude)/3;
                LatLng centre = new LatLng(centreLat, centreLng);

                // Change v to set the amount to zoom
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centre,14));

                // Display notification of centre point
                //Toast notify = new Toast(MapsActivity.this);
                //notify.setText("The centre point is: " + centre.toString());
                //notify.show();
            }
        });


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

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //LatLng singapore = new LatLng(1.3521, 103.8198);
        //mMap.addMarker(new MarkerOptions().position(singapore).title("Marker in Singapore"));
    }
}