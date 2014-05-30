package com.roskildeapp;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class BandDesciptionActivity extends Activity {

	TextView band, desc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_band_desciption);
		
		band = (TextView) findViewById(R.id.tvBDband);
		band.setText((String) getIntent().getExtras().get("Band"));
		desc = (TextView) findViewById(R.id.tvBDdesc);
		desc.setText((String) getIntent().getExtras().get("Description"));
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

		if(settings.getBoolean("skift tema", true)){
			setActivityBackgroundRecource(R.color.orange);
		}

	}
	
	public void setActivityBackgroundRecource(int color) {
		View view = this.getWindow().getDecorView();
		view.setBackgroundResource(color);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.band_desciption, menu);
		return true;
	}

}
