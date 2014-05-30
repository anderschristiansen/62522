package com.roskildeapp;

import com.parse.Parse;

import android.os.Bundle;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;


public class MypageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypage);

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

		if(settings.getBoolean("skift tema", true)){
			setActivityBackgroundRecource(R.color.orange);
		}
		
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		FragmentInfo infoFragment = new FragmentInfo();
		ft.add(R.id.fragment_place, infoFragment);
		ft.commit();
	}

	public void setActivityBackgroundRecource(int color) {
		View view = this.getWindow().getDecorView();
		view.setBackgroundResource(color);
	}
	
	public void selectFrag(View view) {
		Fragment frAdd;
		if(view == findViewById(R.id.btnMPinfo)) {
			frAdd = new FragmentInfo();
		}else {
			frAdd = new FragmentProgram();

		}
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragment_place, frAdd);
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fragmentTransaction.commit();
	}


}
