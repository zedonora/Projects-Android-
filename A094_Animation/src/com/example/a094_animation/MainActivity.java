package com.example.a094_animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {
	AnimationDrawable ad;
	ImageView iv1;
	Handler handler = new Handler();
	ImageView iv2;
	ImageView iv3;
	ImageView iv4;
	private ImageView iv5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv5 = (ImageView) findViewById(R.id.imageView5);
		iv1 = (ImageView) findViewById(R.id.imageView1);
		iv2 = (ImageView) findViewById(R.id.ImageView2);
		iv3 = (ImageView) findViewById(R.id.ImageView3);
		iv4 = (ImageView) findViewById(R.id.imageView4);
		ad = (AnimationDrawable) iv5.getBackground();
		ad.start();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.myalpha);
				iv1.startAnimation(anim);
				Animation anim1 = AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.myrotate);
				iv2.startAnimation(anim1);
				Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.myscale);
				iv3.startAnimation(anim2);
				Animation anim3 = AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.mytranslate);
				iv4.startAnimation(anim3);
			}
		}, 3000);
	} // end of onCreate
	@Override
	protected void onStop() {
		super.onStop();
		if(ad!=null) {
			ad.stop();
		}
	}
} // end of class
