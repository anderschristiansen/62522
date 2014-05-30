package com.roskildeapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class WeatherActivity extends Activity {

	ImageView image1;
	ImageView image2;
	ImageView image3;
	int zipCode = 4000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		image1 = new ImageView(this);
		image1.setAdjustViewBounds(true);
		linearLayout.addView(image1);

		image2 = new ImageView(this);
		image2.setAdjustViewBounds(true);
		linearLayout.addView(image2);

		ScrollView scrollView = new ScrollView(this);
		scrollView.addView(linearLayout);
		setContentView(scrollView);

		new getWeather().execute();
	}


	private class getWeather extends AsyncTask<String, Void, Bitmap> {

		private ProgressDialog dialog = new ProgressDialog(WeatherActivity.this);
		Bitmap bitmap;
		protected void onPreExecute(){
			dialog.setMessage("fetching image from the server");
			dialog.show();
		}

		protected Bitmap doInBackground(String... args) {

			try {
				bitmap = getBitmap("http://servlet.dmi.dk/byvejr/servlet/byvejr_dag1?by=" + zipCode + "&mode=long");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return bitmap;
		}

		protected void onPostExecute(Bitmap m_bitmap) {
			dialog.dismiss();
			if(m_bitmap != null){

				image1.setImageBitmap(m_bitmap);
				image1.requestRectangleOnScreen(new Rect());
			}
		}

		public Bitmap getBitmap(String url) throws IOException {
			InputStream is = new URL(url).openStream();
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();
			return bitmap;
		}
	}
}


