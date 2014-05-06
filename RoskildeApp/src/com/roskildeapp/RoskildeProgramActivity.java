package com.roskildeapp;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RoskildeProgramActivity extends Activity implements OnItemClickListener {

	ListView listView;
	List<ParseObject> bandList = new ArrayList<ParseObject>();
	List<String> bandNames = new ArrayList<String>();
	List<String> scenes = new ArrayList<String>();
	List<Date> dates = new ArrayList<Date>();
	boolean[] bandsChecked;

	ProgressBar bar;

	boolean isFirstTime = true;
	String ProgramIdToUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roskilde_program);

		listView = (ListView) findViewById(R.id.lvRP);
		listView.setOnItemClickListener(this);

		bar = (ProgressBar) findViewById(R.id.pbProgram);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Bands");
		query.orderByAscending("date");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> parseBandList, ParseException e) {
				if (e == null) {
					bandList = parseBandList;
				} else {
					System.out.println("Error: " + e.getMessage());
				}
				FindUserProgram();
			}
		});
	}

	private void FindUserProgram() {
		final Context context = this;
		System.out.println(context);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProgram");
		query.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> bandsCheckedList, ParseException e) {
				if (e == null) {
					if(bandsCheckedList.size() > 0){
						isFirstTime = false;
						bandsChecked = new boolean[bandList.size()];
						for(int i=0; i < bandList.size(); i++){
							if(bandsCheckedList.get(0).getBoolean("band"+i) == true){
								bandsChecked[i] = true;
							}
							else{bandsChecked[i] = false;}
						}
						ProgramIdToUpdate = bandsCheckedList.get(0).getObjectId();
					}
				} else {
					Dialog d = new Dialog(context);
					d.setTitle("Der skete en fejl!");
					TextView tv = new TextView(context);
					tv.setText("Det blev ikke gemt.");
					d.setContentView(tv);
					d.show();
				}
				MakeList();
			}
		});
	}

	private void MakeList() {
		bar.setVisibility(8);
		System.out.println(bandList.size());
		for (int i = 0; i < bandList.size(); i++){
			bandNames.add((String) bandList.get(i).get("name"));
			scenes.add((String) bandList.get(i).get("scene"));
			dates.add((Date) bandList.get(i).get("date"));
		}

		if(isFirstTime){
			bandsChecked = new boolean[bandList.size()];
			for (int i = 0; i < bandList.size(); i++) {
				bandsChecked[i] = false;   
			}
		}

		listView.setAdapter(new ArrayAdapter(this, 
				R.layout.listview_program, R.id.tvRPBand, bandNames) {
			@Override
			public View getView(int position, View cachedView, ViewGroup parent) {
				View view = super.getView(position, cachedView, parent);
				TextView name = (TextView) view.findViewById(R.id.tvRPBand);
				name.setText(bandNames.get(position));
				TextView scene = (TextView) view.findViewById(R.id.tvRPScene);
				scene.setText(scenes.get(position));
				TextView time = (TextView) view.findViewById(R.id.tvRPTime);
				time.setText(dates.get(position).toString());
				if(bandsChecked[position] == true){
					view.setBackgroundColor(Color.LTGRAY);
				}
				return view;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.roskilde_program, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> liste, View v, int position, long id) {
		if(bandsChecked[position] == false){
			bandsChecked[position] = true;
		}
		else bandsChecked[position] = false;
		CheckedBands(liste);
	}

	private void CheckedBands(AdapterView<?> liste) {
		for (int i = 0; i < bandList.size(); i++){
			if(bandsChecked[i] == true){
				liste.getChildAt(i).setBackgroundColor(Color.LTGRAY);
			}
			else liste.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);	
		}
	}

	public void onBackPressed() {
		if(isFirstTime){
			SaveUserProgram();
		}
		else{
			UpdateUserProgram();
		}
		finish();
	}

	private void SaveUserProgram(){
		try{
			ParseObject userProgram = new ParseObject("UserProgram");
			userProgram.put("userId", ParseUser.getCurrentUser().getObjectId());
			userProgram.put("userName", ParseUser.getCurrentUser().get("username"));
			for(int i = 0; i < bandList.size(); i++){
				userProgram.put("band"+i, bandsChecked[i]);
			}
			userProgram.saveInBackground();
		}
		catch (Exception e) {
			Dialog d = new Dialog(this);
			d.setTitle("Der skete en fejl!");
			TextView tv = new TextView(this);
			tv.setText("Det blev ikke gemt.");
			d.setContentView(tv);
			d.show(); 
		}
	}

	private void UpdateUserProgram() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProgram");

		// Retrieve the object by id
		query.getInBackground(ProgramIdToUpdate, new GetCallback<ParseObject>() {
			public void done(ParseObject userProgram, ParseException e) {
				if (e == null) {
					for(int i = 0; i < bandList.size(); i++){
						userProgram.put("band"+i, bandsChecked[i]);
					}
					userProgram.saveInBackground();
				}
			}
		});
	}
}
