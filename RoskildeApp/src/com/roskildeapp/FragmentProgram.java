package com.roskildeapp;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentProgram extends Fragment{
	ListView listView;
	ProgressBar bar;

	ParseObject parseObject;
	List<ParseObject> bandList = new ArrayList<ParseObject>();
	List<String> bandName = new ArrayList<String>();

	List<String> bandNames = new ArrayList<String>();
	List<String> scenes = new ArrayList<String>();
	List<String> BandDescription = new ArrayList<String>();
	List<String> dates = new ArrayList<String>();

	int bandCount;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		
		listView = (ListView) getActivity().findViewById(R.id.fragmentlvRFP);
		
		bar = (ProgressBar) getActivity().findViewById(R.id.fragmentpbFProgram);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("bands");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> parseBandList, ParseException e) {
				if (e == null) {
					bandCount = parseBandList.size();
					bandList = parseBandList;
				} else {
					System.out.println("Error: " + e.getMessage());
				}
				getBandsFromFriend();
			}
		});
		
		return inflater.inflate(
				R.layout.fragment_program, container, false);
	}
	public void getBandsFromFriend(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProgram");
		query.whereContains("userId", ParseUser.getCurrentUser().getObjectId());
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> program, ParseException e) {
				if (e == null) {
					parseObject = program.get(0);
				} else {
					System.out.println("Error: " + e.getMessage());
				}

				for(int band = 0; band < bandCount; band++){
					if(!parseObject.get("band" + band).equals("")){
						System.out.println("band" + band);
						bandName.add("band" + band);
						for(int i = 0; i < bandList.size(); i++){
							if(parseObject.get("band" + band).equals(bandList.get(i).getString("name"))){
								System.out.println(i);
								bandNames.add(bandList.get(i).getString("name"));
								scenes.add(bandList.get(i).getString("scene"));
								BandDescription.add(bandList.get(i).getString("description"));
								dates.add(bandList.get(i).getString("date"));
							}
						}
					}
				}
				MakeList();
				
				//				System.out.println(bandList.size());
				//				for (int i = 0; i<bandList.size(); i++){
				//					System.out.println(bandList.get(i));
				//				}
			}
		});
	}

	private void MakeList() {
		bar.setVisibility(8);

		//		for (int i = 0; i < bandList.size(); i++){
		//			bandNames.add((String) bandList.get(i).get("name"));
		//			BandDescription.add((String) bandList.get(i).get("description"));
		//			scenes.add((String) bandList.get(i).get("scene"));
		//			dates.add((String) bandList.get(i).get("date"));
		//		}

		System.out.println("bandNames: "+ bandNames.size());
		
		listView.setAdapter(new ArrayAdapter(getActivity().getApplicationContext(), 
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
				return view;
			}
		});
		System.out.println("-------------/n ListView er oprettet/n--------------");
	}

	public void GoToBand(int position) {
		Intent i = new Intent(getActivity().getApplicationContext(), BandDesciptionActivity.class);
		i.putExtra("Band", bandNames.get(position));
		i.putExtra("Description", BandDescription.get(position));
		startActivity(i);
	}

}
