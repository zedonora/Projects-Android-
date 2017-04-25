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

public class A106_MainActivity extends FragmentActivity implements OnClickListener {
	SupportMapFragment smf;
	GoogleMap map; // 구글맵 객체
	Button b1, b2, b3, b4;
	SeekBar sb;
	LatLng ll1, ll2, ll3, ll4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a106__main);

		// SupportMapFragment 객체를 xml에서 자바코드로 가져오는 방법
		smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
		map = smf.getMap(); // 보여지고 있는 구글맵 객체 얻어오기
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.554502, 126.970654), // 지도의 위치
				15)); // 카메라 줌 (확대/축소) - 숫자가 커지면 확대
		map.getUiSettings().setZoomControlsEnabled(true); // 지도 우측하단에 확대/축소 버튼

		// Marker 달기 (표시) - 확대/축소 해도 이미지 크기는 그대로이다.
		MarkerOptions marker = new MarkerOptions();
		marker.position(new LatLng(37.554502, 126.970654)); // marker 위치지정
		marker.title("민쌤 짱~!");
		marker.snippet("안드로이드 지도서비스\n부가설명입니다. 서울역 위치에 marker를 달았습니다."); // 설명
		map.addMarker(marker).showInfoWindow(); // 지도에 marker 추가, 화면에 보여주기

		// Overlay 달기 - 확대/축소 하면 지도의 일부인것 처럼 확대하면 커지고 축소하면 작아진다
		GroundOverlayOptions overlay = new GroundOverlayOptions();
		overlay.image(BitmapDescriptorFactory.fromResource(R.drawable.spongebob));
		overlay.position(new LatLng(37.553709, 126.970758), // 위치지정
				100, 100); // 이미지의 크기
		map.addGroundOverlay(overlay); // 지도에 overlay 추가

		// 이벤트 처리
		map.setOnMapClickListener(new OnMapClickListener() { // 맵을 클릭했을때
			int num;
			@Override
			public void onMapClick(LatLng arg0) { // LatLng arg0 (위도, 경도)
				// 사용자가 클릭한 위치에 마커를 표시하기
				MarkerOptions marker = new MarkerOptions();
				marker.position(arg0); // 마커 위치
				marker.title(num++ + " 마커 클릭");
				marker.snippet("이곳이 궁금합니까?");
				map.addMarker(marker).showInfoWindow(); // 마커 추가하고 보여주기
			}
		});
		map.setOnMarkerClickListener(new OnMarkerClickListener() { // 마커를 클릭했을때
			@Override
			public boolean onMarkerClick(Marker arg0) {
				// 마커 클릭시 호출되는 콜백 메서드
				Toast.makeText(getApplicationContext(),
						"위도[" + arg0.getPosition().latitude + "," + arg0.getPosition().longitude + "]",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		ll1 = new LatLng(37.459275, 126.953122); // 서울대 : 37.459275, 126.953122
		ll2 = new LatLng(37.478724, 126.881593); // KOSTA : 37.478724, 126.881593
		ll3 = new LatLng(36.877257, 127.157814); // 우리집 : 36.877257, 127.157814
		ll4 = new LatLng(37.243655, 131.867845); // 독도 : 37.243655, 131.867845

		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		b4 = (Button) findViewById(R.id.button4);
		sb = (SeekBar) findViewById(R.id.seekBar1);
		
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		
		sb.setMax(21); // 최대범위 지정
		sb.setProgress((int)map.getCameraPosition().zoom); // 기본값 현재 15
		
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			} // 썸네일에서 손을 뗐을 때 호출되는 콜백메서드
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			} // 썸네일에 손을 댔을때 호출되는 콜백메서드
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// Seekbar 값을 변경했을 때 호출되는 콜백메서드
				map.moveCamera(CameraUpdateFactory.zoomTo(progress)); // 2~21
				setTitle("줌 레벨 : " + progress);
			}
		});
	} // end of onCreate

	@Override
	public void onClick(View v) {
		if (v == b1) {
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll1, sb.getProgress()));
		} else if (v == b2) {
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll2, sb.getProgress()));
		} else if (v == b3) {
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll3, sb.getProgress()));
		} else if (v == b4) {
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll4, sb.getProgress()));
		}
	} // end of onClick

	/** 메뉴를 눌렀을 때 나오는 이벤트 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.a106__main, menu);
		return true;
	} // end of onCreateOptionsmenu

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
		case R.id.menu4: // 아무것도 없음
			map.setMapType(GoogleMap.MAP_TYPE_NONE);
			break;
		case R.id.menu5: // 위성지도랑 비슷? 같음
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		}
		return true;
	} // end of onOptionsItemSelected

} // end of class
