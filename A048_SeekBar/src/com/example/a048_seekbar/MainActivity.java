package com.example.a048_seekbar;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Handler handler = new Handler(); // 화면을 그려주기 위해서 필요
	Runnable r = new Runnable() {
		@Override
		public void run() {
			// 해야할 작업
			if(sb.getProgress()<sb.getMax()){
				sb.setProgress(sb.getProgress()+1);
				handler.postDelayed(r, 100);
			}
		}
	};
	Runnable r2 = new Runnable() {
		@Override
		public void run() {
			iv.setX(iv.getX()+1);
			iv.setY(iv.getY()-0.5f);
//			if(iv.getX()<size.x-100 && iv.getY()<size.y-100) { // 화면 안쪽이면
			
			// 1. 이미지 뷰에서 사각형 영역 얻어오기
			Rect re1 = new Rect();
			iv.getGlobalVisibleRect(re1);
			Rect re2 = new Rect();
			iv.getGlobalVisibleRect(re2);
			
			re1.contains(3,4); // (x,y)->(3,4)가 사각영역에 포함되었는지 리턴
//			r1.contains(r2); // 포함되었는지 리턴
//			r1.intersects(r2); // 일부 영역이 겹치는지 리턴
			
			if(!re1.intersect(re2)) { // 충돌이 아니면
				handler.postDelayed(r2, 10);
			} else { 
				Toast.makeText(getApplicationContext(), 
						"Runnable2종료", Toast.LENGTH_SHORT).show();
			}
		}
	};
	SeekBar sb;
	ImageView iv;
	Point size;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 화면 크기
		Display display = getWindowManager().getDefaultDisplay();
		size = new Point();
		display.getSize(size);
		
		final TextView tv = (TextView) findViewById(R.id.textView1);
		Button b = (Button) findViewById(R.id.button1);
		sb = (SeekBar) findViewById(R.id.seekBar1);
		iv = (ImageView) findViewById(R.id.imageView1);
//		sb.setMax(50);
//		sb.getProgress(); // 현재 값을 얻어옴
//		sb.setProgress(30); // 현재 값을 설정
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			} // 썸네일에서 손을 땠을 때 호출되는 콜백 메서드
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			} // 썸네일에 손을 댔을 때 호출되는 콜백 메서드
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				tv.setText("값 : "+progress);
			} // 값이 변경되었을 때 호출되는 콜백 메서드
		});
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handler.post(r);
			}
		});
		Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handler.post(r2);	
			}
		});
	} // end of onCreate
} // end of class
