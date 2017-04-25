package com.example.a032_intro;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
public class Intro extends Activity {
	Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro); // xml과 연결
	} // end of onCreate
	@Override
	protected void onResume() { 
		// 현재화면의 일부가 가려져서 onPause 된 상태 후 다시 앱이 화면에 나타날때 호출되는 콜백 메서드
		super.onResume();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() { // 예약할 작업을 기술
				// 화면전환
				Intent intent = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(intent);
				finish(); // 인트로 Activity 종료
			}
		}, 3000); // MilliSeconds ms 후에 실행할지

		// 시간끌기 -> 화면을 그리지 않은채로 시간을 지연시킨다.
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() { // 나중에 할 작업을 기술
//				// 화면전환
//				Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//				startActivity(intent);
//				finish(); // 인트로 Activity 종료
//			}
//		}, 3000); // ms 후에 실행할 지
		
//		handler.postDelayed(new Runnable() {
//			@Override
//			public void run() { // 예약할 작업을 기술
//				// 화면전환
//				Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//				startActivity(intent);
//				finish(); // 인트로 Activity 종료
//			}
//		}, 3000); // MilliSeconds ms 후에 실행할지
	} // end of onResume
	@Override
	protected void onPause() { // 현재화면의 일부가 가려질때 호출되는 콜백 메서드
//		timer.cancle(); // 예약작업을 취소하기 (onPause())
		super.onPause();
		handler.removeMessages(0); // 예약한 작업을 취소 -> 안해주면 다른 작업이 진행되는 도중에 예약된 작업이 실행 
	} // end of onPause()
} // end of class
