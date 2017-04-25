package com.example.a020_imageview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		// 권한을 설정해줘야 한다. AndroidManifest.xml
		// 스마트폰의 이미지 읽어오기
		Bitmap bm = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/KakaoTalk/1488068165555.jpg");
		
		iv.setImageBitmap(bm); // 읽어온 이미지 파일 출력
	} // end of onCreate	
} // end of class
