package com.example.a042_gridview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Show extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show);
		
		Intent intent = getIntent();
		Nation nation = (Nation) intent.getSerializableExtra("nation");
		
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		TextView tv = (TextView) findViewById(R.id.textView1);
		
		iv.setImageResource(nation.img);
		tv.setText(nation.name);
	} // end of onCreate
} // end of class