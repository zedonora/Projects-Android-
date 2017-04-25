package com.example.a116_service2;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service{
	
	public class MyBinder extends Binder {
		LocalService getService() {
			return LocalService.this; // 현재의 서비스 객체를 리턴
		}
	}
	IBinder b = new MyBinder();
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("test","onCreate - service");
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("test","onStartCommand - service");
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public IBinder onBind(Intent intent) { // 연결 서비스시 사용
		Log.d("test","onBind - service");
		return b;
	}
	
	public int getRanNum() {
		Log.d("test","getRanNum - service");
		Random ran = new Random();
		return ran.nextInt(100);
	}
	MediaPlayer mp;
	public void musicPlay() {
		if (mp==null) {
			mp = MediaPlayer.create(getApplicationContext(), R.raw.chacha);
			mp.setLooping(false);
			mp.start();
			mp.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					LocalService.this.mp = null;
				}
			});
//		} else if (mp != null && !mp.isPlaying()) { // 재생이 다 끝난 상태일 때
//			mp.start();
		}
	}
	public void musicStop() {
		if (mp!=null) {
			mp.stop();
			mp = null;
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("test","onDestroy - service");
	}
} // end of class LocalService
