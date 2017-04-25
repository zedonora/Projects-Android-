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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String path;//녹음한 파일의 경로
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bRecord = (Button)findViewById(R.id.button1);
		Button bPlay = (Button)findViewById(R.id.button2);
		tv = (TextView)findViewById(R.id.textView1);
		bRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//녹음 App으로 인텐트 넘겨서 녹음해오기
//				Intent intent = new Intent("android.provider.MediaStore.RECORD_SOUND");
				Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);//위랑 똑같은 결과값을 가지는 코드
//				startActivity(intent);//결과를 받지 않고, 화면 전환만하기
				startActivityForResult(intent, 1234);//화면전환 후 결과를 받을 수 있음
											//requestCode:1234
			}
		});
		bPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(path==null){
					Toast.makeText(getApplicationContext(), "녹음된 파일이 없습니다.녹음을 먼저 해 주세요", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(path)), "audio/*");
				startActivity(intent);
			}
		});
	}//end of void onCreate
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		//화면전환 후 되돌아 왔을 때 호출되는 콜백메서드
		if(resultCode==RESULT_OK && requestCode==1234){//결과 값,요청값 확인
			Uri recordAudio =data.getData();
			Cursor c  = getContentResolver().query(recordAudio,null,null,null,null);
			c.moveToNext();//읽을 최초의 위치로 커서 이동
			//저장된 녹음파일의 경로를 꺼내기
			path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
			//외부메모리 저장시 권한 설정:    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>	manifest에서 권한주기
			Toast.makeText(getApplicationContext(), "녹음 성공", Toast.LENGTH_SHORT).show();
			tv.append(path);
		}
	}
}//end of class MainActivity
