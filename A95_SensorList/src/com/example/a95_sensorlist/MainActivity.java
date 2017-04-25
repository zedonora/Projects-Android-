package com.example.a95_sensorlist;

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
		TextView tv = (TextView)findViewById(R.id.textView1);
		//내 디바이스에서 사용 할 수 있는 센서 장치 목록을 조회하기
		SensorManager sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		List<Sensor>list = sm.getSensorList(Sensor.TYPE_ALL);
		String str="전체센서 목록조회\n"
				+"센서 개수:"+list.size();
		for (int i = 0; i < list.size(); i++) {
			Sensor sensor = list.get(i);
			str+="\n\n"+i
					+"\nName:"+sensor.getName()//이름
					+"\nPower:"+sensor.getPower()//전력
					+"\nResolution:"+sensor.getResolution()//해상도
					+"\nMaximumRange:"+sensor.getMaximumRange()//최대값
					+"\nVendor:"+sensor.getVendor();//제조회사
		}
		tv.setText(str);
		//
	}//end of void onCreate
}//end of class MainActivity
