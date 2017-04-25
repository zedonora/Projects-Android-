package com.example.a102_musicplay;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b =  (Button)findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//음악재생 - 재생가능한 App으로 Intent넘기기
				Intent intent = new Intent(Intent.ACTION_VIEW);
				Uri uri = Uri.parse("file:///storage/emulated/0/Samsung/Music/Over the Horizon.mp3");//내 핸드폰에 있는 음악파일 재생하기
				intent.setDataAndType(uri, "audio/mp3");
				startActivity(intent);
			}
		});
	}//end of void onCreate
}//end of class MainActivity
