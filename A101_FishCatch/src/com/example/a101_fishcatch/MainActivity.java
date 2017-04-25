package com.example.a101_fishcatch;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	int throwFishing=0;
	
	private long lastTime;
	private float speed;
	private float lastX;
	private float lastY;
	private float lastZ;
	private float x, y, z;

	TextView tvSpeed;
	TextView tvY;
	TextView tvZ;
	TextView tvX;

//	float[] gravity_data = new float[3];
	final float alpha = (float) 0.8;

	// 민감도(수치가 낮을수록 민감)
	private static final int SHAKE_THRESHOLD = 1000;

	private SensorManager sensorManager;
	private Sensor accelerormeterSensor;

	Toast t;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		tvSpeed = (TextView) findViewById(R.id.textView1);
		tvY = (TextView) findViewById(R.id.textView2);
		tvZ = (TextView) findViewById(R.id.textView3);
		tvX= (TextView) findViewById(R.id.textView4);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (accelerormeterSensor != null)
			sensorManager.registerListener(new MySensorEvent(), accelerormeterSensor, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (sensorManager != null)
			sensorManager.unregisterListener(new MySensorEvent());
	}

	class MySensorEvent implements SensorEventListener {// 센서 이벤트 처리

		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				// 먼저 중력데이터를 계산함
//				gravity_data[0] = alpha * gravity_data[0] + (1 - alpha) * event.values[0];
//				gravity_data[1] = alpha * gravity_data[1] + (1 - alpha) * event.values[1];
//				gravity_data[2] = alpha * gravity_data[2] + (1 - alpha) * event.values[2];

				long currentTime = System.currentTimeMillis();
				long gabOfTime = (currentTime - lastTime);
				// 센서 동작이 이전/이후 시간의 차이가 0.1초 이내이면
				if (gabOfTime > 100) {
					lastTime = currentTime;
					x = event.values[0];
					y = event.values[1];
					z = event.values[2];
					

					speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;
					tvSpeed.setText("speed:" + speed);
					tvY.setText("y:" + y);
					tvZ.setText("z:" + z);
					tvX.setText("X:"+x);

					String str = "";

					if (speed > SHAKE_THRESHOLD) {
						// 이벤트발생!!
							if (y < 1 && throwFishing==0 && x>4) {
								if (t != null)
									t.cancel();
									t = Toast.makeText(getApplicationContext(), "y: " + y + "던짐", Toast.LENGTH_SHORT);
									t.show();
									throwFishing=1;   
							} 
							if (y >= 1 && throwFishing==1 && x<0) {
								if (t != null)
									t.cancel();
									t = Toast.makeText(getApplicationContext(), "y: " + y + "올림", Toast.LENGTH_SHORT);
									t.show();
									throwFishing=0; 
							}
					}
					lastX = event.values[0];
					lastY = event.values[1];
					lastZ = event.values[2];

				}

			}

		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}

	}

}
