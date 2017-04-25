package com.example.a064_slidingpuzzle;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {
	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MyImageView(Context context) {
		super(context);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 위젯을 화면에 그릴때 크기를 조정할 수 있는 마지막 기회
		int w = MeasureSpec.getSize(widthMeasureSpec);
		int h = MeasureSpec.getSize(heightMeasureSpec);
		// w와 h 중에 최소값을 h에 넣고 그 다음 h의 값을 w에 넣는 방법
		w = h = Math.min(w, h);
		setMeasuredDimension(w, h);
	}
} // end of class
