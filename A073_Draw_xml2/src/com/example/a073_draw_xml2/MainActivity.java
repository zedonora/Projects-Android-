package com.example.a073_draw_xml2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	MyView mv;
	static int prog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		SeekBar sb = (SeekBar) findViewById(R.id.seekBar1);
		mv = (MyView) findViewById(R.id.myView1);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mv.setChangeColor(Color.RED);
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mv.setChangeColor(Color.BLUE);
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mv.clear();
			}
		});
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				prog = progress;
			}
		});
	} // end of onCreate
} // end of class

class MyView extends View {
	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MyView(Context context) {
		super(context);
	}
	public void setChangeColor(int color) {
		paint.setColor(color);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN : // 손가락 1개 눌렀을 때
		case MotionEvent.ACTION_POINTER_DOWN : // 손가락 여러개 눌렀을 때
			x = event.getX();
			y = event.getY();
			lastX = x;
			lastY = y;
			break;
		case MotionEvent.ACTION_MOVE : // 손가락 대고 움직일 때
		case MotionEvent.ACTION_UP : // 손가락 1개 뗐을 때
		case MotionEvent.ACTION_POINTER_UP : // 손가락 여러개 뗐을 때
			x = event.getX();
			y = event.getY();
//			myCan.drawCircle(x, y, 10, paint); // 원
			paint.setStrokeWidth(MainActivity.prog);
			myCan.drawLine(lastX, lastY, x, y, paint); // 선
			lastX = x;
			lastY = y;
//			myCan.drawPoint(x, y, paint); // 점
			break;
		}
		invalidate(); // 화면 갱신하도록 호출
		return true;
	}
	float x,y;
	float lastX; // 이전의 좌표
	float lastY;
	Canvas myCan;
	Bitmap myBit;
	Paint paint = new Paint();
	public void clear() {
		myBit.eraseColor(Color.WHITE);
		invalidate();
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// 화면의 크기가 바뀌었을 때 호출 되는 메서드
		myBit = Bitmap.createBitmap(w,h,
				Bitmap.Config.ARGB_8888); // 색의 종류
		myCan = new Canvas(myBit);
		clear();
		paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
	}
	@Override
	protected void onDraw(Canvas canvas) { // 화면을 다시 그릴 때
		canvas.drawBitmap(myBit, 0, 0, paint); // 저장해둔 비트맵을 화면에 그리기
	} 
} // end of class MyView
