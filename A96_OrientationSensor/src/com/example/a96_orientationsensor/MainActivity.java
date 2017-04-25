package com.example.a96_orientationsensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tvX;
	TextView tvY; 
	TextView tvZ;
	ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvX = (TextView)findViewById(R.id.textView1);
		tvY = (TextView)findViewById(R.id.textView2);
		tvZ = (TextView)findViewById(R.id.textView3);
		iv = (ImageView)findViewById(R.id.imageView2);
		SensorManager sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		Sensor ori = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);//방향센서
		sm.registerListener(new MySensorEvent(), ori,SensorManager.SENSOR_DELAY_NORMAL);
		
	}//end of void onCreate
	class MySensorEvent implements SensorEventListener{//센서이벤트 처리
		@Override
		public void onSensorChanged(SensorEvent event) {//측정값이 변경되었을 때 호출
			tvX.setText("X:"+event.values[0]);
			tvY.setText("Y:"+event.values[1]);
			tvZ.setText("Z:"+event.values[2]);
			iv.setRotation(event.values[0]);
		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}//정밀도가 변경되었을 때 호출되는 콜백메서드
	}
}//end of class MainActivity
