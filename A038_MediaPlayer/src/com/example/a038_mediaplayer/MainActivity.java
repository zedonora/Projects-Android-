package com.example.a038_mediaplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	MediaPlayer mp;
	int position; // 노래 재생중인 위치 ms
	Button b1;
	Button b2;
	Button b3;
	Button b4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		b4 = (Button) findViewById(R.id.button4);
		
		b1.setEnabled(true);
		b2.setEnabled(false);
		b3.setEnabled(false);
		b4.setEnabled(false);
		
		b1.setOnClickListener(new OnClickListener() { // 재생
			@Override
			public void onClick(View v) {
				b1.setEnabled(false);
				b2.setEnabled(true);
				b3.setEnabled(false);
				b4.setEnabled(true);
				
//				mp.isPlaying() // 현재 재생중인지 여부
				mp = MediaPlayer.create(getApplicationContext(), R.raw.aaaa);
				mp.setLooping(false); // 반복재생 안함
				mp.start();
				
				// 음악이 다 끝나면 초기화
				mp.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						b1.setEnabled(true);
						b2.setEnabled(false);
						b3.setEnabled(false);
						b4.setEnabled(false);
					}
				});
			}
		});
		b2.setOnClickListener(new OnClickListener() { // 일시정지
			@Override
			public void onClick(View v) {
				b1.setEnabled(true);
				b2.setEnabled(false);
				b3.setEnabled(true);
				b4.setEnabled(true);
				
				position = mp.getCurrentPosition(); // 멈추기 직전에 재생위치를 저장
				mp.pause();
			}
		});
		b3.setOnClickListener(new OnClickListener() { // 재시작
			@Override
			public void onClick(View v) {
				b1.setEnabled(false);
				b2.setEnabled(true);
				b3.setEnabled(false);
				b4.setEnabled(true);
				
				mp.seekTo(position); // 멈췄던 지점으로 이동 후
				mp.start();
			}
		});
		b4.setOnClickListener(new OnClickListener() { // 종료
			@Override
			public void onClick(View v) {
				b1.setEnabled(true);
				b2.setEnabled(false);
				b3.setEnabled(false);
				b4.setEnabled(false);
				mp.stop();
			}
		});
	} // end of onCreate
	@Override
	protected void onPause() {
		super.onPause();
		// 화면의 일부가 가려졌을 때 호출되는 콜백 메서드
		if(mp!=null) {
			b1.setEnabled(true);
			b2.setEnabled(false);
			b3.setEnabled(false);
			b4.setEnabled(false);
			mp.stop();
		}
	}
} // end of class
