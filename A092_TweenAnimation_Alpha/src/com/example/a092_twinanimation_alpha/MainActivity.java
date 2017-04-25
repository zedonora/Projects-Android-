package com.example.a092_twinanimation_alpha;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		Button b5 = (Button) findViewById(R.id.button5);
		final ImageView iv = (ImageView) findViewById(R.id.imageView1);
		
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 알파 애니메이션 시작
				Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), 
						R.anim.myalpha);
				iv.startAnimation(anim);
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.myscale);
				iv.startAnimation(anim);
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.mytranslate);
				iv.startAnimation(anim);
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.myrotate);
				iv.startAnimation(anim);
			}
		});
		b5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.myset);
				iv.startAnimation(anim);
			}
		});
	} // end of onCreate
} // end of class
