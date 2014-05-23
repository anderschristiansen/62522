package com.roskildeapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class RoskildeNewsChosenActivity extends Activity {

	TextView title, content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roskilde_news_chosen);
		
		title = (TextView) findViewById(R.id.tvRNCTitle);
		title.setText(getIntent().getStringExtra("title"));
		
		content = (TextView) findViewById(R.id.tvRNCContent);
		content.setText(getIntent().getStringExtra("content"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.roskilde_news_chosen, menu);
		return true;
	}

}