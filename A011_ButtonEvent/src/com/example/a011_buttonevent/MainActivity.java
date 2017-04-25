package com.example.a011_buttonevent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.textView1);
		findViewById(R.id.button1).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) { // 터치시 호출되는 콜백메서드
				// 터치 : 다운, 무브, 업
				String str = "";
				if (event.getAction()==MotionEvent.ACTION_DOWN) {
					str = "터치 다운";
				} else if (event.getAction()==MotionEvent.ACTION_MOVE) {
					str = "터치 무브";
				} else if (event.getAction()==MotionEvent.ACTION_UP) {
					str = "터치 업";
				}
				printMessage(str);
				return false;
			}
		});
		findViewById(R.id.button2).setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) { // 롱클릭시 호출되는 콜백메서드
				printMessage("롱클릭됨");
				return false;
			}
		});
		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 클릭시 호출되는 콜백메서드
				printMessage("클릭됨");
			}
		});
		Button b4 = (Button) findViewById(R.id.button4);
		b4.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				String str = "";
				if (event.getAction()==MotionEvent.ACTION_DOWN) {
					str = "터치 다운";
				} else if (event.getAction()==MotionEvent.ACTION_MOVE) {
					str = "터치 무브";
				} else if (event.getAction()==MotionEvent.ACTION_UP) {
					str = "터치 업";
				}
				printMessage(str);
				return false; // true는 이벤트를 소비시킴 -> 그래서 롱클릭과 클릭이 반응을 못함.
			}
		});
		b4.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				printMessage("롱클릭됨");
				return false; // 소비시킴
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				printMessage("클릭됨");
			}
		});
	} // end of onCreate
	/** 받은 문자열을 TextView 에 보여주고, Log 도 찍어주는 메서드 */
	void printMessage(String str) {
		tv.setText(str);
		Log.d("Event",str); // d는 level을 나타내는 것 d까지만 쓰자.
	}
} // end of class
