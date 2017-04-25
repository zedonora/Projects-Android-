package com.example.a095_sensorlist;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView tv = (TextView) findViewById(R.id.textView1);
		
		// 내 디바이스에서 사용할 수 잇는 센서 장치 목록 조회하기
		SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		List<Sensor> list = sm.getSensorList(Sensor.TYPE_ALL);
		
		String str = "전체 센서 목록 조회\n"
				+"센서 개수: "+list.size();
		for (int i = 0; i < list.size(); i++) {
			Sensor s = list.get(i);
			str += "\n\n"+i
					+"\nName: "+s.getName()
					+"\nPower: "+s.getPower()
					+"\nResolution: "+s.getResolution()
					+"\nMaximumRange: "+s.getMaximumRange()
					+"\nVendor: "+s.getVendor();
		}
		tv.setText(str);
	} // end of onCreate
} // end of class
