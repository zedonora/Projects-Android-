package com.example.a114_myalbum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Select extends Activity {
	String filename;
	ImageView ivSelect;
	Bitmap bm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		Intent intent = getIntent();
		filename = intent.getStringExtra("filename");
		
		ivSelect = (ImageView) findViewById(R.id.ivSelect);
		bm = BitmapFactory.decodeFile(filename);
		ivSelect.setImageBitmap(bm);
		
		Button b2 = (Button) findViewById(R.id.bSelect1);
		Button b3 = (Button) findViewById(R.id.bSelect2);
		Button b4 = (Button) findViewById(R.id.bSelect3);
		b2.setOnClickListener(ol);
		b3.setOnClickListener(ol);
		b4.setOnClickListener(ol);
	} // end of onCreate
	OnClickListener ol = new OnClickListener() {
		@Override
		public void onClick(View v) { // 이미지 영상처리
			int num = Integer.valueOf(v.getTag().toString());
			File file = new File(filename);
			try {
				bm = Images.Media.getBitmap(getContentResolver(),Uri.fromFile(file));
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
//			bm = BitmapFactory.decodeFile(filename);
			
			go(num); // 영상처리
		}
	};
	public void go(int num) {
		// 영상처리
		int width = bm.getWidth();
		int height = bm.getHeight();
		Bitmap bmCopy = bm.copy(Bitmap.Config.ARGB_8888, true); // 이미지 복사
		
		switch (num) {
		case 1: // 처리1 - 이진화 : 중간값을 기준으로 흑,백 두가지 색만 표현
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int value = bmCopy.getPixel(j, i);
					if (value < 0xff808080) {
						bmCopy.setPixel(j, i, 0xff000000); // 검정
					} else {
						bmCopy.setPixel(j, i, 0xffffffff); // 흰색
					}
				}
			}
			break;
		case 2: // 처리2 - 흑백영상만들기
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int value = bmCopy.getPixel(j, i); // 0xffffffff
					// RGB값 추출하기 mask 기법
					int r = (value & 0x00ff0000) >> 16; // 0 ~ 255
					int g = (value & 0x0000ff00) >> 8;  // 0 ~ 255
					int b = (value & 0x000000ff);  		// 0 ~ 255
//					value = 0xff << 24 | r << 16 | r << 8 | r; // 빨강색을 기준으로 흑백처리
//					value = 0xff << 24 | g << 16 | g << 8 | g; // 녹색을 기준으로 흑백처리
//					int avr = (r+g+b)/3;
//					value = 0xff << 24 | avr << 16 | avr << 8 | avr; // 평균으로 흑백처리
					
					int y = (int)(r*0.2126 + g*0.7152 + b*0.0722);
					value = 0xff << 24 | y << 16 | y << 8 | y; // 밝기를 기준으로 흑백처리
					bmCopy.setPixel(j, i, value);
				}
			}
			break;
		case 3: // 처리3 - 반전영상
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int value = bmCopy.getPixel(j, i);
					int r = (value & 0x00ff0000) >> 16;
					int g = (value & 0x0000ff00) >> 8;
					int b = (value & 0x000000ff);
					r = 0xff - r;
					g = 0xff - g;
					b = 0xff - b;
					value = 0xff << 24 | r << 16 | g << 8 | b;
					bmCopy.setPixel(j, i, value);
				}
			}
			break;
		case 4: // 처리4 - 프레임
			for (int i = 0; i < height; i++) { // y
				for (int j = 0; j < width; j++) { // x
					int value = bmCopy.getPixel(j, i);
					int r = (value & 0x00ff0000) >> 16;
					int g = (value & 0x0000ff00) >> 8;
					int b = (value & 0x000000ff);
					
					// 중심좌표 height/2, width/2,   원의 반지름 height/2
					int cH = height/2;
					int cW = width/2;
					int cR = height/2;
					if ((i-cH)*(i-cH)+(j-cW)*(j-cW) < cR*cR) { // 원의 안쪽
						// 흑백 처리
						int y = (int)(r*0.2126 + g*0.7152 + b*0.0722);
						value = 0xff << 24 | y << 16 | y << 8 | y; // 밝기를 기준으로 흑백처리
					} else { // 원의 바깥쪽
						value = 0;
					}
					
					bmCopy.setPixel(j, i, value);
				}
			}
			break;
		}
		
		ivSelect.setImageBitmap(bmCopy);
	}
	@Override
	protected void onResume() {
		super.onResume();
		File file = new File(filename);
		if(file.exists()) {
			bm = BitmapFactory.decodeFile(filename);
			ivSelect.setImageBitmap(bm);
		}
	} 
} // end of class