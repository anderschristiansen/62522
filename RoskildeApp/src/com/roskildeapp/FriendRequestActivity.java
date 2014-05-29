package com.roskildeapp;

import java.util.ArrayList;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;


public class FriendRequestActivity extends Activity implements OnItemClickListener{

	ListView listView;
	ProgressBar progressBar;

	ArrayList<String> friendUserList;
	ArrayList<String> friendUserIdList;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_request);
		listView = (ListView) findViewById(R.id.lvFR);
		friendUserList = getIntent().getStringArrayListExtra("friendRequestList");
		friendUserIdList = getIntent().getStringArrayListExtra("friendRequestIdList");

		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.listview_friend_requests, R.id.lvTvFR, friendUserList) {
			@Override
			public View getView(int position, View cachedView, ViewGroup parent) {
				View view = super.getView(position, cachedView, parent);
				return view;
			}
		};

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		progressBar = (ProgressBar) findViewById(R.id.pbFR);
		progressBar.setVisibility(8);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend_request, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> 	arg0, View arg1, int arg2, long arg3) {
		AlertDialog.Builder builderCancel = new AlertDialog.Builder(this);
		builderCancel.setMessage("Vil du v¾re venner med " + friendUserList.get(arg2) +
				"?").setPositiveButton("Ja", dialogClickListener)
				.setNegativeButton("Nej", dialogClickListener).show();
		position = arg2;
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which){
			case DialogInterface.BUTTON_POSITIVE:
				Answer(true);
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				Answer(false);
				break;
			}
		}
	};

	private void Answer(boolean answer){
		if(answer){
			AcceptFriend();
			friendUserList.remove(position);
			friendUserIdList.remove(position);
			Intent i = new Intent(this,FriendRequestActivity.class);
			i.putStringArrayListExtra("friendRequestList", (ArrayList<String>) friendUserList);
			i.putStringArrayListExtra("friendRequestIdList", (ArrayList<String>) friendUserIdList);
			startActivity(i);
			finish(); 

		}
		else{
			RejectFriend();
			friendUserList.remove(position);
			friendUserIdList.remove(position);
			Intent i = new Intent(this,FriendRequestActivity.class);
			i.putStringArrayListExtra("friendRequestList", (ArrayList<String>) friendUserList);
			i.putStringArrayListExtra("friendRequestIdList", (ArrayList<String>) friendUserIdList);
			startActivity(i);
			finish();
		}
	}

	private void RejectFriend() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");
		// Retrieve the object by id
		query.getInBackground(friendUserIdList.get(position), new GetCallback<ParseObject>() {
			public void done(ParseObject reject, ParseException e) {
				if (e == null) {
					reject.deleteInBackground();
				}
			} 
		}); 
		
	}

	private void AcceptFriend(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");

		// Retrieve the object by id
		query.getInBackground(friendUserIdList.get(position), new GetCallback<ParseObject>() {
			public void done(ParseObject friendStatus, ParseException e) {
				if (e == null) {
					friendStatus.put("friends", true);
					friendStatus.saveInBackground();
				}
			} 
		}); 
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this,MyFriendsActivity.class);
		startActivity(intent);
		finish();
	}
	
}
