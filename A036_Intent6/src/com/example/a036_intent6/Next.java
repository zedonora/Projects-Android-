package com.example.a036_intent6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Next extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.next);
		
		// 두번째 화면
		Intent intent = getIntent();
		String str = intent.getStringExtra("name");
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText("두번째 화면, 전달받은 이름: "+str);
		
		// 빽(뒤로가기)로 되돌아 갔을 때 처리할 값을 저장
		Intent intent2 = new Intent();
		intent2.putExtra("key", "호랑이");
		setResult(Activity.RESULT_OK,intent2); // resultCode
		
	} // end of onCreate
} // end of class
