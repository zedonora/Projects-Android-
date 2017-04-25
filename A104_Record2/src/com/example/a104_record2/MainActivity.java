package com.example.a104_record2;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	String filename; // 녹음, 재생할 파일명
	MediaRecorder recorder; // 녹음  객체
	MediaPlayer player; // 재생 객체
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button bRecord = (Button) findViewById(R.id.button1);
		final Button bPlay = (Button) findViewById(R.id.button2);
		final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		// 절대경로 + 파일명 준비하기
		filename = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/test.3gp"; // 절대경로 : /storage/emulated/0
		bRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if ("녹음".equals(bRecord.getText().toString())) {
					// 녹음하기
					// 오디오 사용권한, 외부메모리 사용권한 획득하기
					// 녹음하고 있는지를 표시
					recorder = new MediaRecorder(); // 녹음위한 객체 준비
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 오디오
																			// 소스설정
					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					// 출력파일 형식 설정
					recorder.setOutputFile(filename); // 저장할 경로+파일명 지정
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // 엔코더
																					// 설정
					try {
						recorder.prepare(); // 녹음전 준비
					} catch (IllegalStateException e) {
					} catch (IOException e) {
					}

					recorder.start(); // 녹음 시작
					bRecord.setText("녹음 중지");
					pb.setVisibility(View.VISIBLE);
				} else { // 녹음 중지면
					recorder.stop(); // 녹음 중지
					recorder.release(); // 녹음 자원 해제
					bRecord.setText("녹음");
					pb.setVisibility(View.GONE);
				} // end if
			}
		});

		bPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 재생하기  재생 <-> 재생중지
				if ("재생".equals(bPlay.getText().toString())){ // 재생하기
					try {
						player = new MediaPlayer();
						player.setDataSource(filename); // 재생할 음악 파일명
						player.prepare(); // 재생 준비
						player.start(); // 재생 시작
						bPlay.setText("재생 중지");
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), 
								"재생오류", Toast.LENGTH_SHORT).show();
						return;
					} 
				} else { // 재생중지
					player.stop(); // 재생 멈춤
					player.release(); // 자원 해제
					bPlay.setText("재생");
				}
			}
		});
	} // end of onCreate
} // end of class
