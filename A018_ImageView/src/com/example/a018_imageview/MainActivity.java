package com.example.a018_imageview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		
		// 이미지 바꾸기 3가지
		// 1. 프로젝트 리소스(res)를 가져오기
		iv.setImageResource(R.drawable.i);
		
		// 2. drawable 객체 사용
		Drawable drawable = getResources().getDrawable(R.drawable.i2);
		iv.setImageDrawable(drawable);
		
		// 3. Bitmap 객체 사용
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.i3);
		iv.setImageBitmap(bm);
	} // end of onCreate
} // end of class
