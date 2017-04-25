package com.example.a101_project;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		
		// 이미지 객체 연결
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		
		// 영롱님 translate 애니메이션
		Animation anim_young = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.intro_anim);
		anim_young.setFillAfter(true);
		iv.startAnimation(anim_young);

		// 글자 Alpha 애니메이션
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		AlphaAnimation anim = new AlphaAnimation(1.0f, 0.5f);
		anim.setDuration(3000);// 지연시간
		anim.setRepeatCount(-1);
		anim.setInterpolator(new BounceInterpolator());
		tv1.startAnimation(anim);
		
		// 영롱님 클릭시 동작
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// 현재 Activity 종료 후 MainActivity로 이동
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				finish();
			}
		}); // end of iv.setOnClickListener(new OnClickListener()
	} // end of onCreate
} // end of class
