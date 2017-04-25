package com.example.a034_intent4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		Button b5 = (Button) findViewById(R.id.button5);
		Button b6 = (Button) findViewById(R.id.button6);
		
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 전화 다이얼 
//				Intent intent = new Intent("android.intent.action.VIEW");
				Intent intent = new Intent(Intent.ACTION_VIEW, 
											Uri.parse("tel:010-3132-6483"));
				startActivity(intent);
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 전화 걸기 - 사용자의 권한을 획득해야함 AndroidManifest.xml
				Intent intent = new Intent(Intent.ACTION_CALL,
						Uri.parse("tel:010-3132-6483")); // 전화를 걸어버림
				startActivity(intent);
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 웹브라우저
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://cafe.naver.com/minsseam1"));
				startActivity(intent);
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 문자
				Intent intent = new Intent(Intent.ACTION_SENDTO,
						Uri.parse("smsto:010-3132-6483"));
				startActivity(intent);
			}
		});
		b5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 이메일
				Intent intent = new Intent(Intent.ACTION_SENDTO,
						Uri.parse("mailto:timeundtime@gmail.com"));
				startActivity(intent);
			}
		});
		b6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 지도
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("geo:37.493185, 126.875591"));
				startActivity(intent);
			}
		});
	} // end of onCreate
} // end of class
