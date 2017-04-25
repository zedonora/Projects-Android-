package com.example.a109_mapintent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final EditText etLat = (EditText) findViewById(R.id.editText1);
		final EditText etLong = (EditText) findViewById(R.id.editText2);
		final EditText etZoom = (EditText) findViewById(R.id.editText3);
		Button b = (Button) findViewById(R.id.button1);
			
		// 지도 앱으로 Intent 화면 전환 - Permission이 전혀 필요없음
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				float lat = Float.valueOf(etLat.getText().toString());
				float lon = Float.valueOf(etLong.getText().toString());
				float zoom = Float.valueOf(etZoom.getText().toString());
				String str = String.format("geo:%f,$f,$f", lat, lon, zoom);
				Uri uri = Uri.parse(str);
//				Uri uri = Uri.parse("geo:"+lat+","+lon+"?z="+zoom);
				
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		
		
	} // end of onCreate
} // end of class
