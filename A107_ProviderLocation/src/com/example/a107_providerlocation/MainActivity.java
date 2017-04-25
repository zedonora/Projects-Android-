package com.example.a107_providerlocation;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 내 디바이스에서 사용가능한 위치 제공자 조회하기
//		1. AndroidManifest.xml에 권한 획득
//		2. LocationManager를 얻어오기
		
		LocationManager manager = (LocationManager) 
				getSystemService(Context.LOCATION_SERVICE);
		
		TextView tv = (TextView) findViewById(R.id.textView1);
		// 위치제공자 목록 가져오기
		List<String> providers = manager.getAllProviders();
		String result = ""; // 출력할 문자열
		for (int i = 0; i < providers.size(); i++) {
			result += i + ":" + providers.get(i) + "의 상태 : "+
						manager.isProviderEnabled(providers.get(i)) + "\n";
		}
		tv.setText(result);
	} // end of onCreate
} // end of class
