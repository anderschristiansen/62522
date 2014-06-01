package com.roskildeapp;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.roskildeapp.R.color;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.renderscript.Sampler.Value;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener {
	LinearLayout program, friends, news, map, myPage, gps, weather, compas;
	TextView gpsText;

	ParseUser user = ParseUser.getCurrentUser();

	Location location;
	LocationManager locationManager;
	FetchCoordinates asyncTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		setTitle(ParseUser.getCurrentUser().getUsername());
		setTitleColor(Color.BLUE);
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

		if(settings.getBoolean("skift tema", true)){
			setActivityBackgroundRecource(R.color.orange);
		}

		program = (LinearLayout) findViewById(R.id.btnMenuProgram);
		program.setOnClickListener(this);

		friends = (LinearLayout) findViewById(R.id.btnMenuMyFriends);
		friends.setOnClickListener(this);

		news = (LinearLayout) findViewById(R.id.btnMenuNews);
		news.setOnClickListener(this);

		map = (LinearLayout) findViewById(R.id.btnMenuMap);
		map.setOnClickListener(this);

		myPage = (LinearLayout) findViewById(R.id.btnMenuMyPage);
		myPage.setOnClickListener(this);

		weather = (LinearLayout) findViewById(R.id.btnWeather);
		weather.setOnClickListener(this);

		compas = (LinearLayout) findViewById(R.id.btnCompas);
		compas.setOnClickListener(this);

		gps = (LinearLayout) findViewById(R.id.btnGPS);
		gps.setOnClickListener(this);	

		gpsText = (TextView) findViewById(R.id.txtGPS);
		gpsText.setOnClickListener(this);
	}
	public void setActivityBackgroundRecource(int color) {
		View view = this.getWindow().getDecorView();
		view.setBackgroundResource(color);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnMenuProgram:
			startActivity(new Intent(this,RoskildeProgramActivity.class));
			break;
		case R.id.btnMenuMyFriends:
			startActivity(new Intent(this,MyFriendsActivity.class));
			break;
		case R.id.btnMenuNews:
			startActivity(new Intent(this,RoskildeNewsActivity.class));
			break;
		case R.id.btnMenuMap:
			startActivity(new Intent(this,MapActivity.class));
			break;
		case R.id.btnMenuMyPage:
			Intent i = new Intent(this,MypageActivity.class);
			i.putExtra("Band", "");
			startActivity(i);
			break;
		case R.id.btnWeather:
			startActivity(new Intent(this, WeatherActivity.class));
			break;
		case R.id.btnCompas:
			startActivity(new Intent(this, CompasActivity.class));
			break;
		case R.id.btnGPS:			
			startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);	
		default:
			break;
		}
	}

	public void InitGps(){

		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

		//Check if the GPS is enabled
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

			gpsText.setText("GPS: Tændt");		

			// GPS dialog box
			Dialog d = new Dialog(this);
			d.setTitle("OBS:");
			TextView tv = new TextView(this);
			tv.setText("Din GPS er tændt og din lokation vil blive synlig for venner");
			d.setContentView(tv);
			d.show();	

			// Starting an async task if no async is running 
			if (asyncTask == null)
			{
				asyncTask = new FetchCoordinates();		
				asyncTask.execute();
			}
		}
		else{
			gpsText.setText("GPS: Slukket");		
		}		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		InitGps();
	}	

	public void onBackPressed() {
		finish();
	}


	public class FetchCoordinates extends AsyncTask<String, Void, Void> {

		public double lati = 0.0;
		public double longi = 0.0;
		public LocationManager locationManager;
		public GpsListener gpsListener;

		@Override
		public void onPreExecute() {

			gpsListener = new GpsListener();
			locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener);
		}

		@Override
		protected Void doInBackground(String...params) {

			while(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {

				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);

				System.out.println("LOCATION: " + lati + ", " + longi);

				if (lati != 0.0 && longi != 0.0)
				{
					if (user != null)
					{
						user.put("GPSActive", true);
						user.put("longitude", longi);
						user.put("latitude", lati);
						user.saveInBackground(new SaveCallback() {
							public void done(ParseException e) {
								if (e == null) {
									// Saved successfully
									System.out.println(user.getUsername() + " was saved successfully to Parse.com");
								} else {
									// ParseException
									System.out.println("Exception: " + e);
								}
							}				
						});
					}
				}

				SystemClock.sleep(60000*10);

			}
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}



		public class GpsListener implements LocationListener {

			@Override
			public void onLocationChanged(Location location) {
				lati = location.getLatitude();
				longi = location.getLongitude();				
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
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menuSignOut){
			ParseUser.logOut();
			startActivity(new Intent(this,LoginActivity.class));
			finish();
		}
		if(item.getItemId() == R.id.action_settings){
			startActivity(new Intent(this, SettingsActivity.class));
			finish();
		}
		return false;
	}
}

