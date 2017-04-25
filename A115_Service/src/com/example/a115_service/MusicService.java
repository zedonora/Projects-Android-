package com.example.a115_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MusicService extends Service {
	private MediaPlayer mp;
	@Override
	public void onCreate() { // 서비스가 처음 실행될 때
		super.onCreate();
		// Service 도 Activity 처럼 AndroidManifest.xml 에 등록해줘야 한다.
		Log.d("service","onCreate");
		mp = MediaPlayer.create(this, R.raw.chacha);
		mp.setLooping(false); // 반복안함
	} // end of onCreate
	@Override
	public IBinder onBind(Intent intent) { // 연결서비스로 통신할 때
		return null; // null - 연결안함
	} // end of onBind
	@Override
	public void onStart(Intent intent, int startId) { // 서비스가 실행될 때마다
		Log.d("service","onStartCommand");
		mp.start();
		Toast.makeText(getApplicationContext(), "음악이 시작됩니다.", Toast.LENGTH_SHORT).show();
		super.onStart(intent, startId);
	} // end of onStart
	@Override
	public void onDestroy() { // 서비스가 종료될 때
		super.onDestroy();
		Log.d("service","onDestroy");
		mp.stop(); // 음악중지
	} // end of onDestroy
} // end of class
