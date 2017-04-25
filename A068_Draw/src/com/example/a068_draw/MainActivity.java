package com.example.a068_draw;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this)); // 화면의 view 단을 연결
	} // end of onCreate
} // end of class
class MyView extends View{
	Paint paint = new Paint();
	ArrayList<Point> p = new ArrayList<Point>(); // 터치한 y,x의 좌표를 저장할 변수
	public MyView(Context context) { // 생성자
		super(context);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) { // 뷰를 터치했을 때 호출되는 콜백 메서드
		Toast.makeText(getContext(), "onTouchEvent", Toast.LENGTH_SHORT).show(); // 토스트
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN : // 손가락 한개 다운
			p = new ArrayList<Point>();
			p.add(new Point((int)event.getX(),(int)event.getY()));
			invalidate(); // 화면을 갱신해야 할 때 -> 화면을 갱신하면서 Draw를 호출한다.
			break;
		case MotionEvent.ACTION_POINTER_DOWN : // 손가락 여러개 다운
		case MotionEvent.ACTION_MOVE : // 손가락 한개 or 여러개 무브
		case MotionEvent.ACTION_POINTER_UP : // 손가락 여러개 업
			p = new ArrayList<Point>();
			for (int i = 0; i < event.getPointerCount(); i++) {
				p.add(new Point((int)event.getX(i),(int)event.getY(i)));
			}
			invalidate(); // 화면을 갱신해야 할 때 -> 화면을 갱신하면서 Draw를 호출한다.
			break;
		case MotionEvent.ACTION_UP: // 손가락 한개 업
			p = new ArrayList<Point>();
			invalidate(); // 화면을 갱신해야 할 때 -> 화면을 갱신하면서 Draw를 호출한다.
			break;
		}
		return true;
	}
//	int colors[] = {Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN,Color.MAGENTA,
//			Color.CYAN,Color.GRAY};
//	int index;
	Random ran = new Random();
	int cr = 128; // 레드 ,  0~255
	int cg = 128; // 그린
	int cb = 128; // 블루
	@Override
	protected void onDraw(Canvas canvas) { // 화면 그릴 때 호출
		super.onDraw(canvas);
		for (int i = 0; /*p!=null&&*/i < p.size(); i++) {
//			index++;
//			if(index>=colors.length) {
//				index = 0;
//			}
//			paint.setColor(colors[index]);
			cr += ran.nextInt(11) -5; // -5 ~ 5
			cg += ran.nextInt(11) -5; // -5 ~ 5
			cb += ran.nextInt(11) -5; // -5 ~ 5
			if (cr>255) {
				cr = 255;
			}
			if (cr<0) {
				cr = 0;
			}
			if (cg>255) {
				cg = 255;
			}
			if (cg<0) {
				cg = 0;
			}
			if (cb>255) {
				cb = 255;
			}
			if (cb<0) {
				cb = 0;
			}
			paint.setColor(new Color().rgb(cr, cg, cb));
			canvas.drawCircle(p.get(i).x, p.get(i).y, 100, paint);
		}
	}
}