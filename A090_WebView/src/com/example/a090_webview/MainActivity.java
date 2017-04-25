package com.example.a090_webview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final EditText et = (EditText) findViewById(R.id.editText1);
		Button b = (Button) findViewById(R.id.button1);
		final WebView wv = (WebView) findViewById(R.id.webView1);
		b.setOnClickListener(new OnClickListener() {
			long time;
			@Override
			public void onClick(View v) {
				// EditText의 입력한 주소를 가져와서 WebView로 연결
				// AndroidManifest.xml 권한 Internet
				String address = et.getText().toString();
				wv.loadUrl(address);
				// 안드로이드에서는 기본적으로 웹뷰로 사이트 이동하려하면
				// 기본 브라우저를 통해서 접속하도록 권장하고 있다.
				
				time = System.currentTimeMillis(); // 시작 시간 저장
		// 웹뷰 셋팅
				WebSettings setting = wv.getSettings();
				setting.setJavaScriptEnabled(true); // 자바 스크립트 사용 설정
				setting.setJavaScriptCanOpenWindowsAutomatically(true);
							// 자바스크립트가 window.open() 사용할 수 있도록 설정
				setting.setPluginState(WebSettings.PluginState.ON_DEMAND); // 플러그인 사용
				setting.setSupportZoom(true); // 줌 제스쳐
				setting.setBuiltInZoomControls(true); // 줌인 줌아웃 -> ctrl 버튼 사용여부
				wv.setWebViewClient(new WebViewClient(){ // 현재화면의 WebView에서 사이트 이동
					@Override
					public void onPageStarted(WebView view, String url, Bitmap favicon) {
						
					} // 웹페이지 로딩 시작할 때
					// 시간이 얼마나 걸리는 지 확인하기 위한 용도
					@Override
					public void onPageFinished(WebView view, String url) { 
						float ms = (System.currentTimeMillis() - time)/1000f;
						Toast.makeText(getApplicationContext(), 
								ms+"ms", Toast.LENGTH_SHORT).show();
					}
					@Override
					public void onReceivedError(WebView view, int errorCode, 
							String description, String failingUrl) { 
					} // 에러 발생시 
					@Override
					public void onScaleChanged(WebView view, 
							float oldScale, float newScale) {
					} // 화면 scale이 변경되었을 때
					@Override
					public boolean shouldOverrideUrlLoading(WebView view, String url) {
						return false;
					} // 새로운 url 불러올 때
				}); 
			}
		});
	} // end of onCreate
} // end of class
