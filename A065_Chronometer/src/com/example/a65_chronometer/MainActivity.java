package com.example.a65_chronometer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	Handler handler = new Handler();
	Runnable r = new Runnable() {
		@Override
		public void run() {
			tv.setText("결과창"+a++);
			handler.postDelayed(r, 1000);
		}
	};
	boolean gameOn; // 시간 진행여부
	int a;
	TextView tv;
	final int TIME_OUT = 10; // sec
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.textView1);
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		final Chronometer c = (Chronometer) findViewById(R.id.chronometer1);
		c.setOnChronometerTickListener(new OnChronometerTickListener() {
			int x;
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				tv.setText(""+ x++);
//				time out 시간이 되면 종료
				long time = SystemClock.elapsedRealtime() - c.getBase();
				if (time/1000 >= TIME_OUT) {
					c.stop();
					tv.setText("종료");
				}
			}
		});
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				gameOn = true;
//				handler.post(r); // 핸들러 호출
				c.setBase(SystemClock.elapsedRealtime());
				c.start();
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				gameOn = false;
				c.stop();
			}
		});
	} // end of onCreate
} // end of class
