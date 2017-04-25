package com.example.a026_framelayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// FrameLayout 사용
		// 버튼 1을 누르면 버튼 1을 숨기고 -> 버튼 2가 보이게
		// 버튼 2을 누르면 버튼 2을 숨기고 -> 버튼 3이 보이게
		// 버튼 3을 누르면 버튼 3을 숨기고 -> 버튼 1이 보이게
		final Button b1 = (Button)findViewById(R.id.button1);
		final Button b2 = (Button)findViewById(R.id.button2);
		final Button b3 = (Button)findViewById(R.id.button3);
		b1.setVisibility(View.VISIBLE);
		b2.setVisibility(View.INVISIBLE);
		b3.setVisibility(View.INVISIBLE);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				b1.setVisibility(View.INVISIBLE);
				b2.setVisibility(View.VISIBLE);
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				b2.setVisibility(View.INVISIBLE);
				b3.setVisibility(View.VISIBLE);
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				b3.setVisibility(View.INVISIBLE);
				b1.setVisibility(View.VISIBLE);
			}
		});
	} // end of onCreate
} // end of class
