package com.example.a102_musicplay;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				Uri uri = Uri.parse("file:///storage/emulated/0/Music/감미로운 음악/owen.mp3");
				intent.setDataAndType(uri, "audio/*");
				startActivity(intent);
			}
		});
	} // end of onCreate
} // end of class
