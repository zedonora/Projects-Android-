package com.example.a045_tab;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// TabHost 얻어오기
		TabHost host = getTabHost();
//1
		// Widget 생성
		TabHost.TabSpec tabs;
		tabs = host.newTabSpec("tag1"); // 태그
		tabs.setIndicator("One"); // 라벨
		Intent intent1 = new Intent(getApplicationContext(),FirstActivity.class);
		tabs.setContent(intent1);
		
		host.addTab(tabs);
//2		
		tabs = host.newTabSpec("tag2"); // 태그
		tabs.setIndicator("Two"); // 라벨
		Intent intent2 = new Intent(getApplicationContext(),SecondActivity.class);
		tabs.setContent(intent2);
		
		host.addTab(tabs);
//3		
		tabs = host.newTabSpec("tag3"); // 태그
		tabs.setIndicator("Three"); // 라벨
		Intent intent3 = new Intent(getApplicationContext(),ThirdActivity.class);
		tabs.setContent(intent3);
		
		host.addTab(tabs);
	} // end of onCreate
} // end of class
