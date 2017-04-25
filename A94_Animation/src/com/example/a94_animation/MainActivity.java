package com.example.a94_animation;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	ImageView iv1;
	ImageView iv2;
	ImageView iv3;
	ImageView iv4;
	ImageView iv5;
	ImageView ballun1;
	ImageView ballun2; 
	ImageView ballun3;
	ImageView ballun4;
	ImageView ballun5;
	ImageView ballun6;
	ImageView ballun7;
	ImageView ballun8;
	ImageView ballun9;
	ImageView ballun10;
	ImageView ballun11;
	ImageView star;
	Point p = new Point();
	Display d;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			star.setX(event.getX());
			star.setY(event.getY());
			star.setVisibility(View.VISIBLE);
			RotateAnimation anim = new RotateAnimation(0,3600,
					Animation.RELATIVE_TO_PARENT,100.0f);
			anim.setDuration(2000);
			star.startAnimation(anim);
			anim.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationEnd(Animation animation) {
					star.setVisibility(4);
				}
			});
			break;
		}
		return true;
	}
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		d= getWindowManager().getDefaultDisplay();
		d.getSize(p);
		Button b = (Button)findViewById(R.id.button1);
		iv1 = (ImageView)findViewById(R.id.imageView1);//크룡
		iv2 = (ImageView)findViewById(R.id.imageView3);//공룡
		iv4 = (ImageView)findViewById(R.id.imageView2);//구름 첫번째 꺼
		iv5 = (ImageView)findViewById(R.id.ImageView01);//구름 두번째꺼
		ballun1 = (ImageView)findViewById(R.id.ballun1);//풍선
		ballun2 = (ImageView)findViewById(R.id.ballun2);//풍선
		ballun3 = (ImageView)findViewById(R.id.ballun3);//풍선
		ballun4 = (ImageView)findViewById(R.id.ballun4);//풍선
		ballun5 = (ImageView)findViewById(R.id.ballun5);//풍선
		ballun6 = (ImageView)findViewById(R.id.ballun6);//풍선
		ballun7 = (ImageView)findViewById(R.id.ballun7);//풍선
		ballun8 = (ImageView)findViewById(R.id.ballun8);//풍선
		ballun9 = (ImageView)findViewById(R.id.ballun9);//풍선
		ballun10 = (ImageView)findViewById(R.id.ballunballun1);//풍선
		ballun11 = (ImageView)findViewById(R.id.ballunballun2);//풍선
		star = (ImageView)findViewById(R.id.star);
		star.setVisibility(View.INVISIBLE);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				go(1);
			}
		});
	}//end of void onCreate
	public void go(int num){
		switch (num) {
		case 1:
			TranslateAnimation anim1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,
														Animation.RELATIVE_TO_SELF,500.0f);
			anim1.setDuration(2000);
			iv1.startAnimation(anim1);
			anim1.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationEnd(Animation animation) {
					go(2);
					iv1.setX(iv4.getX());
					iv1.setY(iv4.getY()-100);
				}
			});
			break; 
		case 2:
			TranslateAnimation anim2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,
															Animation.RELATIVE_TO_SELF,500.0f);
			anim2.setDuration(2000);
			iv2.setAnimation(anim2);
			anim2.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationEnd(Animation animation) {
					go(3);
					iv2.setX(iv5.getX());
					iv2.setY(iv5.getY()-100);
				}
			});
			break;
		case 3:
			TranslateAnimation anim3 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,
															Animation.RELATIVE_TO_SELF,-700.0f);
			anim3.setDuration(2000);
			ballun1.setAnimation(anim3);
			ballun2.setAnimation(anim3);
			ballun3.setAnimation(anim3);
			ballun4.setAnimation(anim3);
			ballun5.setAnimation(anim3);
			ballun6.setAnimation(anim3);
			ballun7.setAnimation(anim3);
			ballun8.setAnimation(anim3);
			ballun9.setAnimation(anim3);
			ballun10.setAnimation(anim3); 
			ballun11.setAnimation(anim3);
			anim3.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationEnd(Animation animation) {
					ballun1.setX(ballun1.getX());
					ballun1.setY(-700.0f);
					ballun2.setY(-700.0f);
					ballun3.setY(-700.0f);
					ballun4.setY(-700.0f);
					ballun5.setY(-700.0f);
					ballun6.setY(-700.0f);
					ballun7.setY(-700.0f);
					ballun8.setY(-700.0f);
					ballun9.setY(-700.0f);
					ballun10.setY(-700.0f);
					ballun11.setY(-700.0f);
					}
			});
			break;
		case 4:
			break;
		}
	}
}//end of vlass MainActivity
