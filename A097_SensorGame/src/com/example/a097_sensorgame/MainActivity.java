package com.example.a097_sensorgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView iv;
	long moveCnt = 10;
	Point point;
	Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		iv = (ImageView) findViewById(R.id.imageView1);
		SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		Sensor s = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		sm.registerListener(new MySensorEvent(), s, SensorManager.SENSOR_DELAY_GAME);
	} // end of onCreate
	class MySensorEvent implements SensorEventListener {
		@Override
		public void onSensorChanged(final SensorEvent event) { // 센서 값이 변경되었을 때
			handler.post(new Runnable() {
				@Override
				public void run() {
					iv.setX(-event.values[2]*8 	+400); // 좌 80 ~ -80우
					iv.setY(-event.values[1]*10 +700); // 똑바로 90 ~ -90 거꾸로
					iv.setRotation(-event.values[0]);; // 회전
				}
			});
		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}
	}
} // end of class
