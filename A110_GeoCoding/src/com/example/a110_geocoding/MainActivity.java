package com.example.a110_geocoding;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//위치기반 앱: 지도를 이용한,좌표를 이용한 앱
		//지오코딩(geoCoding) : 주소,지명->위도,경도 좌표로 변환
		//역지오코딩:			:위도,경도 좌표 - >주소,지명으로 변환
		
		//1.권한설정 AndroidManifest.xml
		//Geocoder 객체사용
		
		Button b1 = (Button)findViewById(R.id.button1);
		Button b2 = (Button)findViewById(R.id.button2);
		final EditText etLat = (EditText)findViewById(R.id.editText1);//위도
		final EditText etLong = (EditText)findViewById(R.id.editText2);//경도
		final EditText etAddress= (EditText)findViewById(R.id.editText3);//주소입력
		final TextView tv = (TextView)findViewById(R.id.textView1);//결과창
		final Geocoder geocoder = new Geocoder(getApplicationContext());//지오 코더 객체 준비
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
//				위도,경도=>주소,지명출력(역지오코딩)
				double lat = Float.valueOf(etLat.getText().toString());
				double lon = Float.valueOf(etLong.getText().toString());
					List <Address>list = geocoder.getFromLocation(lat, lon, 10);//인자값:위도,경도,최대검색할 개수
					if(list!=null &&list.size()>0){
						Address add = list.get(0);
						tv.setText("국가명:"+add.getCountryName()+"\n시:"+add.getAdminArea()+"\n구:"+add.getLocality()+"\n위도:"+add.getLatitude()+"\n경도:"+add.getLongitude()+"\n전체주소:"+add.getAddressLine(0));
					}
				} catch (IOException e) {
					tv.setText("입출력오류:IOException");
				}
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
				String address = etAddress.getText().toString();
					List<Address>list = geocoder.getFromLocationName(address, 10);
					if(list!=null && list.size()>0){
						Address add = list.get(0);
						tv.setText("위도:"+add.getLatitude()+"\n경도:"+add.getLongitude());
					}
				} catch (IOException e) {
					tv.setText("입출력오류:IOException");
				}
			}
		});
	}//end of void onCreate
}//end of class MainActivity



//<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>					wifi와 gps를 허용
//<uses-permission android:name="android.permission.INTERNET"/>