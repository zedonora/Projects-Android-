package com.example.a115_service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		Android 4대 컴포넌트중 하나인 Service
//		Service : 사용자 인터페이스 화면이 없다, 그래서 주로 background에서 동작하며 음악재생, 파일 입출력, 네트워크를 담당한다.
//		시작타입 서비스, 연결타입 서비스
//		시작타입 서비스 : 한번 시작되면 백그라운드에서 무한정 실행할 수 있다. 그리고 액티비티가 소멸하더라도 살아있다, 결과를 반환 못함
//		연결타입 서비스 : 클라이언트-서버 처럼 통신가능 
		
// 		Intent로 처리하고 AndroidManifest.xml 에 등록해야함
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 음악 재생 서비스 실행
				Intent intent = new Intent(getApplicationContext(), MusicService.class);
				startService(intent);
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 음악 재생 서비스 종료
				Intent intent = new Intent(getApplicationContext(), MusicService.class);
				startService(intent);
			}
		});
	} // end of onCreate
} // end of class
