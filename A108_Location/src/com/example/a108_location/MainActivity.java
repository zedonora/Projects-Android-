package com.example.a108_location;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	private LocationManager lm; // 위치 관리자
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 1. 권한 설정
		// 2. 위치 관리자 획득
		// 3. 이벤트를 등록해서 통지받는다.
		
		tv1  = (TextView) findViewById(R.id.textView1);
		tv2  = (TextView) findViewById(R.id.textView2);
		tv3  = (TextView) findViewById(R.id.textView3);
		
		lm = (LocationManager) 
				getSystemService(Context.LOCATION_SERVICE);
		// 위치관리자 얻어오기
		
	} // end of onCreate
	@Override
	protected void onResume() {
		super.onResume();
		// 위치관리자에 리스너 이벤트를 등록한다.
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 사용할 위치 제공자
				0, // 통지의 최소 시간간격(ms) long, 0입력하면, 가능한 자주 통지 받음 -> 배터리 사용량 증가
				0, // 통지의 최소 거리변경기준(m) float, 0입력하면, 가능한 자주 통지 받음 -> 배터리 사용량 증가
				ll);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				0, 
				0, 
				ll2);
		lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,
				0, 
				0, 
				ll3);
	} // end of onResume
	@Override
	protected void onPause() {
		super.onPause();
		if(lm != null) {
			lm.removeUpdates(ll); // 리스너 제거
		}
		if(lm != null) {
			lm.removeUpdates(ll2);
		}
		if(lm != null) {
			lm.removeUpdates(ll3);
		}
	} // end of onPause
	LocationListener ll = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) { 
			// 위치제공자에 의해 위치정보가 변경되었을 때 호출되는 콜백 메서드
			tv1.setText("위치 제공자: GPS"
					+"\n위도: "+ location.getLatitude()
					+"\n경도: "+ location.getLongitude()
					+"\n고도: "+ location.getAltitude());
		} 
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// 위치제공자의 상태가 바뀌었을 때 호출되는 콜백 메서드
		} 	
		@Override
		public void onProviderEnabled(String provider) {
			// 위치제공자가 활성화 되었을 때 호출되는 콜백 메서드
		}
		@Override
		public void onProviderDisabled(String provider) {
			// 위치제공자가 비활성화 되었을 때 호출되는 콜백 메서드
		}
	};
	LocationListener ll2 = new LocationListener() {
		public void onLocationChanged(Location location) { 
			// 위치제공자에 의해 위치정보가 변경되었을 때 호출되는 콜백 메서드
			tv2.setText("위치 제공자: NETWORK"
					+"\n위도: "+ location.getLatitude()
					+"\n경도: "+ location.getLongitude()
					+"\n고도: "+ location.getAltitude());
		} 
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
		@Override
		public void onProviderEnabled(String provider) {
		}
		@Override
		public void onProviderDisabled(String provider) {
		}
	};
	LocationListener ll3 = new LocationListener() {
		public void onLocationChanged(Location location) { 
			// 위치제공자에 의해 위치정보가 변경되었을 때 호출되는 콜백 메서드
			tv3.setText("위치 제공자: PASSIVE"
					+"\n위도: "+ location.getLatitude()
					+"\n경도: "+ location.getLongitude()
					+"\n고도: "+ location.getAltitude());
		} 
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		@Override
		public void onProviderEnabled(String provider) {
		}
		@Override
		public void onProviderDisabled(String provider) {
		}
	};
} // end of class
