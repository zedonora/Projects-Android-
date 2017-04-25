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
		Button b = (Button)findViewById(R.id.button1);
		final EditText edLat = (EditText)findViewById(R.id.editText1);
		final EditText edLong = (EditText)findViewById(R.id.editText2);
		final EditText edZoom = (EditText)findViewById(R.id.editText3);
		
		//지도 앱으로 Intent화면 전환 - permission이 전혀 필요없음
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				float lat = Float.valueOf(edLat.getText().toString());
				float lon = Float.valueOf(edLong.getText().toString());
				float zoom = Float.valueOf(edZoom.getText().toString());
				String str = String.format("geo:%f,%f?z=%f", lat,lon,zoom);
				Uri uri = Uri.parse(str);
//				Uri uri = Uri.parse("geo>위도:"+lat+","+"경도:"+lon+",줌:"+zoom);
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
			}
		});
	}//end of void onCreate
}//end of class MainActivity


//가산동 위도와 경도
//37.2786764
//126.8810654