package com.example.a072_draw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
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
	} // 생성자
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cadence);
		canvas.drawBitmap(bitmap, 100, 100, null);
		
		Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, 800, 400, false);
		canvas.drawBitmap(bitmap2, 100, 600, null);

		Matrix m = new Matrix();
//		m.preScale(1, -1);
		degree++;
		m.preRotate(degree);
		Bitmap bitmap3 = Bitmap.createBitmap(bitmap,0,0,
				bitmap.getWidth(),bitmap.getHeight(),m,false);
		canvas.drawBitmap(bitmap3,100,1000,null);
		invalidate();
	}
	int degree;
} // end of class
