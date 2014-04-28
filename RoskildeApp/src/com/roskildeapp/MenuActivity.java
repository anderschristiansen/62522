package com.roskildeapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity implements OnClickListener{
	Button program, friends, news, map, myPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		program = (Button) findViewById(R.id.btnMenuProgram);
		program.setOnClickListener(this);

		friends = (Button) findViewById(R.id.btnMenuMyFriends);
		friends.setOnClickListener(this);

		news = (Button) findViewById(R.id.btnMenuNews);
		news.setOnClickListener(this);

		map = (Button) findViewById(R.id.btnMenuMap);
		map.setOnClickListener(this);

		myPage = (Button) findViewById(R.id.btnMenuMyPage);
		myPage.setOnClickListener(this);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnMenuProgram:
			startActivity(new Intent(this,RoskildeProgramActivity.class));
			break;
		case R.id.btnMenuMyFriends:
			startActivity(new Intent(this,MyFriendsActivity.class));
			break;
		case R.id.btnMenuNews:
			startActivity(new Intent(this,RoskildeNewsActivity.class));
			break;
		case R.id.btnMenuMap:
			startActivity(new Intent(this,MapActivity.class));
			break;
		case R.id.btnMenuMyPage:
			startActivity(new Intent(this,MypageActivity.class));
			break;
		default:
			break;
		}
	}

	public void onBackPressed() {
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
