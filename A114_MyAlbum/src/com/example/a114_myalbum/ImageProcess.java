package com.example.a114_myalbum;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageProcess extends Activity {
	Bitmap bm;
	String filename; // 파일명
	private ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView() 메서드 이전에 선언해야만 한다
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 제목줄 없애기
//		<AndroidManifest.xml> 에서 테마 바꾸는 효과
		
		setContentView(R.layout.image_process);
//		<AndroidManifest.xml>
//		android:screenOrientation="landscape"
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 가로모드
		
		iv = (ImageView)findViewById(R.id.imageView1);
		Intent intent = getIntent();
		// 파일에 저장하기 위해서 메모리 사용 권한 설정 
		filename= intent.getStringExtra("fullpath");
		if (!new File(filename).exists()) {
			Toast.makeText(getApplicationContext(), "존재하지 않는 파일입니다", Toast.LENGTH_SHORT).show();
			int _id = intent.getIntExtra("_id", -1);
			Intent intent2 = new Intent();
			intent2.putExtra("delete_id", _id);
			setResult(5, intent2);
			finish();
		}
		iv.setImageBitmap(BitmapFactory.decodeFile(filename));
		
		Button b2 = (Button)findViewById(R.id.button2);
		Button b3 = (Button)findViewById(R.id.button3);
		Button b4 = (Button)findViewById(R.id.button4);
		Button b5 = (Button)findViewById(R.id.button5);
		b2.setOnClickListener(ol);
		b3.setOnClickListener(ol);
		b4.setOnClickListener(ol);
		b5.setOnClickListener(ol);
	} // end of onCreate
	OnClickListener ol = new OnClickListener() {
		public void onClick(View v) { // 이미지 영상처리 
			int num = Integer.valueOf(v.getTag().toString());
			go(num); // 영상처리
		}
	};
	public void go(int num) {
		// 영상처리
		bm = BitmapFactory.decodeFile(filename);
		int width = bm.getWidth();
		int height = bm.getHeight();
		Bitmap bmCopy = bm.copy(Bitmap.Config.ARGB_8888, true); // 이미지 복사
		
		switch (num) {
		case 1: // 처리1 - 이진화 : 중간값을 기준으로 흑, 백 두가지 색만 표현
			// 너비 * 높이의 배열 선언
            int[] values = new int[width*height];
            // 픽셀값들 한 번에 배열에 받아오기
            //             저장할 배열, 오프셋, 한 행의 크기(row=width), 시작x, 시작y, 너비, 높이
            bmCopy.getPixels(values, 0, bmCopy.getWidth(), 0, 0, bmCopy.getWidth(), bmCopy.getHeight());
            // 일차원 배열로 바꿔도 됨!
            for (int i = 0; i < height; i++) {      //y
               int row = i*width;
               for (int j = 0; j < width; j++) {   //x
                  // 지금 접근할 위치 (지나친 열들(row = 지금 행*행의 크기) + 지금 행의 열)
                  int col = row+j;
                  int value = values[col];   // 0xffffffff
                  int r = (value & 0x00ff0000) >> 16; // 0 ~ 255
                  int g = (value & 0x0000ff00) >> 8; // 0 ~ 255
                  int b = (value & 0x000000ff); // 0 ~ 255
                  // 값 반전시키는 코드
                  r = 0xff - r;
                  g = 0xff - g;
                  b = 0xff - b;
                  value = 0xff << 24 | r << 16 | g << 8 | b;
                  // 배열에 저장
                  values[col] = value;
               }
            }
            // 픽셀값들 한 번에 비트맵에 설정!
            //             저장할 배열, 오프셋, 한 행의 크기(row=width), 시작x, 시작y, 너비, 높이
            bmCopy.setPixels(values, 0, bmCopy.getWidth(), 0, 0, bmCopy.getWidth(), bmCopy.getHeight());
			break;
		case 2: // 처리2 - r제거
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int value = bmCopy.getPixel(j, i); // 0xffffffff
					int g = (value & 0x0000ff00) >> 8;  // 0 ~ 255
					int b = (value & 0x000000ff);		// 0 ~ 255
					value = 0xff << 24 | g << 8 | b; // 밝기를 기준으로 흑백처리
					bmCopy.setPixel(j, i, value);
				}
			}
			break;
		case 3 : // 처리 3 - g제거
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int value = bmCopy.getPixel(j, i);
					int r = (value & 0x00ff0000) >> 16; // 0 ~ 255
					int b = (value & 0x000000ff);		// 0 ~ 255
					value = 0xff << 24 | r << 16 | b;
					bmCopy.setPixel(j, i, value);
				}
			}
			break;
		case 4 : // 처리4 - b제거
			for (int i = 0; i < height; i++) { // y
				for (int j = 0; j < width; j++) { // x
					int value = bmCopy.getPixel(j, i);
					int r = (value & 0x00ff0000) >> 16; // 0 ~ 255
					int g = (value & 0x0000ff00) >> 8;  // 0 ~ 255
					value = 0xff << 24 | r << 16 | g << 8;
					bmCopy.setPixel(j, i, value);
				}
			}
			break;
		} // end of switch
		iv.setImageBitmap(bmCopy);
	}
} // end of class





















