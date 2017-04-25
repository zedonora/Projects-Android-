package com.example.a091_frameanimation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button b = (Button) findViewById(R.id.button1);
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		
		// background drawable 객체를 얻어옴
		final AnimationDrawable ad = (AnimationDrawable) iv.getDrawable(); // src
//		final AnimationDrawable ad = (AnimationDrawable) iv.getBackground(); // background
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(ad.isRunning()) { // 실행중인지 여부
					ad.stop();
					b.setText("시작");
				} else { // 현재 멈춰있는 상태
					ad.start(); // 애니메이션 시작
					b.setText("멈춤");
				}
			}
		});
	} // end of onCreate
} // end of class
