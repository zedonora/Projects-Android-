package com.example.a069_draw;

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
class MyView extends View{
	public MyView(Context context) {
		super(context);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		canvas.drawRect(50, 100, 200, 200, paint); // 사각형
		
		paint.setStrokeWidth(10); // 선 두께, 단위 픽셀
		paint.setStyle(Paint.Style.STROKE); // 외곽선
//		paint.setStyle(Paint.Style.FILL); // 내용채우기
//		paint.setStyle(Paint.Style.FILL_AND_STROKE); // 외곽선 + 내용채우기
		
		paint.setColor(Color.RED);
		canvas.drawPoint(250, 50, paint); // 점
		
		canvas.drawLine(300, 50, 400, 50, paint); // 선
		
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(false); // 외곽석 윤곽이 계단식의 색깔 차이가 보임
		canvas.drawCircle(600, 200, 200, paint);
		
		paint.setAntiAlias(true); // 외곽선 윤곽이 부드럽게 처리됨
		canvas.drawCircle(600, 600, 200, paint);
		
		
		paint.setColor(Color.MAGENTA);
		paint.setTextSize(100); // 글자 크기 키우기
		canvas.drawText("안녕하세요", 100, 400, paint); // 글자 출력
		
		int w = canvas.getWidth(); // 도화지의 폭
		int h = canvas.getHeight(); // 도화지의 높이
		canvas.drawText("[폭"+w+",높이"+h+"]", 0, 800, paint);
		
		paint.setColor(Color.BLUE);
		canvas.drawRoundRect(new RectF(50, 600, 400, 800), 50, 50, paint); // 둥근 사각형
		
		paint.setColor(Color.GREEN);
		canvas.drawOval(new RectF(50, 850,400,1000),paint); // 타원
		
		canvas.drawLines(new float[]{10,10,100,100,  
									100,100,100,200,	
									100,200,500,400}, paint); // 여러개 직선
		// 4개의 숫자가 직선 하나를 표현한다.
		
		paint.setColor(Color.CYAN);
		canvas.drawArc(new RectF(400,800,600,1000), 0, 120, true, paint); // 원호
		// startAngle : 시작 각도 , sweepAngle : 그려줄 각도
		// useCenter : 중심점과 양쪽 끝점을 연결 할지 여부
	}
} 
