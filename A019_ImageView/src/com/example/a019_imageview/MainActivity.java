package com.example.a019_imageview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	int index = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		final ImageView iv = (ImageView) findViewById(R.id.imageView1);
		final int img[] = {R.drawable.apple,R.drawable.banana,R.drawable.cherry,
				R.drawable.jadoo_plum,R.drawable.melon,R.drawable.remon};
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 다음
				index++;
				if (index>= img.length) {
					index = 0;
				}
				iv.setImageResource(img[index]);
			}
		});
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 이전
				index--;
				if(index<0) {
					index = img.length-1;
				}
				iv.setImageResource(img[index]);
			}
		});
	} // end of onCreate
} // end of class
