package com.example.a006_activitylifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

// Activity : 화면을 그려주기 위한 컴포넌트
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("ActivityLifeCycle", "onCreate"); // tag, message
	} // end of onCreate
	/**
	 * Activity LifeCycle 메서드 - on~ ()
	 *  각 메서드에는 super.on~() 자신의 부모메서드를 호출하는 코드
	 */
	@Override
	protected void onStart() {
		super.onStart();
		Log.d("ActivityLifeCycle", "onStart");
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("ActivityLifeCycle", "onReStart");
	}
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("ActivityLifeCycle", "onResume");
	}
	@Override
	protected void onPause() {
		super.onPause();
		Log.d("ActivityLifeCycle", "onPause");
	}
	@Override
	protected void onStop() {
		super.onStop();
		Log.d("ActivityLifeCycle", "onStop");
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("ActivityLifeCycle", "onDestroy");
	}
} // end of class
