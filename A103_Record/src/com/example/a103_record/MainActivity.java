package com.example.a103_record;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	String path; // 녹음한 파일의 경로
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button bRecord = (Button) findViewById(R.id.button1);
		Button bPlay = (Button) findViewById(R.id.button2);
		
		bRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 녹음 App 으로 인텐트 넘겨서 녹음해오기
//				Intent intent = new Intent("android.provider.MediaStore.RECORD_SOUND");
				Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
//				startActivity(intent); // 결과를 받지않고, 화면전환만 하기
				startActivityForResult(intent, 1234); // 화면 전환 후 결과를 받을 수 있음
											// requestCode
			}
		});
		bPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(path == null) {
					Toast.makeText(getApplicationContext(), 
							"녹음된 파일이 없습니다. 녹음을 먼저 해주세요", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile((new File(path))), "audio/*");
				startActivity(intent);
			}
		});
	} // end of onCreate
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 화면 전환 후 되돌아 왔을 때 호출되는 콜백 메서드
		if (resultCode == RESULT_OK) { // 결과 값, 요청 값 확인
			Uri recordedAudio = data.getData();
			Cursor c = getContentResolver().query(recordedAudio, null, null, null, null);
			c.moveToNext(); // 읽을 최초의 위치로 커서 이동
			// 저장된 녹음 파일의 경로를 꺼내기
			path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
			// 외부 메모리에 저장신 권한 설정
			Toast.makeText(getApplicationContext(), "녹음성공", Toast.LENGTH_SHORT).show();
		}
	}
} // end of class
