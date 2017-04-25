package com.example.a031_intent2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Apple extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apple);
		TextView tv = (TextView) findViewById(R.id.textView1);
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		// 실어온 데이터를 확인하기
		Intent intent = getIntent();
		String name = intent.getStringExtra("과일이름");
		
		if(name == null) {
			Toast.makeText(getApplicationContext(), "과일이름이 담겨있지 않아요", 
					Toast.LENGTH_SHORT).show();
			finish(); // 현재 화면을 제거
		} 
		int img = -1;
		if(name.equals("사과")) {
//			텍스트뷰, 이미지뷰에 값을 넣어주면 된다.
			img = R.drawable.apple;
		} else if(name.equals("배")) {
//			텍스트뷰, 이미지뷰에 값을 넣어주면 된다.
			img = R.drawable.pear;
		} else if(name.equals("수박")) {
//			텍스트뷰, 이미지뷰에 값을 넣어주면 된다.
			img = R.drawable.watermelon;
		} else if(name.equals("귤")) {
//			텍스트뷰, 이미지뷰에 값을 넣어주면 된다.
			img = R.drawable.orange;
		}
		tv.setText(name);
		if(img!=-1) {
			iv.setImageResource(img);
		}
	} // end of onCreate
} // end of class
