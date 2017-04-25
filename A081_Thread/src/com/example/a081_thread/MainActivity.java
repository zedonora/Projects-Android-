package com.example.a081_thread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.textView1);
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		b1.setOnClickListener(new OnClickListener() {
			int num;
			@Override
			public void onClick(View v) {
				tv.setText(num++ + "");
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				go(); // 화면을 그리는 MainUI Thread 에서는 오래 걸리는 작업을 하면 응답성이 좋지 않다.
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 오래 걸리는 작업은 사용자의 응답성을 높이기 위해서 별도의 쓰레드로 작성한다.
				// 별도의 쓰레드에서 화면의 변경을 직접 할 수 없다.
				// 별도의 쓰레드에서는 Handler, ... 을 이용해서 화면을 변경할 수 있다.
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						go();
					}
				});
				t.start();
			}
		});
	} // end of onCreate
	public void go() { // 오래걸리는 작업
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(2000);
				tv.setText("시간: "+i);
			} catch (InterruptedException e) {
				Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_SHORT).show();
			}
		}
		tv.setText("오래 걸리는 작업 종료");
		Log.d("go","오래걸리는 작업 종료");
	} // end of go
} // end of class
