package com.example.a033_intent3;

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
		
		final Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 다음화면으로 가기
//				Intent intent = new Intent(getApplicationContext(), // 현재화면 제어권자
//						Next.class); // 다음 넘어갈 화면 (명시적 인텐트)
				
				// 다음 넘어갈 화면 (묵시적 인텐트)
				Intent intent = new Intent("android.intent.action.VIEW"); 
				startActivity(intent);
			}
		});
	} // end of onCreate
} // end of class
