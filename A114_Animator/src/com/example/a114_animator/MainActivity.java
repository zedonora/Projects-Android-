package com.example.a114_animator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/** ObjectAnimator 활용 예제 */
public class MainActivity extends Activity {
	private Button b;
	private ImageView iv;
	private RelativeLayout rl;
	private AnimationDrawable ad;
	AnimatorSet as;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		b = (Button) findViewById(R.id.button1);
		iv = (ImageView) findViewById(R.id.imageView1);
		rl = (RelativeLayout) findViewById(R.id.relativeLayout1);
		ad = (AnimationDrawable) iv.getBackground();
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(ad.isRunning()){
					ad.stop();
					as.end();
					b.setText("애니메이션 시작");
				} else {
					go();
					b.setText("애니메이션 멈춤");
				}
			}
		});
	} // end of onCreate
	
	public void go(){
		int rlW = rl.getWidth();
		int rlH = rl.getHeight();
		int imgW = iv.getWidth();
		int imgH = iv.getHeight();
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(iv, "x", 100, rlW-imgW-100);
		anim1.setDuration(1000);
		
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv, "rotation", 0,90);
		anim2.setDuration(1000);
		anim2.setStartDelay(1000);
		
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(iv, "y", 100, rlH-imgH-100);
		anim3.setDuration(1000);
		anim3.setStartDelay(2000);
		
		ObjectAnimator anim4 = ObjectAnimator.ofFloat(iv, "rotation", 90, 180);
		anim4.setDuration(1000);
		anim4.setStartDelay(3000);
		
		ObjectAnimator anim5 = ObjectAnimator.ofFloat(iv, "x", rlW-imgW-100, 100);
		anim5.setDuration(1000);
		anim5.setStartDelay(4000);
		
		ObjectAnimator anim6 = ObjectAnimator.ofFloat(iv, "rotation", 180, 270);
		anim6.setDuration(1000);
		anim6.setStartDelay(5000);
		
		ObjectAnimator anim7 = ObjectAnimator.ofFloat(iv, "y", rlH-imgH-100, 100);
		anim7.setDuration(1000);
		anim7.setStartDelay(6000);
		
		ObjectAnimator anim8 = ObjectAnimator.ofFloat(iv, "rotation", 270, 360);
		anim8.setDuration(1000);
		anim8.setStartDelay(7000);
		
		as = new AnimatorSet();
		as.playTogether(anim1, anim2, anim3, anim4, anim5, anim6, anim7, anim8);
		as.start(); // 위젯 위치, 방향
		ad.start(); // 달리는 배경
		
		as.addListener(new AnimatorListener() {
			public void onAnimationStart(Animator animation) { }
			public void onAnimationRepeat(Animator animation) { }
			public void onAnimationEnd(Animator animation) {
				ad.stop();
				b.setText("애니메이션 시작");
			}
			public void onAnimationCancel(Animator animation) { }
		});
	} // end of go
} // end of class
