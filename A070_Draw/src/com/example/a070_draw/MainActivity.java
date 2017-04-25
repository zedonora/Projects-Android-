package com.example.a070_draw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	} // end of onCreate
} // end of class
class MyView extends View {
//	int x, y;
	int x = 100, y=100;
	final int RIGHT	= 0;
	final int DOWN 	= 1;
	final int LEFT = 2;
	final int UP 	= 3;
	int dir = RIGHT; // 현재 진행 방향
	float startAngle;
	float sweepAngle;
	public MyView(Context context) {
		super(context);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.MAGENTA);
		paint.setStrokeWidth(5); // 선 두께
		
		// 호 돌리기
		Paint paint2 = new Paint();
		paint2.setColor(Color.CYAN);

		startAngle += 10;
		if (startAngle>=360){
			startAngle -= 360;
		}
		sweepAngle += 2;
		if (sweepAngle>=360){
			sweepAngle -= 360;
		}
		canvas.drawArc(new RectF(200,200,canvas.getWidth()-200, canvas.getHeight()-200), 
				startAngle, sweepAngle, false, paint2);
		
		// 원 돌리기
		switch (dir) {
		case RIGHT:
			x+=10;
			if (x>=canvas.getWidth()-100) {
				x = canvas.getWidth()-100;
				dir = DOWN;
			}
			break;
		case DOWN:
			y+=10;
			if (y>=canvas.getHeight()-100) {
				y = canvas.getHeight()-100;
				dir = LEFT;
			}
			break;
		case LEFT:
			x-=10;
			if (x<=100) {
				x = 100;
				dir = UP;
			}
			break;
		case UP:
			y-=10;
			if (y<=100) {
				y = 100;
				dir = RIGHT;
			}
			break;
		}
		canvas.drawCircle(x, y, 100, paint);
//		x+=5;
//		y+=5;
//		if(x>canvas.getWidth()){
//			x = 0;
//		}
//		if(y>canvas.getHeight()){
//			y = 0;
//		}
//		canvas.drawLine(0, 0, x, y, paint);
		invalidate(); // 화면 다시그려라
	} // end of onDraw
} // end of class MyView