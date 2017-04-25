package com.example.a073_draw_xml;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	MyView mv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		mv = (MyView) findViewById(R.id.myView1);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mv.paint.setColor(Color.RED);
				mv.invalidate();
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mv.paint.setColor(Color.BLUE);
				mv.invalidate();
			}
		});
	} // end of onCreate
} // end of class

class MyView extends View {
	ArrayList<pluColor> p = new ArrayList<pluColor>();
	Paint paint = new Paint();
	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MyView(Context context) {
		super(context);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			p.add(new pluColor(new Point((int)event.getX(),(int)event.getY())
					,paint));
			break;
		}
		return true;
	}
	@Override
	protected void onDraw(Canvas canvas) {
//		paint.setColor(Color.GREEN);
		for (int i = 0; i < p.size(); i++) {
			canvas.drawCircle(p.get(i).point.x, p.get(i).point.y, 10, p.get(i).paint);
		}
	} 
} // end of class MyView
class pluColor{
	Point point;
	Paint paint;
	public pluColor() {
	}
	public pluColor(Point point, Paint paint) {
		this.point = point;
		this.paint = paint;
	}
}