package com.example.a038_piano;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	int sound[] = new int[7];
	SoundPool sp ;
	int streamID;
	Handler handler;
	ArrayList <Integer> arSound = new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button bDo = (Button)findViewById(R.id.button1);
		final Button bRe = (Button)findViewById(R.id.button2);
		final Button bMi = (Button)findViewById(R.id.button3);
		final Button bFa = (Button)findViewById(R.id.button4);
		final Button bSol = (Button)findViewById(R.id.button5);
		final Button bRa = (Button)findViewById(R.id.button6);
		final Button bSi = (Button)findViewById(R.id.button7);
		
		sp = new SoundPool(7, AudioManager.STREAM_MUSIC, 0);
		
		sound[0] = sp.load(this,R.raw.ddo,1);
		sound[1] = sp.load(this,R.raw.re,1);
		sound[2] = sp.load(this,R.raw.mi,1);
		sound[3] = sp.load(this,R.raw.fa,1);
		sound[4] = sp.load(this,R.raw.sol,1);
		sound[5] = sp.load(this,R.raw.ra,1);
		sound[6] = sp.load(this,R.raw.si,1);
		handler = new Handler();
		bDo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				streamID = sp.play(sound[0], 1, 1, 0, 0, 1);
				bDo.setBackgroundColor(Color.LTGRAY);
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						bDo.setBackgroundColor(Color.TRANSPARENT);
					}
				}, 500);
				arSound.add(streamID);
			}
		});
		bRe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				streamID = sp.play(sound[1], 1, 1, 0, 0, 1);
				bRe.setBackgroundColor(Color.LTGRAY);
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						bRe.setBackgroundColor(Color.TRANSPARENT);
					}
				}, 500);
				arSound.add(streamID);
			}
		});
		bMi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				streamID = sp.play(sound[2], 1, 1, 0, 0, 1);
				bMi.setBackgroundColor(Color.LTGRAY);
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						bMi.setBackgroundColor(Color.TRANSPARENT);
					}
				}, 500);
				arSound.add(streamID);
			}
		});
		bFa.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				streamID = sp.play(sound[3], 1, 1, 0, 0, 1);
				bFa.setBackgroundColor(Color.LTGRAY);
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						bFa.setBackgroundColor(Color.TRANSPARENT);
					}
				}, 500);
				arSound.add(streamID);
			}
		});
		bSol.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				streamID = sp.play(sound[4], 1, 1, 0, 0, 1);
				bSol.setBackgroundColor(Color.LTGRAY);
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						bSol.setBackgroundColor(Color.TRANSPARENT);
					}
				}, 500);
				arSound.add(streamID);
			}
		});
		bRa.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				streamID = sp.play(sound[5], 1, 1, 0, 0, 1);
				bRa.setBackgroundColor(Color.LTGRAY);
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						bRa.setBackgroundColor(Color.TRANSPARENT);
					}
				}, 500);
				arSound.add(streamID);
			}
		});
		bSi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				streamID = sp.play(sound[6], 1, 1, 0, 0, 1);
				bSi.setBackgroundColor(Color.LTGRAY);
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						bSi.setBackgroundColor(Color.TRANSPARENT);
					}
				}, 500);
				arSound.add(streamID);
			}
		});
	} // end of onCreate
	@Override
	protected void onPause() {
		super.onPause();
		if(streamID!=0){
			sp.stop(streamID);
		}
	}
} // end of class
