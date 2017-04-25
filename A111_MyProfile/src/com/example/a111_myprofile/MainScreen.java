package com.example.a111_myprofile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainScreen extends Activity {
	Animation anim;
	TextView tv;
	boolean show;
	Handler handler = new Handler();
	boolean b=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscreen );
		anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.startani);
		tv = (TextView)findViewById(R.id.textView1);
		tv.setVisibility(View.INVISIBLE);
		tv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getActionMasked()) {
				case MotionEvent.ACTION_DOWN:
					if(b){
						Intent intent = new Intent(getApplicationContext(),MainActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.nextin, R.anim.nextout);
						finish();
					}
					break;
				}
				return false;
			}
		});
	}//end of void onCreate
	@Override
	protected void onPause() {
		super.onPause();
		handler.removeCallbacksAndMessages(0);
	}
	@Override
	protected void onResume() {
		super.onResume();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				tv.setVisibility(View.VISIBLE);
				b=true;
			}
		}, 500);
		tv.startAnimation(anim);//TextView무한 깜빡임
	}
}//end of class MainActivity
