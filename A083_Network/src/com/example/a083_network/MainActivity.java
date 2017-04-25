package com.example.a083_network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b = (Button) findViewById(R.id.button1);
		final TextView tv = (TextView) findViewById(R.id.textView1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// AndroidManifest.xml 에 권한등록
//				android.permission.ACCESS_NETWORK_STATE
				ConnectivityManager manager = (ConnectivityManager) 
						getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni1 = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				NetworkInfo ni2 = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				
			// 네트워크에 연결되어있는지 여부 확인
				boolean available = false;
				NetworkInfo ni = manager.getActiveNetworkInfo(); // 연결된 네트워크 객체
				if (ni != null && ni.isAvailable()) {
					available = true;
				}
				
				tv.setText(ni1.toString()+
						"\n\n"+ni2.toString()+
						"\n\n"+ni.toString());
			}
		});
	} // end of onCreate
} // end of class
