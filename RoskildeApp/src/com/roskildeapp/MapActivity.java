package com.roskildeapp;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class MapActivity extends Activity {

	GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		String username = getIntent().getStringExtra("username");
		double latitude = getIntent().getDoubleExtra("latitude", 0);
		double longitude = getIntent().getDoubleExtra("longitude", 0);
		String date = getIntent().getStringExtra("updated");
		
		System.out.println("username: " + username + ", latitude: " + latitude + ", longitude; " + longitude + ", date: " + date);
		
		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initilizeMap() {
		
		GpsListener gpsListener;
		
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			googleMap.setMyLocationEnabled(true);
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			gpsListener = new GpsListener();
			
			Criteria criteria = new Criteria(); 
			String provider = locationManager.getBestProvider(criteria, true);

			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener);
			
			// Getting Current Location
			Location location = locationManager.getLastKnownLocation(provider);	
			
			if(location == null){
		
				Log.d("APpln", "location: >>"+location);
				locationManager.requestLocationUpdates(provider, 20000, 0, gpsListener);

			}else{
				double lat= location.getLatitude();
				double lng = location.getLongitude();

				LatLng ll = new LatLng(lat, lng);

				googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 20));
				
				this.googleMap.addMarker(new MarkerOptions().position(new LatLng(55.75571805, 12.52080712)).title("userName").snippet("createdTime"));
			}  

			
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
	
	public class GpsListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			double lat= location.getLatitude();
			double lng = location.getLongitude();
			LatLng ll = new LatLng(lat, lng);
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 20));     				
		}

		@Override
		public void onProviderDisabled(String provider) {
			System.out.println("OnProviderDisabled");
		}

		@Override
		public void onProviderEnabled(String provider) {
			System.out.println("onProviderEnabled");
		}

		@Override
		public void onStatusChanged(String provider, int status,
				Bundle extras) {
			System.out.println("onStatusChanged");

		}
	}



	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}
}


