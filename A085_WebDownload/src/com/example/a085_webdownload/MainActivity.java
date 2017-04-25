package com.example.a085_webdownload;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	ProgressBar pb;
	ImageView iv;
	Handler handler = new Handler(); // 화면 변경시 필요
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.button1);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		iv = (ImageView) findViewById(R.id.imageView1);
		
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				download(); // 이미지 다운로드 받아서 ImageView 에 띄우기
			}
		});
	} // end of onCreate
	/**
	 * 웹에서 Image 다운로드 받기
	 */
	public void download(){
		// AndroidManifest.xml Internet 권한 획득
		// Android 3.0부터 별도의 쓰레드에서 웹작업을 하도록 되어있음. -> Main UI에서 사용하려면 문제가 됨
		// 별도의 쓰레드에서 화면 변경을 위해 handler 등을 이용해야 함
		final String address = "https://s.pstatic.net/shopping.phinf/20170410_1/7afd4487-ee56-41f8-8a4c-6f704bcf1ecc.jpg";
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(address);
					InputStream is = url.openStream();
					final Bitmap bm = BitmapFactory.decodeStream(is);
					
					handler.post(new Runnable() {
						@Override
						public void run() {
							pb.setVisibility(View.GONE);
							iv.setImageBitmap(bm);
						}
					});
				} catch (MalformedURLException e) {
					Toast.makeText(getApplicationContext(), 
							"MalformedURLException - URL 파싱실패", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
				}
			}
		});
		pb.setVisibility(View.VISIBLE); 
		// 프로그래스바 띄우기 -1.프로그레스바 위젯 2.프로그레스바 다이얼로그  3.프로그레스바 테마
		t.start(); // 쓰레드 시작
	}
} // end of class
