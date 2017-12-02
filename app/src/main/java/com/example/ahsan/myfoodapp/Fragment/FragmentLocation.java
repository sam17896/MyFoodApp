package com.example.ahsan.myfoodapp.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahsan.myfoodapp.R;
import com.example.ahsan.myfoodapp.utilities.Preference;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.acl.Permission;

public class FragmentLocation extends Fragment implements Permission {
    int ARRAY = 1;
    int PLACES = 2;
    Activity activity;
    private FloatingActionButton floatingActionButton;
    private GoogleMap googleMap;
    Double latitude;
    Preference preference;
    private LocationListener listener = new LocationListener() {
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }

        public void onLocationChanged(Location location) {
            Log.e("INFO", "location update : " + location);
        }
    };
    private Location location;
    private LocationManager locationManager;
    Double longitude;
    private MapView mapView;
    String[] maps;
    int mode;
    private ProgressDialog progressDialog;
    String query;
    private RelativeLayout relativeLayout;
    private EditText textView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_location, container, false);
        setHasOptionsMenu(true);
        preference = new Preference(getActivity());
        this.mapView =  this.relativeLayout.findViewById(R.id.map);
        this.mapView.onCreate(savedInstanceState);
        this.mapView.onResume();
        textView = relativeLayout.findViewById(R.id.edtName);
        textView.setText(preference.getPin());
        this.floatingActionButton = this.relativeLayout.findViewById(R.id.navigate);
        this.floatingActionButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String regexStr = "^[0-9]{5}$";
                if(textView.getText().toString().trim().matches(regexStr)){
                    preference.setPin("");
                    mapView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(activity, "Invalid Code", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return this.relativeLayout;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.activity = getActivity();
        MapsInitializer.initialize(this.activity);
        this.mapView.getMapAsync(new OnMapReadyCallback() {
            public void onMapReady(GoogleMap googleMap) {
                FragmentLocation.this.googleMap = googleMap;
                    FragmentLocation.this.latitude =24.946218 ;
                    FragmentLocation.this.longitude =67.005615 ;
                    LatLng loc = new LatLng(latitude.doubleValue(),longitude.doubleValue() );
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                    googleMap.addMarker(new MarkerOptions().title("Your Order").snippet("Be patient").position(loc)).showInfoWindow();
                    FragmentLocation.this.textView = FragmentLocation.this.relativeLayout.findViewById(R.id.edtName);
                  //  FragmentLocation.this.textView.setText(Html.fromHtml(FragmentLocation.this.maps[0]));
               }
        });
    }

    public void onResume() {
        super.onResume();
        this.mapView.onResume();
    }

    public void onPause() {
        super.onPause();
        this.mapView.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mapView.onDestroy();
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.mapView.onLowMemory();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (this.mode == this.ARRAY) {
            inflater.inflate(R.menu.main, menu);
        }
    }

    public String[] requiredPermissions() {
        return new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE"};
    }
}
