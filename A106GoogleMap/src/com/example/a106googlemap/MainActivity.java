package com.example.a106googlemap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends FragmentActivity{

	GoogleMap map; // 구글맵 객체
	LatLng startingpoint;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	// sdk 설치폴더 \android\extras\google\google_play_services\libproject 폴더가 있어야 함
	// google_play_services.zip 참고
	// sdk 설치폴더 \android\extras\android\support\v4\android-support-4.0.jar 파일이 있어야 함
	// android-support-4.0.jar 참고
	// 프로젝트 생성시 Google APIs 버전으로 생성 <= SDK Manager에서 해당버전을 install 해야 함
	// google-play-servies_lib 라이브러리 프로젝트 import
	// 프로젝트 환경설정 - 구글맵 사용하기위한 라이브러리 추가 2개
		
	// < 우리 프로젝트 작성
	// 1. google-play-servies_lib 를 우리 프로젝트에 등록
	// 2. extras\android\support\v4\android-support-4.0.jar 추가
	// 3. FragmentActivity 를 상속 받도록 MainActivity.java 수정
	// 4. activity_main.xml layout 수정	
	// 5. AndroidManifest.xml 권한 설정
		
//		SupportMapFragment 객체를 xml에서 자바코드로 가져오는 방법
		SupportMapFragment smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
		map = smf.getMap(); // 보여지고 있는 구글맵 객체 얻어오기
		startingpoint = new LatLng(37.554784, 126.970887);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom
				(startingpoint,  // 지도의 위치
					15)); // 카메라 줌 (확대/축소)
		map.getUiSettings().setZoomControlsEnabled(true); // 지도 우측하단 확대/축소 버튼
		
		// Marker 달기
		MarkerOptions marker = new MarkerOptions();
		marker.position(new LatLng(37.554784, 126.970887)); // marker 위치지정
		marker.title("민쌤 짱~!");
		marker.snippet("안드로이드 지도서비스\n서울역의 위치에 Marker달기 실습"); // 설명
		map.addMarker(marker).showInfoWindow(); // 지도에 marker 추가, 화면에 보이기
		
		// Overlay 달기
		GroundOverlayOptions overlay = new GroundOverlayOptions();
		overlay.image(BitmapDescriptorFactory.fromResource(R.drawable.jungyuno));
		overlay.position(new LatLng(37.555669, 126.971937), // 위치지정
				100,100); // 이미지 크기 지정
		map.addGroundOverlay(overlay);
		
		// 이벤트 처리
		map.setOnMapClickListener(new OnMapClickListener() {
			int num;
			@Override
			public void onMapClick(LatLng arg0) { // LatLng arg0 (위도, 경도)
				// 사용자가 클릭한 위치에 마커를 표하기
				MarkerOptions marker = new MarkerOptions();
				marker.position(arg0); // 마커 위치지정
				marker.title(num++ +"마커 클릭");
				marker.snippet("이곳이 궁금합니까?");
				map.addMarker(marker).showInfoWindow(); // 마커를 추가 후 보여주기
			}
		});
		
		map.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker arg0) {
				// 마커 클릭시 호출되는 콜백 메서드
				Toast.makeText(getApplicationContext(),
						"위도:"+arg0.getPosition().latitude+
						", 경도:"+arg0.getPosition().longitude, 
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		SeekBar sb = (SeekBar) findViewById(R.id.seekBar1);
		
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				map.moveCamera(CameraUpdateFactory.newLatLngZoom
						(new LatLng(37.460129, 126.951895),  // 지도의 위치
							15)); // 카메라 줌 (확대/축소)
				MarkerOptions marker = new MarkerOptions();
				startingpoint = new LatLng(37.460129, 126.951895);
				marker.position(startingpoint); // marker 위치지정
				marker.title("서울대");
				marker.snippet("안드로이드 지도서비스\n서울대의 위치에 Marker달기 실습"); // 설명
				map.addMarker(marker).showInfoWindow(); // 지도에 marker 추가, 화면에 보이기
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				map.moveCamera(CameraUpdateFactory.newLatLngZoom
						(new LatLng(37.478738, 126.881697),  // 지도의 위치
								15)); // 카메라 줌 (확대/축소)
				MarkerOptions marker = new MarkerOptions();
				startingpoint = new LatLng(37.478738, 126.881697);
				marker.position(startingpoint); // marker 위치지정
				marker.title("Kosta");
				marker.snippet("안드로이드 지도서비스\nKosta의 위치에 Marker달기 실습"); // 설명
				map.addMarker(marker).showInfoWindow(); // 지도에 marker 추가, 화면에 보이기
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				map.moveCamera(CameraUpdateFactory.newLatLngZoom
						(new LatLng(35.187922, 126.924262),  // 지도의 위치
								15)); // 카메라 줌 (확대/축소)
				MarkerOptions marker = new MarkerOptions();
				startingpoint = new LatLng(35.187922, 126.924262);
				marker.position(startingpoint); // marker 위치지정
				marker.title("우리집");
				marker.snippet("안드로이드 지도서비스\n우리집의 위치에 Marker달기 실습"); // 설명
				map.addMarker(marker).showInfoWindow(); // 지도에 marker 추가, 화면에 보이기
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				map.moveCamera(CameraUpdateFactory.newLatLngZoom
						(new LatLng(37.242879, 131.867904),  // 지도의 위치
								15)); // 카메라 줌 (확대/축소)
				MarkerOptions marker = new MarkerOptions();
				startingpoint = new LatLng(37.242879, 131.867904);
				marker.position(startingpoint); // marker 위치지정
				marker.title("독도");
				marker.snippet("안드로이드 지도서비스\n독도 위치에 Marker달기 실습"); // 설명
				map.addMarker(marker).showInfoWindow(); // 지도에 marker 추가, 화면에 보이기
			}
		});
		// 현재 지도의 줌레벨로 seekbar초기화
		sb.setProgress((int) map.getCameraPosition().zoom); 
		sb.setMax(21); // 최대 범위 지정
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			} // 썸네일에서 손을 뗐을 때 호출되는 콜백 메서드
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) { 
			} // 썸네일에 손을 댔을때 호출되는 콜백 메서드
			@Override
			public void onProgressChanged(SeekBar seekBar, 
					int progress, boolean fromUser) {
				map.moveCamera(CameraUpdateFactory.zoomTo(progress)); // 2 ~ 21
				setTitle("줌 레벨: " + progress);
			} // SeekBar 값을 변경했을 때 호출 되는 콜백 메서드
		});
	} // end of onCreate
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu1: // 일반지도
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.menu2: // 위성지도
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.menu3: // 지형도
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		case R.id.menu4: // 지형도
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case R.id.menu5: // 지형도
			map.setMapType(GoogleMap.MAP_TYPE_NONE);
			break;
		}
		return true;
	}
} // end of class
