package com.roskildeapp;

import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MenuActivity extends Activity implements OnClickListener{
	Button program, friends, news, map, myPage, gps;
	LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		program = (Button) findViewById(R.id.btnMenuProgram);
		program.setOnClickListener(this);

		friends = (Button) findViewById(R.id.btnMenuMyFriends);
		friends.setOnClickListener(this);

		news = (Button) findViewById(R.id.btnMenuNews);
		news.setOnClickListener(this);

		map = (Button) findViewById(R.id.btnMenuMap);
		map.setOnClickListener(this);

		myPage = (Button) findViewById(R.id.btnMenuMyPage);
		myPage.setOnClickListener(this);

		gps = (Button) findViewById(R.id.btnGPS);
		gps.setOnClickListener(this);	
				
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            gps.setText("GPS: Tændt");
        }else{
            gps.setText("GPS: Slukket");
        }			
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
			startActivity(new Intent(this,MypageActivity.class));
			break;
		case R.id.btnGPS:			
			startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);

		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            gps.setText("GPS: Tændt");
        }else{
            gps.setText("GPS: Slukket");
        }
	}

	public void onBackPressed() {
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
}
