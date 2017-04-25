package com.example.a116_service2;

import com.example.a116_service2.LocalService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private LocalService mService; // 통신할 상대 Service 객체
	boolean onService; // 서비스의 실행 여부
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("test","onCreate");
		setContentView(R.layout.activity_main);
		
		// bind를 이용한 연결 서비스, 액티비티가 연결되어 있는 동안만 서비스를 실행할 수 있다.
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int num = mService.getRanNum();
				Toast.makeText(getApplicationContext(), 
						"Service 에서 받은 숫자"+num, Toast.LENGTH_SHORT).show();	
			}
		});
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 음악시작
				mService.musicPlay();
			}
		});
		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mService.musicStop();
			}
		});
	} // end of onCreate
	@Override
	protected void onStart() {
		super.onStart();
		Log.d("test","onStart");
		Intent intent = new Intent(getApplicationContext(), LocalService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
	@Override
	protected void onStop() {
		super.onStop();
		Log.d("test","onStop");
		// 실행 중인 서비스를 액티비티를 stop 하면서 함께 서비스종료
		if (onService) {
			unbindService(conn);
			onService = false;
			mService.musicStop(); // 음악종료
		}
	}
	ServiceConnection conn = new ServiceConnection() {
		// 서비스 연결이 맺어지면 호츨된다
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d("test","onServiceConnected");
			MyBinder binder = (MyBinder) service;
			mService = binder.getService();
			onService = true;
		}
//		서비스에 대한 연결이 끊어졌을 때 호출 - unbindService(conn) 실행시 호출되지 않음
//		일반적으로 서비스를 호스팅하는 프로세스가 손상되었거나 죽었을 때 발생
//		ServiceConnection 자체는 제거되지 않고,
//		서비스에 대한 바인딩은 활성 상태로 유지되며,
//		서비스가 다음에 실행될 때 onServiceConnected가 호출된다.
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d("test","onServiceDisconnected");
			onService = false;
		}
	};
} // end of class
