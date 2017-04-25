package com.example.a118_broadcastreceiver2;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private MyBroadcastReceiver mbr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 방송 날리기
				Intent intent = new Intent("com.receiver.MyBroadcastReceiver2");
				sendBroadcast(intent);
			}
		});
	} // end of onCreate
	@Override
	protected void onResume() {
		super.onResume();
		mbr = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter("com.receiver.MyBroadcastReceiver2");
		registerReceiver(mbr, filter); // Broadcast 수신 등록
	}
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mbr); // Broadcast 수신 해제
	}
} // end of class
