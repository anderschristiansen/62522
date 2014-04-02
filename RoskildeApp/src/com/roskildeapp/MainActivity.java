package com.roskildeapp;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roskildeapp.objects.User;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "1PZGZUbJ7AyTkIZTOwMfXxdFRpkbJwo0MoB4J6im", "rkXj2TIHo48jhTXSX9YQthqXYseSSWzUBGT2HoqF");

//				User user = new User("Poul","Password2");
//
//				ParseObject parseUser = new ParseObject("User");
//
//				parseUser.put("name", user.getName());
//				parseUser.put("password", user.getPassword());
//				parseUser.saveInBackground();

		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> userList, ParseException e) {
				if (e == null) {
					for(int i=0; i< userList.size(); i++){
						System.out.println("ObjectId: " + userList.get(i).getObjectId() + ", name: " + userList.get(i).get("name"));
					}
				} else {
					Log.d("score", "Error: " + e.getMessage());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
