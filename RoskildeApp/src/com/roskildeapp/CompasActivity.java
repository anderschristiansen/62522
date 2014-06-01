package com.roskildeapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class CompasActivity extends Activity implements SensorEventListener  {

    private ImageView image;
    private float currentDegree = 0f;
 
    private SensorManager sensorManager;
 
    TextView tvHeading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compas);
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

		if(settings.getBoolean("skift tema", true)){
			setActivityBackgroundRecource(R.color.orange);
		}

        image = (ImageView) findViewById(R.id.vinylImageView);
 
        tvHeading = (TextView) findViewById(R.id.textView1);
 
        // initialize the android device sensor capabilities
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	public void setActivityBackgroundRecource(int color) {
		View view = this.getWindow().getDecorView();
		view.setBackgroundResource(color);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compas, menu);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
    protected void onResume() {
        super.onResume();
         
        // register the listener for the sensor
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }
 
    @Override
    protected void onPause() {
        super.onPause();
         
        // this stops the listener and saves battery for the device
        sensorManager.unregisterListener(this);
    }
 
    @Override
    public void onSensorChanged(SensorEvent event) {
 
        // gets the degree of the angle
        float degree = Math.round(event.values[0]);
 
        tvHeading.setText(Float.toString(degree) + " grad");
 
        // Rotate animation for the compas
        RotateAnimation ra = new RotateAnimation(
                currentDegree, 
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f, 
                Animation.RELATIVE_TO_SELF,
                0.5f);
 
        // animation duration
        ra.setDuration(210);
 
        // set the animation after the end of the reservation status
        ra.setFillAfter(true);
 
        image.startAnimation(ra);
        currentDegree = -degree;
 
    }
 
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
