package com.example.a093_tweenanimation_alpha2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		Button b5 = (Button) findViewById(R.id.button5);
		iv = (ImageView) findViewById(R.id.imageView1);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 알파 (투명도) 애니메이션 시작
				AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
				anim.setDuration(3000); // 지연시간
				anim.setRepeatCount(3); // 반복회수
				anim.setInterpolator(new BounceInterpolator());
				iv.startAnimation(anim);
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // scale(크기변경) 애니메이션 시작
				ScaleAnimation anim = 
						new ScaleAnimation(1,0,1,0, 			// fromX, toX, fromY, toY
							Animation.RELATIVE_TO_SELF, 0.5f,  	// 피봇 타입, 비율 
							Animation.RELATIVE_TO_SELF, 0.5f); 	// 피봇 타입, 비율 
				anim.setDuration(3000); // 지연시간
				anim.setRepeatCount(3); // 반복회수
				anim.setInterpolator(new BounceInterpolator());
//				iv.setPivotX(100); // 이미지 뷰에서 피봇 지정 (픽셀)
//				iv.setPivotY(100); 
				iv.startAnimation(anim);
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // translate(이동) 애니메이션 시작
				TranslateAnimation anim = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF,	-0.5f, 	// 피봇타입, fromX
						Animation.RELATIVE_TO_SELF,	0.5f,	// 피봇타입, toX
						Animation.RELATIVE_TO_SELF,	0,		// 피봇타입, fromY
						Animation.RELATIVE_TO_SELF,	0);		// 피봇타입, toY
				anim.setDuration(3000); // 지연시간
				anim.setRepeatCount(3); // 반복회수
				iv.startAnimation(anim);
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // rotate(회전) 애니메이션 시작
				RotateAnimation anim = new RotateAnimation(
						0, -3600, // fromDegree, toDegree
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				anim.setDuration(3000); // 지연시간
				iv.startAnimation(anim);
			}
		});
		b5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cnt = 0;
				go(1);
//				AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
//				anim.setDuration(3000); // 지연시간
//				anim.setRepeatCount(3); // 반복
//				anim.setAnimationListener(new AnimationListener() {
//					@Override
//					public void onAnimationStart(Animation animation) {
//						// 애니메이션 시작시 호출되는 콜백 메서드
//						setTitle("Start");
//					}
//					@Override
//					public void onAnimationRepeat(Animation animation) {
//						// 애니메이션 반복시 호출되는 콜백 메서드
//						setTitle("Repeat");
//					}
//					@Override
//					public void onAnimationEnd(Animation animation) {
//						// 애니메이션 종료시 호출되는 콜백 메서드
//						setTitle("End");
//					}
//				});
//				iv.startAnimation(anim);
			}
		});
	} // end of onCreate
	public void go(int num) {
		switch (num) {
		case 1: // Alpha
			AlphaAnimation anim1 = new AlphaAnimation(0.0f, 1.0f);
			anim1.setDuration(3000); // 지연시간
			anim1.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}
				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				@Override
				public void onAnimationEnd(Animation animation) { // 애니메이션 종료시
					go(2);
				}
			});
			iv.startAnimation(anim1);
			break;
		case 2: // Scale
			ScaleAnimation anim2 = 
			new ScaleAnimation(1,0,1,0, 			// fromX, toX, fromY, toY
					Animation.RELATIVE_TO_SELF, 0.5f,  	// 피봇 타입, 비율 
					Animation.RELATIVE_TO_SELF, 0.5f); 	// 피봇 타입, 비율 
			anim2.setDuration(3000); // 지연시간
			anim2.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}
				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				@Override
				public void onAnimationEnd(Animation animation) {
					go(3);
				}
			});
			iv.startAnimation(anim2);
			break;
		case 3: // Translate
			TranslateAnimation anim3 = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF,	-0.5f, 	// 피봇타입, fromX
					Animation.RELATIVE_TO_SELF,	0.5f,	// 피봇타입, toX
					Animation.RELATIVE_TO_SELF,	0,		// 피봇타입, fromY
					Animation.RELATIVE_TO_SELF,	0);		// 피봇타입, toY
			anim3.setDuration(3000); // 지연시간
			anim3.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}
				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				@Override
				public void onAnimationEnd(Animation animation) {
					go(4);
				}
			});
			iv.startAnimation(anim3);
			break;
		case 4: // Rotate
			RotateAnimation anim4 = new RotateAnimation(
					0, -3600, // fromDegree, toDegree
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			anim4.setDuration(3000); // 지연시간
			anim4.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}
				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				@Override
				public void onAnimationEnd(Animation animation) {
					cnt++;
					if(cnt<2) {
						go(1);
					} 
				}
			});
			iv.startAnimation(anim4);
			break;
		} // end of switch
	} // end of go()
	int cnt;
} // end of class
