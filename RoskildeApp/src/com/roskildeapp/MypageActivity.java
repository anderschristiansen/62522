package com.roskildeapp;

import android.os.Bundle;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


public class MypageActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypage);

		//		String band = getIntent().getExtras().getString("Band");
		//		System.out.println(band);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mypage, menu);
		return true;
	}

	public void selectFrag(View view) {
		Fragment fr;
		if(view == findViewById(R.id.button2)) {
			fr = new FragmentMap();
		}else {
			fr = new FragmentProgram();

		}
		FragmentManager fm = getFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_place, fr);
		fragmentTransaction.commit();
	}


}
