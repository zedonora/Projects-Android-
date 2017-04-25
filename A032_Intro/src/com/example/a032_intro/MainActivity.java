package com.example.a032_intro;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 화면 전환
		// 1. 다음 넘어갈 화면(java, xml)을 만든다.
		// 2. 화면을 등록해준다.(AndroidManifext.xml)
		// 3. 화면을 전환한다. startActivity(intent)
	} // end of onCreate
} // end of class
