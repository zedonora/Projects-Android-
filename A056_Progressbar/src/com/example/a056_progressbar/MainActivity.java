package com.example.a056_progressbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	private ProgressBar pb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);		
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pb.setVisibility(View.VISIBLE);
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pb.setVisibility(View.INVISIBLE);
			}
		});
		
	} // end of onCreate
} // end of class
