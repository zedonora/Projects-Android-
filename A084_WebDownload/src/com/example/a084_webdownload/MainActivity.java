package com.example.a084_webdownload;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpServerConnection;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Handler handler = new Handler();
	TextView tv;
	ProgressBar pb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pb = (ProgressBar)findViewById(R.id.progressBar1);
		Button b = (Button) findViewById(R.id.button1);
		tv = (TextView) findViewById(R.id.textView1);
		
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 웹에서 자료를 받아서 TextView 에 보여주기
				if(!isNetworkAvailable()) { // 인터넷 연결 불가시
					Toast.makeText(getApplicationContext(), 
							"네트워크 연결을 해주세요", Toast.LENGTH_SHORT).show();
					return;
				} 
				// 별도의 쓰레드에서 네트워크 작업을 하기 (3.0 이후 의무)
				downloadHTML("http://www.naver.com");
			}
		});
	} // end of onCreate
	/**
	 * 쓰레드로 네트워크 작업하기
	 */
	void downloadHTML(final String address) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() { // 오래걸리는 작업
				try {
					URL url = new URL(address);
					
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.connect(); // 연결
					InputStream is = con.getInputStream();
					byte b[] = new byte[1024];
					final StringBuffer sb = new StringBuffer();
					while(true) {
						int cnt = is.read(b);
						if (cnt == -1) {
							break;
						}
						sb.append(new String(b,0,cnt));
					}
					is.close();
					con.disconnect();
					handler.post(new Runnable() {
						@Override
						public void run() {
							pb.setVisibility(View.GONE);
							tv.setText(sb.toString());
						}
					});
				} catch (MalformedURLException e) {
					Toast.makeText(getApplicationContext(), 
							"MalformedURLException 발생 - 파싱에러", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					Toast.makeText(getApplicationContext(), 
							"IOException - Connect 에러", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		t.start(); // 쓰레드 시작
		pb.setVisibility(View.VISIBLE); // 뺑뺑이 프로그래스바 보여주기
	}
	/**
	 * 네트워크 접속 가능여부
	 */
	public boolean isNetworkAvailable() {
		boolean available = false;
		ConnectivityManager manager = (ConnectivityManager) 
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = manager.getActiveNetworkInfo();
		if(ni != null && ni.isAvailable()) {
			available = true;
		}
		return available;
	}
} // end of class
