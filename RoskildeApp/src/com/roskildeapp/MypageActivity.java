package com.roskildeapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MypageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypage);
		
		String band = getIntent().getExtras().getString("Band");
		System.out.println(band);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mypage, menu);
		return true;
	}

}
