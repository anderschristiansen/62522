package com.roskildeapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.band_desciption, menu);
		return true;
	}

}
