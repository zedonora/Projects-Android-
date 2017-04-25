package com.example.a013_toast;

import java.util.Random;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Random ran = new Random();
	Point p;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), // 현재 화면의 제어권자
						"안녕 반가워 - 짧게", // 띄워줄 메시지
						Toast.LENGTH_SHORT) // 띄워줄 시간
						.show(); 
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), // 현재 화면의 제어권자
						"안녕 반가워 - 길게", // 띄워줄 메시지
						Toast.LENGTH_LONG) // 띄워줄 시간
						.show(); 
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast t = Toast.makeText(getApplicationContext(), // 현재 화면의 제어권자
							"위치변경", // 띄워줄 메시지
							Toast.LENGTH_SHORT); // 띄워줄 시간
				t.setGravity(Gravity.TOP | Gravity.LEFT, 100, 500);
				t.show();
			}
		});
		
		Display d = getWindowManager().getDefaultDisplay();
		p = new Point();
		d.getSize(p);
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast t = Toast.makeText(getApplicationContext(), // 현재 화면의 제어권자
						"랜덤 위치", // 띄워줄 메시지
						Toast.LENGTH_SHORT); // 띄워줄 시간
				int x = ran.nextInt(2*p.x);
				int y = ran.nextInt(p.y/2);
				if(2*p.x>x&&x>p.x) {
					x = x-p.x;
					x = -x;
				}
				t.setGravity(Gravity.BOTTOM, x, y);
				t.show();
			}
		});
	} // end of onCreate
} // end of class
