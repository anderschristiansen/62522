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
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RoskildeProgramActivity extends Activity implements OnItemClickListener {

	ListView listView;
	ProgressBar bar;
	
	List<ParseObject> bandList = new ArrayList<ParseObject>();
	List<String> bandNames = new ArrayList<String>();
	List<String> scenes = new ArrayList<String>();
	List<String> BandDescription = new ArrayList<String>();
	List<String> dates = new ArrayList<String>();
	boolean[] bandPositions;
	String[] bandsChecked;

	boolean isFirstTime = true;
	String ProgramIdToUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roskilde_program);

		listView = (ListView) findViewById(R.id.lvRP);
		listView.setOnItemClickListener(this);

		bar = (ProgressBar) findViewById(R.id.pbProgram);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("bands");
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
				//				if (e == null) {
				//					if(bandsCheckedList.size() > 0){
				//						isFirstTime = false;
				//						bandsChecked = new boolean[bandList.size()];
				//						for(int i=0; i < bandList.size(); i++){
				//							if(bandsCheckedList.get(0).getBoolean("band"+i) == true){
				//								bandsChecked[i] = true;
				//							}
				//							else{bandsChecked[i] = false;}
				//						}
				//						ProgramIdToUpdate = bandsCheckedList.get(0).getObjectId();
				//					}
				if (e == null) {

					bandPositions = new boolean[bandList.size()];
					bandsChecked = new String[bandList.size()];
					for(int k = 0; k < bandPositions.length; k++){
						bandPositions[k] = false;
						bandsChecked[k] = "";
					}
					// Hvis brugeren har lavet et program før
					if(bandsCheckedList.size() > 0){
						isFirstTime = false;
						// for alle bands der var oprettet sidst brugeren gemte.
						for(int band = 0; band < bandsCheckedList.size(); band++){
							// Hvis det band der har været valgt før, eller findes i listen over alle bands
							if(bandsCheckedList.get(band).getString("band"+band) != "" || bandsCheckedList.get(band).getString("band"+band) != null){
								// for alle bands
								for(int j = 0; j < bandList.size(); j++){
									String bandName = bandList.get(j).getString("name");
									//									System.out.println("name: " + bandName);
									//									System.out.println("skal sammenlignes med: " + bandsCheckedList.get(band).getString("band"+j));
									for(int i = 0; i < bandList.size(); i++){
										if(bandsCheckedList.get(band).getString("band"+i) != null){
											if(bandsCheckedList.get(band).getString("band"+i).equals(bandName)){
												bandPositions[j] = true;
												bandsChecked[j] = bandName;
												System.out.println("band: " + bandName + " er gemt på position: " + j + ": " + bandPositions[j]);
											}
										}
									}
									// else {bandPositions[j] = false;}
								}
							}
							// else{bandsChecked[i] = "";}
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
				for (int i = 0; i < bandsChecked.length; i++){
					//					System.out.println("bandsChecked: " + bandsChecked[i]);
				}
				System.out.println("isFirstTime: " + isFirstTime);
				MakeList();
			}
		});
	}

	private void MakeList() {
		bar.setVisibility(8);
		System.out.println(bandList.size());
		for (int i = 0; i < bandList.size(); i++){
			bandNames.add((String) bandList.get(i).get("name"));
			BandDescription.add((String) bandList.get(i).get("description"));
			scenes.add((String) bandList.get(i).get("scene"));
			dates.add((String) bandList.get(i).get("date"));
		}

		if(isFirstTime){	
			bandsChecked = new String[bandList.size()];
			for (int i = 0; i < bandList.size(); i++) {
				bandsChecked[i] = "";   
			}
		}

		//		for(int i = 0; i < bandList.size(); i++){
		//			for(int j = 0; j < bandList.size(); j++){
		//				//				System.out.println(i + ", " + j);
		//				//				System.out.println(bandsChecked[i] + ", " + bandList.get(j).getString("name"));
		//				String bandName = bandList.get(j).getString("name");
		//				//				System.out.println("name: " + bandName);
		//				if(bandsChecked[i].equals(bandName)){
		//					bandPositions[j] = true;
		//				}
		//				//				else {bandPositions[j] = false;}
		//			}
		//		}

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
				ImageView info = (ImageView) view.findViewById(R.id.ivP);
				info.setTag(position);
				info.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						int position = Integer.parseInt(v.getTag().toString());
						GoToBand(position);
					}

				});
				//				if(bandsChecked[position] != true){
				//					view.setBackgroundColor(Color.LTGRAY);
				//				}
				//				System.out.println(position);
				//				System.out.println("bandList.get(position).getString(name): " + bandList.get(position).getString("name"));
				//				System.out.println("bandsChecked[position]: " + bandsChecked[position]);
				//				System.out.println("position: " + position + "bandPositions[position]: " + bandPositions[position]);
				if(bandPositions[position] == true){
					view.setBackgroundColor(Color.LTGRAY);
				}
				else{view.setBackgroundColor(Color.TRANSPARENT);}
				return view;
			}
		});
		System.out.println("-------------/n ListView er oprettet/n--------------");
	}

	public void GoToBand(int position) {
		Intent i = new Intent(this, BandDesciptionActivity.class);
		i.putExtra("Band", bandNames.get(position));
		i.putExtra("Description", BandDescription.get(position));
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.roskilde_program, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> liste, View v, int position, long id) {
		System.out.println(liste.getChildCount());
		if(bandsChecked[position] == ""){
			bandPositions[position] = true;
//			liste.getChildAt(i).setBackgroundColor(Color.LTGRAY);
			liste.getAdapter().getView(position, v, liste).setBackgroundColor(Color.LTGRAY);
			bandsChecked[position] = bandList.get(position).getString("name");
		}
		else {
			bandsChecked[position] = "";
			bandPositions[position] = false;
//			.setBackgroundColor(Color.TRANSPARENT);
			liste.getAdapter().getView(position, v, liste).setBackgroundColor(Color.TRANSPARENT);
		}

	}

	//	private void CheckedBands(AdapterView<?> liste, int position) {
	//		System.out.println(position);
	//		if(bandPositions[position] == true){
	//			liste.getChildAt(position).setBackgroundColor(Color.LTGRAY);
	//		}
	//		else { liste.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);	}
	//	}

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
