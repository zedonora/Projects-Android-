package com.example.a030_intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Next extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 화면 그릴때 가장 먼저 호출되는 콜백 메서드
		super.onCreate(savedInstanceState);
		// 모든 Activity의 Lifecycle 메서드에는 부모를 호출하는 메서드가 있어야 한다.
		setContentView(R.layout.next); // xml과 연결
		
		// 버튼을 누르면 첫번째 화면으로 넘어가기
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 화면 전환
				// 1. 다음 넘어갈 화면을 준비한다.(java, xml)
				// 2. 다음화면 (Activity)을 등록한다.(AndroidManifest.xml)
				// 3. 화면전환 startActivity
				Intent intent = new Intent(getApplicationContext(), // 현재화면의 제어권자
						MainActivity.class); // 다음 넘어갈 화면
				startActivity(intent); // 화면전환
				finish(); // 현재 Activity 화면을 종료시키겠다.
			}
		});
	} // end of onCreate
} // end of class
