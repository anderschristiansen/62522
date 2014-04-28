package com.roskildeapp;

import com.parse.Parse;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashActivity extends Activity implements Runnable {

	Handler handler = new Handler();
	static SplashActivity aktivitetDerVisesNu = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Parse.initialize(this, "1PZGZUbJ7AyTkIZTOwMfXxdFRpkbJwo0MoB4J6im", "rkXj2TIHo48jhTXSX9YQthqXYseSSWzUBGT2HoqF");

		System.out.println(getClass().getSimpleName() + ": aktiviteten blev startet!");

		// Hvis savedInstanceState ikke er null er det en aktivitet der er ved at blive genstartet
		if (savedInstanceState == null) {
			handler.postDelayed(this, 1500); // <1> Kør run() om 1,5 sekunder
		}
		aktivitetDerVisesNu = this;
	}

	public void run() {
		startActivity(new Intent(this, LoginActivity.class));
		aktivitetDerVisesNu.finish();  // <2> Luk velkomsaktiviteten
		aktivitetDerVisesNu = null;
	}

	/**
	 * Kaldes hvis brugeren trykker på tilbage-knappen.
	 * I så tilfælde skal vi ikke hoppe videre til næste aktivitet
	 */
	@Override
	public void finish() {
		super.finish();
		handler.removeCallbacks(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}