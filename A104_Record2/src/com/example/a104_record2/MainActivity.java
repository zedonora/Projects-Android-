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
import android.widget.Toast;

public class MainActivity extends Activity {
	MediaRecorder recorder;//녹음 객체
	String fileName;//녹음, 재생할 파일명
	MediaPlayer player;//재생객체
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button bRecord = (Button)findViewById(R.id.button1);
		final Button bPlay = (Button)findViewById(R.id.button2);
		
		//절대경로+파일명준비하기
		fileName = Environment.getExternalStorageDirectory().getAbsolutePath()//절대경로를 알 수 없을 때, 이 코드를 사용하면 된다
					+"/test.3gp";//절대경로:	/storage/emulate/0
		
		bRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if("녹음".equals(bRecord.getText().toString())){
				//녹음하기
				//권한 획득하기
				//오디오 사용권한, 외부메모리 사용권한 획득하기(manifest.xml)			 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
																		//<uses-permission android:name="android.permission.RECORD_AUDIO"/>
				//녹음하고 있는지를 이미지 같은 것으로 표시하면 좋음
				recorder = new MediaRecorder();//녹음을 위한 객체 준비
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);//오디오 소스 설정
				recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				//출력파일 형식 설정
				recorder.setOutputFile(fileName);//저장할 경로+파일명 지정
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//엔코더 설정
				try {
					recorder.prepare();//녹음전 준비
				} catch (IllegalStateException e) {
				} catch (IOException e) {
				}
				recorder.start();//녹음시작
				bRecord.setText("녹음중지");
				}else{
					recorder.stop();//녹음중지
					recorder.release();//녹음 자원해제
					bRecord.setText("녹음");
				}//end of if문
			}
		});
		bPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//재생하기		재생<->재생중지
				if("재생".equals(bPlay.getText().toString())){//재생하기
					try {
						player = new MediaPlayer();//file재생할 때는 create하지 않고 객체생성하여 할 수 있다.
						player.setDataSource(fileName);
						player.prepare();//재생준비
						player.start();//재생시작
						bPlay.setText("재생중지");
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "재생오류", Toast.LENGTH_SHORT).show();
						return;
					}//재생할 음악 파일명
				}else{//재생중지
					player.stop();
					player.release();
					bPlay.setText("재생");
				}
			}
		});
	}//end of void onCreate
}//end of class MainActivity
