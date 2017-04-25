package com.example.a075_animation;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	} // end of onCreate
} // end of class
class MyView extends View {
	int x = 300, y = 100;
	public MyView(Context context) {
		super(context);
		setBackgroundColor(Color.WHITE);
		
		ValueAnimator va = ValueAnimator.ofInt(100, 1000, 500, 1500, 100);
		va.setDuration(4000); // 애니메이션 (한턴) 하는 시간
//		va.setInterpolator(new AccelerateInterpolator()); // 일정하게 증가
//		va.setInterpolator(new BounceInterpolator()); // 튀기는 것
		va.setInterpolator(new LinearInterpolator()); // 일정하게
		va.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				y = (Integer) animation.getAnimatedValue(); // 변경된 애니메이터 값을 얻어오기
				invalidate();
			}
		});
		va.setRepeatCount(1000); // 반복회수
//		va.setRepeatMode(ValueAnimator.REVERSE); // 반복모드, 재시작 or 역방향
		va.start(); // 애니메이션 시작
	} // constructor
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		canvas.drawCircle(x, y, 50, paint);
	} // end of onDraw
} // end of class MyView
