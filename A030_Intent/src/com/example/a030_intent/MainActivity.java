package com.example.a030_intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); // xml과 연결
		Button b = (Button) findViewById(R.id.button1);
		
		// 화면전환
//		1. 다음 넘어갈 화면을 준비한다. (java[Activity상속받음], xml) 
//		2. 화면(Activity)를 등록한다. (AndroidManifest.xml)
//		3. 화면 전환한다. (startActivity()) -> intent를 보내준다.
		
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), // 현재화면의 제어권자
						Next.class); // 다음 넘어갈 화면의 class
				startActivity(intent); // 화면전환
				finish(); // 현재 Activity는 종료
			}
		});
	} // end of onCreate
} // end of class
