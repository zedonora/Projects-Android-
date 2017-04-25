package com.example.a108_location;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	LocationManager lm;//위치 관리자
	TextView tv1;
	TextView tv2;
	TextView tv3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//1.권한설정-Manifest.xml
		//2.위치 관리자 획득-getSystemservies
		//3.이벤트를 등록해서 통지 받는다
		tv1 = (TextView)findViewById(R.id.textView1);
		tv2 = (TextView)findViewById(R.id.textView2);
		tv3 = (TextView)findViewById(R.id.textView3);
		
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		//위치 관리자 얻어오기
	}//end of void onCreate
	@Override
	protected void onPause() {
		super.onPause();
		if(lm!=null)
		lm.removeUpdates(ll);//리스너제거 .더이상 변경된 값을 실시간으로 받지 않겠다.배터리 소모를 줄이기 위해서 
		if(lm!=null)
			lm.removeUpdates(ll2);//리스너제거 .더이상 변경된 값을 실시간으로 받지 않겠다.배터리 소모를 줄이기 위해서 
		if(lm!=null)
			lm.removeUpdates(ll3);//리스너제거 .더이상 변경된 값을 실시간으로 받지 않겠다.배터리 소모를 줄이기 위해서 
	}
	@Override
	protected void onResume() {
		super.onResume();
		//위치 관리자에 리스너이벤트를 등록한다.
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,//위치제공자의 종류를 선택 
				0,//통지의 최소 시간간격(ms)-long 타입의 값으로 넣어준다.0을 입력하면 가능한 자주 통제를 받겠다는 뜻. 
				0,//통지의 최소거리 변경 기준(m)float,0을 입력하면 가능한 자주 통지 받음
				ll);
		//위치 관리자에 리스너이벤트를 등록한다.
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,//위치제공자의 종류를 선택 
					0,//통지의 최소 시간간격(ms)-long 타입의 값으로 넣어준다.0을 입력하면 가능한 자주 통제를 받겠다는 뜻. 
					0,//통지의 최소거리 변경 기준(m)float,0을 입력하면 가능한 자주 통지 받음
					ll2);
			lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,//위치제공자의 종류를 선택 
					0,//통지의 최소 시간간격(ms)-long 타입의 값으로 넣어준다.0을 입력하면 가능한 자주 통제를 받겠다는 뜻. 
					0,//통지의 최소거리 변경 기준(m)float,0을 입력하면 가능한 자주 통지 받음
					ll3);
	}//end of void onResume
	LocationListener ll = new LocationListener(){
		@Override
		public void onLocationChanged(Location location) {
			tv1.setText("위치제공자:GPS"+"\n위도:"+location.getLatitude()+"\n경도:"+location.getLongitude()+"\n고도:"+location.getAltitude());
		}//위치제공자에 의해 위치정보가 변경되었을 때 호출되는 콜백메서드
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}//위치제공자의 상태가 바뀌었을 때, 호출되는 콜백메서드 
		@Override
		public void onProviderEnabled(String provider) {
			
		}//위치제공자가 활성화 되었을 때 호출되는 콜백메서드
		@Override
		public void onProviderDisabled(String provider) {
			
		}};//위치 제공자가 비활성화 되었을 때 호출되는 콜백메서드
		LocationListener ll2 = new LocationListener(){
			@Override
			public void onLocationChanged(Location location) {
				tv2.setText("위치제공자:GPS"+"\n위도:"+location.getLatitude()+"\n경도:"+location.getLongitude()+"\n고도:"+location.getAltitude());
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
			@Override
			public void onProviderEnabled(String provider) {
				
			}
			@Override
			public void onProviderDisabled(String provider) {
				
			}};
			LocationListener ll3 = new LocationListener(){
				@Override
				public void onLocationChanged(Location location) {
					tv3.setText("위치제공자:GPS"+"\n위도:"+location.getLatitude()+"\n경도:"+location.getLongitude()+"\n고도:"+location.getAltitude());
				}
				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {
				}
				@Override
				public void onProviderEnabled(String provider) {
					
				}
				@Override
				public void onProviderDisabled(String provider) {
					
				}};
}//end of class MainActivity
