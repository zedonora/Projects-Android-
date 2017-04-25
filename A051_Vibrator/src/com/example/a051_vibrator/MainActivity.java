package com.example.a051_vibrator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 진동하기
		// 1. 권한을 얻어온다. androidManifest.xml
		// 2. 진동 객체를 얻어온다. 
		// 3. 진동 방법에 따라 진동시킨다.
		final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vibrator.vibrate(1000); // 1초 진동
			}
		});
//		b2.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				vibrator.vibrate(
//						//		   	대기,진동,대기,진동......
//						new long[]{100,100,100,300,100,500,100,1000}, // 진동패턴
//						-1); // 반복회수, 0:무한반복, -1:노반복, 양의정수 : 해당 index부터 무한반복
//			}
//		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vibrator.vibrate(
						//		   	대기,진동,대기,진동......
						new long[]{100,173,100,206,100,243,100,264,
								100,308,100,358,100,414}, // 진동패턴
						0); // 반복회수, 0:무한반복, -1:노반복, 양의정수 : 해당 index부터 무한반복
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vibrator.vibrate(
						//		   	대기,진동,대기,진동......
						new long[]{100,100,100,300,100,500,100,1000}, // 진동패턴
						0); // 반복회수, 0:무한반복, -1:노반복, 양의정수 : 해당 index부터 무한반복
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vibrator.cancel(); // 진동취소
			}
		});
	} // end of onCreate
} // end of class
