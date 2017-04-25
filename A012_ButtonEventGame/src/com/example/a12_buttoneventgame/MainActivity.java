package com.example.a12_buttoneventgame;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	int num = 10;
	private Point p;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ImageView iv = (ImageView) findViewById(R.id.imageView1);
		Button bUp = (Button) findViewById(R.id.button4);
		Button bDown = (Button) findViewById(R.id.button2);
		Button bLeft = (Button) findViewById(R.id.button1);
		Button bRight = (Button) findViewById(R.id.button3);
		
		// 스크린의 크기 얻어오기
		Display d = getWindowManager().getDefaultDisplay();
		p = new Point();
		d.getSize(p); // 폭과 높이 얻어오기
		
		bUp.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float y = iv.getY()-num;
				if(y<0){
					y = 0;
				}
				iv.setY(y);
				return false;
			}
		});
		
		bDown.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float y = iv.getY()+num;
				if(y > p.y-400){
					y = p.y-400;
				}
				iv.setY(y);
				return false;
			}
		});
		
		bLeft.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = iv.getX()-num;
				if (x < 0){
					x = 0;
				}
				iv.setX(x);
				return false;
			}
		});
		
		bRight.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = iv.getX()+num;
				if(x>p.x-100) {
					x = p.x -100;
				}
				iv.setX(x);
				return false;
			}
		});
	} // end of onCreate
} // end of class
