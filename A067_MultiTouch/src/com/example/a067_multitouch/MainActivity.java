package com.example.a067_multitouch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 멀티 터치
		tv = (TextView) findViewById(R.id.textView1);
		tv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				String str = "";
//				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				switch (event.getActionMasked()) {
				case MotionEvent.ACTION_DOWN : // 손가락 하나 눌렀을 때
					str += "ACTION_DOWN";
					str += ", ("+event.getY()+","+event.getX()+")";
					break;
				case MotionEvent.ACTION_POINTER_DOWN: // 손가락 여러개 눌렀을 때
					str += "ACTION_POINTER_DOWN";
					for (int i = 0; i < event.getPointerCount(); i++) { // 멀티터치 개수
						int id = event.getPointerId(i); // 손가락에 대한 id
						str += ", "+id+"("+event.getY(i)+","+event.getX(i)+")";
					}
					break;
				case MotionEvent.ACTION_MOVE : // 손가락 하나 눌러서 움직일 때
					str += "ACTION_MOVE";
					for (int i = 0; i < event.getPointerCount(); i++) {
						int id = event.getPointerId(i); // 손가락에 대한 id
						str += ", "+id+"("+event.getY(i)+","+event.getX(i)+")";
					}
					break;
				case MotionEvent.ACTION_UP : // 손가락 하나 눌렀던 것을  뗄 때
					str += "ACTION_UP";
					str += ", ("+event.getY()+","+event.getX()+")";
					break;
				case MotionEvent.ACTION_POINTER_UP: // 손가락 여러개 떼었을 때
					str += "ACTION_POINTER_UP";
					for (int i = 0; i < event.getPointerCount(); i++) {
						int id = event.getPointerId(i); // 손가락에 대한 id
						str += ", "+id+"("+event.getY(i)+","+event.getX(i)+")";
					}
					break;
				}
				p(str);
				return true;
			}
		});
	} // end of onCreate
	void p(String str) {
		Log.d("multi",str); // 로그
		tv.setText(str); // tv
	}
} // end of class
