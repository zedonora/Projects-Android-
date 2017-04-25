package com.example.a031_intent2;

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
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		OnClickListener ocl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 각 화면으로 전환
				Intent intent = new Intent(getApplicationContext(), Apple.class);
				intent.putExtra("과일이름",((Button)v).getText().toString()); // 데이터를 실어서 보냄
				
				startActivity(intent); // 화면전환
			}
		};
		b1.setOnClickListener(ocl);
		b2.setOnClickListener(ocl);
		b3.setOnClickListener(ocl);
		b4.setOnClickListener(ocl);
	} // end of onCreate
} // end of class
