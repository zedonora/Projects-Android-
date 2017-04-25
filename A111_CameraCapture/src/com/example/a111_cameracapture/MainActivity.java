package com.example.a111_cameracapture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	String filename; // 파일명
	ImageView iv;
	Bitmap bm = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// **setContentView() 메서드 이전에 선언해야만 한다.
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 제목줄 없애기
//		<AndroidManifest.xml> 에서 테마 바꾸는 효과
		
		setContentView(R.layout.activity_main);
//		<AndroidManifest.xml>
//		android:screenOrientation="landscape"
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 가로모드
		
		Button bCapture = (Button) findViewById(R.id.button1);
		iv = (ImageView) findViewById(R.id.imageView1);
		
		// 파일에 저장하기 위해서 메모리 사용 권한 설정
		filename = Environment.getExternalStorageDirectory().getAbsolutePath()
				+"/image.jpg"; // 절대경로 : /storage/emulated/0 /mnt/sdcard/
		
		bCapture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 캡쳐
				// 카메라에게 Intent를 날려 사진찍도록 함 - 찍을 사진 파일명을 넘겨주기
				Intent intent = new Intent
						(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				// 찍을 사진을 저장할 파일명을 담아서 보내기
				intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, // 키
						Uri.fromFile(new File(filename)));				  // 값
				startActivityForResult(intent, 123); // requestCode
			}
		});
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		Button b5 = (Button) findViewById(R.id.button5);
		b2.setOnClickListener(ol);
		b3.setOnClickListener(ol);
		b4.setOnClickListener(ol);
		b5.setOnClickListener(ol);
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
		
		iv.setImageBitmap(bmCopy);
	}
	@Override
	protected void onResume() {
		super.onResume();
		File file = new File(filename);
		if(file.exists()) {
			bm = BitmapFactory.decodeFile(filename);
			iv.setImageBitmap(bm);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// startActivityForResult로 인텐트 날린 후 되돌아 왔을 때 호출되는 콜백 메서드
		if(requestCode == 123 && resultCode == Activity.RESULT_OK) {
//			Bitmap bm = BitmapFactory.decodeFile(filename); // 한줄로 작성도 가능
			File file = new File(filename);
			try {
				bm = Images.Media.getBitmap(getContentResolver(), 
						Uri.fromFile(file));
				iv.setImageBitmap(bm);
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		}
	} // end of onActivityResult
} // end of class
