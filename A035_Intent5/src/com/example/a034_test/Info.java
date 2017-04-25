package com.example.a034_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Info extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		TextView tv = (TextView) findViewById(R.id.textView1);
		// 보내 온 값을 꺼내기
//		Intent intent = getIntent();
//		String name = intent.getStringExtra("name");
//		int age = intent.getIntExtra("age",0);
//		String address = intent.getStringExtra("address");
//		boolean sex = intent.getBooleanExtra("sex",false);
//		tv.setText("이름:"+name
//				+"\n 나이: "+age
//				+"\n 주소: "+address
//				+"\n 성별: "+(sex?"남":"여"));
//		tv.setText("이름:"+name
//				+"\n 나이: "+age
//				+"\n 주소: "+address
//				+"\n 성별: "+(sex?"남":"여"));
		Intent intent = getIntent();
		Man man = (Man) intent.getSerializableExtra("man");
		tv.setText("이름:"+man.name
				+"\n 나이: "+man.age
				+"\n 주소: "+man.address
				+"\n 성별: "+(man.sex?"남자":"여자"));
	} // end of onCreate
} // end of class
