package com.example.a042_listview3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Next extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.next);
		Intent intent = getIntent();
		Chat chat = (Chat) intent.getSerializableExtra("chat");
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		iv.setImageResource(chat.img);
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		tv1.setText(chat.name);
		TextView tv2 = (TextView) findViewById(R.id.textView2);
		tv2.setText(chat.state);
		
	} // end of onCreate
} // end of class
