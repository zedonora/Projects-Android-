package com.example.a001_helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
/**
 * 하나의 화면관리하는 객체가 Activity 이다.
 */
public class MainActivity extends Activity {
	/**
	 * onCreate : Activity 가 처음 화면에 나타날 때 호출되는 콜백메서드
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // 부모의 메서드를 호출
		setContentView(R.layout.activity_main); // xml과 java 파일을 연결해주는 명령문
	}

	
}
