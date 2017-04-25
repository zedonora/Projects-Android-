package com.example.a071_draw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
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
		paint.setTextSize(50); // 글자 크기
//		paint.setTextSkewX(1); // 비틀기
//		paint.setTextScaleX(2); // 글자를 옆으로 늘이기
//		
//		paint.setUnderlineText(true); // 밑줄
//		paint.setStrikeThruText(true); // 취소선
		
		paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
		canvas.drawText("DEFAULT, NORMAL", 50, 100, paint);
		
		paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL));
		canvas.drawText("DEFAULT_BOLD, NORMAL", 50, 300, paint);
		
		paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
		canvas.drawText("MONOSPACE, NORMAL", 50, 500, paint);
		
		paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
		canvas.drawText("SERIF, NORMAL", 50, 700, paint);
		
		paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
		canvas.drawText("SANS_SERIF, NORMAL", 50, 900, paint);
		
		paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "enblbk.ttf"));
		canvas.drawText("enblbk.ttf, NORMAL", 50, 1100, paint);
		
		Path path = new Path();
		
		canvas.drawTextOnPath("안녕하세요 반갑습니다 또만났군요", path, 0, 0, paint);
	} // end of onDraw
} // end of class MyView
