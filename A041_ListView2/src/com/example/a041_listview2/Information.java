package com.example.a041_listview2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Information extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);
		
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		TextView tv2 = (TextView) findViewById(R.id.textView2);
		
		Intent intent = getIntent();
		Fruit fruit = (Fruit)intent.getSerializableExtra("fruit");
		
		iv.setImageResource(fruit.img);
		tv1.setText(fruit.name);
		tv2.setText(fruit.info);
	} // end of onCreate
} // end of class
