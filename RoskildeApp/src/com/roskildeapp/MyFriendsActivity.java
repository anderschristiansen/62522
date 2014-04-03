package com.roskildeapp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyFriendsActivity extends Activity {

	ProgressBar progressBar;
	ListView listView;
	final List<String> parseUserIdList = new ArrayList<String>();
	final List<String> parseUserList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_friends);
		System.out.println("test");

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		listView = (ListView) findViewById(R.id.lvFriends);

		FindFriendsFromCurrentUser();
	}

	private void FindFriendsFromCurrentUser() {
		final ParseUser currentUser = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> userList, ParseException e) {
				if (e == null) {
					for(int i=0; i< userList.size(); i++){
						if(userList.get(i).get("friendsId2").toString().equals(currentUser.getObjectId().toString())){
							parseUserIdList.add(userList.get(i).get("friendsId1").toString());
							System.out.println("ven gemt");
						}
						if(userList.get(i).get("friendsId1").toString().equals(currentUser.getObjectId().toString())){
							parseUserIdList.add(userList.get(i).get("friendsId2").toString());
						}
					}
				} else {
					Log.d("score", "Error: " + e.getMessage());
				}
				FindUsernames();
			}
		});		
	}

	private void FindUsernames(){
		Collection<String> userId = parseUserIdList;
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereContainedIn("objectId", userId);
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> users, ParseException e) {
				if (e == null) {
					for(int i= 0; i < users.size(); i++){
						parseUserList.add((String) users.get(i).get("username"));
					}
				} else {
					System.out.println("fejl i FindUsernames()");
				}
				MakeListView();
			}		
		});
	}

	private void MakeListView(){
		progressBar.setVisibility(8);
		
		listView.setAdapter(new ArrayAdapter(this, 
				R.layout.listview_friends, R.id.lvTvName, parseUserList) {
			@Override
			public View getView(int position, View cachedView, ViewGroup parent) {
				View view = super.getView(position, cachedView, parent);
				TextView listeelem_beskrivelse = (TextView) view.findViewById(R.id.lvTvName);
				listeelem_beskrivelse.setText(parseUserList.get(position));
				return view;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_friends, menu);
		return true;
	}

}
