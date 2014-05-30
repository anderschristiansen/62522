package com.roskildeapp;

import java.util.List;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.roskildeapp.database.DatabaseHandler;
import com.roskildeapp.objects.User;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {

	EditText brugernavn, password;
	Button login, opretBruger;
	ProgressBar pbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

		if(settings.getBoolean("skift tema", true)){
			setActivityBackgroundRecource(R.color.orange);
		}
		
		if (ParseUser.getCurrentUser() != null) {
			Intent i = new Intent(this, MenuActivity.class);
			startActivity(i);
			finish();
		} 

		brugernavn = (EditText) findViewById(R.id.etBrugernavn);
		password = (EditText) findViewById(R.id.etPassword);

		login = (Button) findViewById(R.id.btnLogin);
		login.setOnClickListener(this);

		opretBruger = (Button) findViewById(R.id.btnOpretBruger);
		opretBruger.setOnClickListener(this);

		pbar = (ProgressBar) findViewById(R.id.pbarLogin);
		pbar.setVisibility(8);
	}

	public void setActivityBackgroundRecource(int color) {
		View view = this.getWindow().getDecorView();
		view.setBackgroundResource(color);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {

		String name = brugernavn.getText().toString().trim();
		String pass = password.getText().toString().trim();
		final Context context = this;
		final Intent i = new Intent(this, MenuActivity.class);

		switch (arg0.getId()) {
		case R.id.btnLogin:

			//login
			pbar.setVisibility(0);
			ParseUser.logInInBackground(name, pass, new LogInCallback() {
				public void done(ParseUser user, ParseException e) {
					if (user != null) {
						startActivity(i);
						finish();
					} else {
						Dialog d = new Dialog(context);
						d.setTitle("Fejl!");
						TextView tv = new TextView(context);
						tv.setText("Forkert brugernavn eller password!");
						d.setContentView(tv);
						d.show();	
						pbar.setVisibility(8);
					}
				}
			});

			break;
		case R.id.btnOpretBruger:

			try{
				ParseUser user = new ParseUser();
				user.setUsername(name);
				user.setPassword(pass);		
				user.put("GPSActive", false);
				user.put("longitude", 0);
				user.put("latitude", 0);
				
				pbar.setVisibility(0);
				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						if (e == null) {
							startActivity(i);
							finish();
						} else {
							Dialog d = new Dialog(context);
							d.setTitle("Der skete en fejl!");
							TextView tv = new TextView(context);
							tv.setText("Fejl: " + e.toString());
							d.setContentView(tv);
							d.show();
						}
					}
				});
			}
			catch (Exception e) {
				Dialog d = new Dialog(context);
				d.setTitle("Der skete en fejl!");
				TextView tv = new TextView(context);
				tv.setText("Hvis du vil oprette en bruger, skal du bare indtaste dit ¿nskede brugernavn og password samme" +
						" steder, som hvis du skulle logge ind!");
				d.setContentView(tv);
				d.show(); 
			}
			break;
		default:
			break;
		}

	}

}
