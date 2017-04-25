package com.example.a49_water;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class IntroActivity extends Activity {
	Handler handler = new Handler();
	Intent intent;
	MediaPlayer mpIntro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);

		intent = new Intent(getApplicationContext(), MainActivity.class);

		mpIntro = MediaPlayer.create(this, R.raw.openning);
		mpIntro.start();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(intent);
				finish();
			}
		}, 6000);

	} // end of onCreate

	@Override
	protected void onPause() {
		super.onPause();
		handler.removeMessages(0);
		if (mpIntro != null) {
			mpIntro.stop();
		}
	} // end of onPause

} // end of class
