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
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RoskildeNewsActivity extends Activity implements OnItemClickListener {

	ListView listView;
	List<String> newsTitles = new ArrayList<String>();
	List<ParseObject> newsList = new ArrayList<ParseObject>();
	ProgressBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roskilde_news);

		listView = (ListView) findViewById(R.id.lvNews);
	    listView.setOnItemClickListener(this);
		
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
				TextView title = (TextView) view.findViewById(R.id.tvNewsTitle);
				title.setText(newsTitles.get(position));
//				TextView date = (TextView) view.findViewById(R.id.);
//				date.setText(newsTitles.get(position));
				return view;
			}
		});
	}
	
	 public void onItemClick(AdapterView<?> liste, View v, int position, long id) {
		    Toast.makeText(this, "Klik på " + position, Toast.LENGTH_SHORT).show();
		    String newsTitle = (String) newsList.get(position).get("title");
		    String newsContent = (String) newsList.get(position).get("content");
		    System.out.println("titel: " + newsTitle + ", tekst: " + newsContent);
		    Intent i = new Intent(this, RoskildeNewsChosenActivity.class);
		    i.putExtra("title", newsTitle);
		    i.putExtra("content", newsContent);
		    startActivity(i);
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.roskilde_news, menu);
		return true;
	}

}
