package com.example.a096_orientationsensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tvX;
	private TextView tvY;
	private TextView tvZ;
	private ImageView pointer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvX = (TextView)findViewById(R.id.textView1);
		tvY = (TextView)findViewById(R.id.textView2);
		tvZ = (TextView)findViewById(R.id.textView3);
		SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor ori = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION); // 방향 센서
		pointer = (ImageView)findViewById(R.id.imageView1);
		sm.registerListener(new MySensorEvent(), ori, SensorManager.SENSOR_DELAY_NORMAL);	
	} // end of onCreate
	class MySensorEvent implements SensorEventListener{ // 센서 이벤트 처리
		@Override
		public void onSensorChanged(SensorEvent event) { // 측정값이 변경되었을 때 호출
			tvX.setText("x:"+event.values[0]); 
			tvY.setText("y:"+event.values[1]); 
			tvZ.setText("z:"+event.values[2]); 
			pointer.setRotation(event.values[0]);
		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		} // 정밀도가 변경되었을 때 호출
	}
} // end of class
