package com.roskildeapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.parse.Parse;
import com.parse.ParseAnalytics;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
			  Parse.initialize(this, "it7c4C7WBcjWeGs7utRUcSX7MaVKu5sOPIGDwt9c", "at1cvsQHPjlhVIRUx1BzuwArJn6VEMIY1KHQOVYO");
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
