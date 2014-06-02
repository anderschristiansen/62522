package com.roskildeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;

public class SettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

		if(settings.getBoolean("skift tema", true)){
			setActivityBackgroundRecource(R.color.orange);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public void setActivityBackgroundRecource(int color) {
		View view = this.getWindow().getDecorView();
		view.setBackgroundResource(color);
	}
	
	public void onBackPressed() {
		startActivity(new Intent(this,MenuActivity.class));
		finish();
	}

}

