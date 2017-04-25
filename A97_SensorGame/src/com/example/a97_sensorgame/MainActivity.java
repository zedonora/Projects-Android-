package com.example.a97_sensorgame;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {
	ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView)findViewById(R.id.imageView1);
		SensorManager sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		Sensor s = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		sm.registerListener(new MySensorEvent(), s,SensorManager.SENSOR_DELAY_GAME);
		
	}//end of void onCreate
	class MySensorEvent implements SensorEventListener{
		@Override
		public void onSensorChanged(SensorEvent event) {//센서값이 변경되었을 때
			iv.setX(-event.values[2]*8+300);
			iv.setY(-event.values[1]*10+700);
			iv.setRotation(-event.values[0]);//회전
		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	}
}//end of class MainActivity
