package com.roskildeapp;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MapActivity extends Activity {

	GoogleMap googleMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			googleMap.setMyLocationEnabled(true);
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			Criteria criteria = new Criteria(); 
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location
			Location location = locationManager.getLastKnownLocation(provider);

			if(location == null){
				LocationListener locationListener = new LocationListener() {

					@Override
					public void onLocationChanged(Location location) {
						double lat= location.getLatitude();
						double lng = location.getLongitude();
						LatLng ll = new LatLng(lat, lng);
						googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 20));                    
					}
					@Override
					public void onProviderDisabled(String provider) {}
					@Override
					public void onProviderEnabled(String provider) {}
					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {}
				};
				Log.d("APpln", "location: >>"+location);
				locationManager.requestLocationUpdates(provider, 20000, 0, locationListener);

			}else{
				double lat= location.getLatitude();
				double lng = location.getLongitude();

				LatLng ll = new LatLng(lat, lng);

				googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 20));
			}


			// Define a listener that responds to location updates
			LocationListener locationListener = new LocationListener() {
				public void onLocationChanged(Location location) {

					// Called when a new location is found by the network location provider.
					makeUseOfNewLocation(location);
				}

				private void makeUseOfNewLocation(Location location) {
					double lat = location.getLatitude();
					double lng = location.getLongitude();
					LatLng ll = new LatLng(lat, lng);				

//				    Context context = getApplicationContext();
//				    CharSequence text = "Lokation: \nBreddegrad: " + lat + "\nLængdegrad: " + lng;
//				    int duration = Toast.LENGTH_SHORT;
//
//				    Toast toast = Toast.makeText(context, text, duration);
//				    toast.show();

				}

				public void onStatusChanged(String provider, int status, Bundle extras) {}

				public void onProviderEnabled(String provider) {}

				public void onProviderDisabled(String provider) {}
			};

			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);      


			// check if map is created successfully or not
			if (googleMap == null) {
				
				Context context = getApplicationContext();
			    CharSequence text = "Unable to create maps";
			    int duration = Toast.LENGTH_SHORT;

			    Toast toast = Toast.makeText(context, text, duration);
			    toast.show();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}
}


