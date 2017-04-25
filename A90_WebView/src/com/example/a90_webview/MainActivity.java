package com.example.a90_webview;

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
		
		Button b = (Button)findViewById(R.id.button1);
		final EditText et = (EditText)findViewById(R.id.editText1);
		final WebView wv = (WebView)findViewById(R.id.webView1);
		b.setOnClickListener(new OnClickListener() {
			long time;
			@Override
			public void onClick(View v) {
				//EditText의 입력한 주소를 가져와서 WebView로 연결
				//AndroidManifest.xml 권한 Internet
				String address = et.getText().toString();
				wv.loadUrl(address);//해당 주소로 사이트 연결
				//안드로이드에서는 기본적으로 웹뷰로 사이트 이동하려면 
				//os에서 가지고 있는 기본 브라우저를 통해서 접속하도록 권장하고 있다
				time = System.currentTimeMillis();//시작시간 지정
				WebSettings setting = wv.getSettings();
				setting.setJavaScriptEnabled(true);//자바스크립트 사용
				setting.setJavaScriptCanOpenWindowsAutomatically(true);//자바스크립트 window.open()사용할 수 있도록 설정
				setting.setPluginState(WebSettings.PluginState.ON_DEMAND);//플러그인 사용
				setting.setSupportZoom(true);//줌 제스처
				setting.setBuiltInZoomControls(true);//줌인 줌아웃 사용버튼
				wv.setWebViewClient(new WebViewClient(){
					@Override
					public void onPageFinished(WebView view, String url) {
						//super.onPageFinished(view, url);
						float ms = (System.currentTimeMillis()-time)/1000f;
						//웹페이지 로딩시간측정
						Toast.makeText(getApplicationContext(), ms+"ms", Toast.LENGTH_SHORT).show();
					}
					@Override
					public void onPageStarted(WebView view, String url, Bitmap favicon) {
						//super.onPageStarted(view, url, favicon);
					}//웹페이지 로딩 시작할 때
					@Override
					public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
						//super.onReceivedError(view, errorCode, description, failingUrl);
					}//에러 발생시
					@Override
					public void onScaleChanged(WebView view, float oldScale, float newScale) {
						//super.onScaleChanged(view, oldScale, newScale);
					}//화면 scale변경되었을 때
					@Override
					public boolean shouldOverrideUrlLoading(WebView view, String url) {
						//return super.shouldOverrideUrlLoading(view, url);
						return false;
					}//새로운 url불러올 때
				});//현재화면의 WebView에서 사이트 이동
			}
		});
	}//end of void onCreate
}//end of class MainActivity
