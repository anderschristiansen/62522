package com.roskildeapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.crypto.spec.PSource;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyFriendsActivity extends Activity implements OnClickListener {

	ProgressBar progressBar;
	ListView listView;

	Button appli, btnSearch;
	EditText search;
	TextView searchFriend;
	RelativeLayout layout;

	List<String> parseUserIdList = new ArrayList<String>();
	List<ParseUser> parseUserList = new ArrayList<ParseUser>();
	List<String> parseUserNameList = new ArrayList<String>();
	List<String> allParseUserList = new ArrayList<String>();
	List<String> IdOfFriendsWhoMadeRequest = new ArrayList<String>();
	List<String> friendRequestIds = new ArrayList<String>();
	List<String> userNameOfFriendsWhoMadeRequest = new ArrayList<String>();
	List<String> nameOfFriendsWhoMadeProgram = new ArrayList<String>();
	List<Boolean> positionOfFriendsWithProgram = new ArrayList<Boolean>();
	List<ParseUser> friendsWhoHaveGPS = new ArrayList<ParseUser>();
	List<Boolean> positionOfFriendsWithGPS = new ArrayList<Boolean>();


	ParseUser parseUser = new ParseUser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_friends);
		System.out.println("test");
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

		if(settings.getBoolean("skift tema", true)){
			setActivityBackgroundRecource(R.color.orange);
		}
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		listView = (ListView) findViewById(R.id.lvFriends);

		search = (EditText) findViewById(R.id.etMFSearchName);
		searchFriend = (TextView) findViewById(R.id.tvMFOtherUser);

		appli = (Button) findViewById(R.id.btnMFAppli);
		appli.setOnClickListener(this);

		btnSearch = (Button) findViewById(R.id.btnMFSearch);
		btnSearch.setOnClickListener(this);

		layout = (RelativeLayout) findViewById(R.id.rlMF);
		layout.setVisibility(8);

		FindFriendRequests();
		// When you want to search for a new friend, we have to load all people from database.
		FindAllUsernames();
	}

	public void setActivityBackgroundRecource(int color) {
		View view = this.getWindow().getDecorView();
		view.setBackgroundResource(color);
	}

	// finder alle brugere i databasen.
	private void FindAllUsernames() {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> users, ParseException e) {
				if (e == null) {
					for(int i= 0; i < users.size(); i++){
						allParseUserList.add((String) users.get(i).get("username"));
					}
				} else {
					System.out.println("fejl i FindAllUsernames");
				}
			}		
		});
	}

	// finder id'er på nye venneanmodninger, som ikke er besvaret og id på de personer der har lavet anmodningen
	private void FindFriendRequests(){
		final ParseUser currentUser = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");
		query.whereEqualTo("friends", false);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> userList, ParseException e) {
				if (e == null) {
					for(int i=0; i< userList.size(); i++){
						if(userList.get(i).get("friendsId2").equals(currentUser.getObjectId())){
							// Hvem har lavet en anmodning?
							IdOfFriendsWhoMadeRequest.add(userList.get(i).get("friendsId1").toString());
							// Id for den ubesvarede venneanmodning
							friendRequestIds.add(userList.get(i).getObjectId());
						}
					}
				} else {
				}
				System.out.println(IdOfFriendsWhoMadeRequest.toString());
				FindNameFromFriendRequests();
				//TODO: knappen "Anmodninger i topmenuen skal først enables nu.
			}
		});		
	}

	// finder navne på de personer som har lavet en anmodning.
	private void FindNameFromFriendRequests(){
		Collection<String> userId = IdOfFriendsWhoMadeRequest;
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereContainedIn("objectId", userId);
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> users, ParseException e) {
				if (e == null) {
					for(int i= 0; i < users.size(); i++){
						userNameOfFriendsWhoMadeRequest.add((String) users.get(i).get("username"));
					}
				} else {
					System.out.println("fejl i FindUsernames()");
				}
				FindFriendsFromCurrentUser();
			}		
		});		
	}


	// Finder venners id for den person der er logget ind
	private void FindFriendsFromCurrentUser() {
		final ParseUser currentUser = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");
		query.whereEqualTo("friends", true);
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
				System.out.println(parseUserIdList.toString());
				FindUsernames();
			}
		});		
	}

	// finder venners navn og venners objekter, for den person der er logget ind
	private void FindUsernames(){
		Collection<String> userId = parseUserIdList;
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereContainedIn("objectId", userId);
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> users, ParseException e) {
				if (e == null) {
					for(int i= 0; i < users.size(); i++){
						parseUserNameList.add((String) users.get(i).get("username"));
						parseUserList.add(users.get(i));
					}
				} else {
					System.out.println("fejl i FindUsernames()");
				}
				FindFriendPrograms();
			}		
		});
	}

	// Finder venner som har oprettet et program
	private void FindFriendPrograms(){
		Collection<String> friendNames = parseUserNameList;
		ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProgram");
		query.whereContainedIn("userName", friendNames);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> names, ParseException e) {
				if (e == null) {
					for(int i= 0; i < names.size(); i++){
						System.out.println(names.size());
						nameOfFriendsWhoMadeProgram.add(names.get(i).getString("userName"));
					}
					for(int i = 0; i<nameOfFriendsWhoMadeProgram.size();i++){
						System.out.println(nameOfFriendsWhoMadeProgram.get(i));
					}
				} else {

				}

				// Håndterer hvilke venners program der må ses.
				for(int i = 0; i < parseUserNameList.size(); i++){
					boolean answer = false;
					for(int j = 0; j < nameOfFriendsWhoMadeProgram.size(); j++){
						if(parseUserNameList.get(i).equals(nameOfFriendsWhoMadeProgram.get(j))){
							positionOfFriendsWithProgram.add(true);
							j = nameOfFriendsWhoMadeProgram.size();
							answer = true;
						}
					}
					if(!answer){
						positionOfFriendsWithProgram.add(false);
					}
				}

				//Håndterer hvilke venners GPS position der må ses

				Calendar calendar =Calendar.getInstance() ;
				calendar.add(Calendar.HOUR,-1);
				Date date = calendar.getTime();
				for(int k = 0; k<parseUserList.size();k++){
					System.out.println(date + ", " + parseUserList.get(k).getUpdatedAt());
					if(date.before(parseUserList.get(k).getUpdatedAt())){
						if(!(parseUserList.get(k).getDouble("latitude") == 0)){
							friendsWhoHaveGPS.add(parseUserList.get(k));
						}
					}
				}
				for(int l = 0; l<parseUserList.size(); l++){
					boolean answer = false;
					for(int m = 0; m < friendsWhoHaveGPS.size(); m++){
						if(parseUserList.get(l).equals(friendsWhoHaveGPS.get(m))){
							positionOfFriendsWithGPS.add(true);
							m = friendsWhoHaveGPS.size();
							answer = true;
						}
					}
					if(!answer){
						positionOfFriendsWithGPS.add(false);
					}
				}

				System.out.println(positionOfFriendsWithGPS);

				MakeListView();
			}	
		});
	}

	// laver listview af venner.
	private void MakeListView(){
		progressBar.setVisibility(8);

		listView.setAdapter(new ArrayAdapter(this, 
				R.layout.listview_friends, R.id.lvTvName, parseUserList) {
			@Override
			public View getView(final int position, View cachedView, ViewGroup parent) {
				View view = super.getView(position, cachedView, parent);
				TextView friendName = (TextView) view.findViewById(R.id.lvTvName);
				friendName.setText(parseUserNameList.get(position));
				ImageButton friendProgram = (ImageButton) view.findViewById(R.id.ibFprogram);
				TextView program = (TextView) view.findViewById(R.id.lvTvFriendsProgram);
				if(positionOfFriendsWithProgram.get(position)){
					friendProgram.setVisibility(0);
					program.setVisibility(0);
					friendProgram.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{			
							FriendProgram(parseUserNameList.get(position));
						}
					});
				}
				else{
					program.setVisibility(8);
					friendProgram.setVisibility(8);
				}

				ImageButton friendMap = (ImageButton) view.findViewById(R.id.ibFmap);
				TextView map = (TextView) view.findViewById(R.id.lvTvFriendsMap);

				if(positionOfFriendsWithGPS.get(position)){
					friendMap.setVisibility(0);
					map.setVisibility(0);
					friendMap.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{			
							FriendMap(parseUserList.get(position));
						}
					});
				}
				else{
					map.setVisibility(8);
					friendMap.setVisibility(8);
				}
				return view;
			}
		});
	}

	private void FriendProgram(String username) {
		Intent i = new Intent(this, FriendProgramActivity.class);
		i.putExtra("FriendName", username);
		startActivity(i);
	}
	private void FriendMap(ParseUser friend) {

		Intent i = new Intent(this, MapActivity.class);
		i.putExtra("username", friend.getString("username"));
		i.putExtra("latitude", friend.getDouble("latitude"));
		i.putExtra("longitude", friend.getDouble("longitude"));
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String date = df.format(friend.getUpdatedAt());
		i.putExtra("updated", date);
		startActivity(i);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_friends, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnMFSearch:
			String searchName = search.getText().toString().trim();
			FindUser(searchName);

			break;
		case R.id.btnMFAppli:
			ParseObject newFriend = new ParseObject("Friends");
			newFriend.put("friendsId1", ParseUser.getCurrentUser().getObjectId());
			newFriend.put("friendsId2", parseUser.getObjectId());
			newFriend.put("friends", false);
			newFriend.saveInBackground();
			appli.setEnabled(false);

			break;
		default:
			break;
		}
	}

	// søger efter bruger, ud fra bruger input. 
	private void FindUser(String searchName){
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereContains("username", searchName);
		progressBar.setVisibility(0);

		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					if(objects.size() != 0){
						parseUser = objects.get(0);
					}
					else parseUser = null;
				} else {
					parseUser = null;
				}
				UpdateName();
				progressBar.setVisibility(8);
			}
		});
	}

	// viser navnet på en søgt bruger.
	private void UpdateName(){
		if(parseUser != null){
			layout.setVisibility(0);
			for(int i=0; i<allParseUserList.size();i++){
				if(allParseUserList.get(i).equals(parseUser.get("username"))){
					for(int ven = 0; ven < parseUserIdList.size();ven++){
						// Hvis man allerede er ven med den person, kan man ikke ansøge om venskab
						// eller hvis man selv er personen.
						if(parseUserIdList.get(ven).equals(parseUser.getObjectId()) || 
								parseUser.getObjectId().equals(ParseUser.getCurrentUser().getObjectId())){
							appli.setEnabled(false);
						}
					}
					i=allParseUserList.size();
				}
				else appli.setEnabled(true);
			}
			searchFriend.setText(parseUser.getString("username"));
		}else layout.setVisibility(8);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.btnMenuMFRequests){
			Intent i = new Intent(this,FriendRequestActivity.class);
			i.putStringArrayListExtra("friendRequestList", (ArrayList<String>) userNameOfFriendsWhoMadeRequest);
			i.putStringArrayListExtra("friendRequestIdList", (ArrayList<String>) friendRequestIds);
			startActivity(i);
			finish();
		}
		return false;
	}
}
