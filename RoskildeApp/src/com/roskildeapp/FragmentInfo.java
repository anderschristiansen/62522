package com.roskildeapp;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;



public class FragmentInfo extends Fragment implements OnClickListener{

	TextView tvName, tvPhone, tvSaved;
	EditText etName, etPhone;
	Button save;
	View v;
	String name, phone = "";

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		v = inflater.inflate(
				R.layout.fragment_info, container, false);

		System.out.println("Den d¿r da ikke nu?");
		name = (String) ParseUser.getCurrentUser().get("name");
		phone = (String) ParseUser.getCurrentUser().get("phone");
		
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		tvName = (TextView) v.findViewById(R.id.tvfragmInfoName);
		tvPhone = (TextView) v.findViewById(R.id.tvfragmInfoPhone);	
		etName = (EditText) v.findViewById(R.id.etfragInfoName);
		etName.setText(name);
		etPhone = (EditText) v.findViewById(R.id.etfragInfoPhone);
		etPhone.setText(phone);
		save = (Button) v.findViewById(R.id.btnfragInfoSave);
		save.setOnClickListener(this);
		tvSaved = (TextView) v.findViewById(R.id.tvfragmInfoSaved);

	}

	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.btnfragInfoSave){
			
			ParseUser user = ParseUser.getCurrentUser();
			user.put("name", etName.getText().toString());
			user.put("phone", etPhone.getText().toString());
			user.saveInBackground(new SaveCallback() {
				public void done(ParseException e) {
					if (e == null) {
						tvSaved.setTextColor(Color.GREEN);
						tvSaved.setText("Gemt");
						
					} else {
					}
				}				
			});
		}
	}
}
