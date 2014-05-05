package com.roskildeapp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RoskildeNewsActivity extends Activity {

	ListView listView;
	List<String> newsTitles = new ArrayList<String>();
	List<ParseObject> newsList = new ArrayList<ParseObject>();
	ProgressBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roskilde_news);

		listView = (ListView) findViewById(R.id.lvNews);
		bar = (ProgressBar) findViewById(R.id.pbNews);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("News");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> parseNewsList, ParseException e) {
				if (e == null) {
					newsList = parseNewsList;
				} else {
					System.out.println("Error: " + e.getMessage());
				}
				System.out.println(newsList.size());
				makeList();
			}

		});
	}

	private void makeList() {
		bar.setVisibility(8);
		System.out.println(newsList.size());
		for (int i = 0; i < newsList.size(); i++){
			newsTitles.add((String) newsList.get(i).get("title"));

		}
		System.out.println(newsTitles.size());
		listView.setAdapter(new ArrayAdapter(this, 
				R.layout.listview_news, R.id.tvNewsTitle, newsTitles) {
			@Override
			public View getView(int position, View cachedView, ViewGroup parent) {
				View view = super.getView(position, cachedView, parent);
				TextView listeelem_beskrivelse = (TextView) view.findViewById(R.id.tvNewsTitle);
				listeelem_beskrivelse.setText(newsTitles.get(position));
				return view;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.roskilde_news, menu);
		return true;
	}

}
