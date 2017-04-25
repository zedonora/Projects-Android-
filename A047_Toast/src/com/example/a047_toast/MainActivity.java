package com.example.a047_toast;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Toast t;
	LayoutInflater lif;
	int num = 20;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (t!=null) {
					t.cancel();
				}
				t = Toast.makeText(getApplicationContext(), 
						"긴토스트", Toast.LENGTH_LONG);
				t.show();
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(t!=null) {
					t.cancel();
				}
				t = new Toast(getApplicationContext());
				t.setDuration(Toast.LENGTH_SHORT);
				// xml 을 view 객체로 리턴 받아서 setView 하기
				lif = (LayoutInflater) getApplicationContext().getSystemService
						(Context.LAYOUT_INFLATER_SERVICE);
				View view = lif.inflate(R.layout.toast_view, null);
				t.setView(view);
				Random ran = new Random();
				Display d = getWindowManager().getDefaultDisplay();
				Point p = new Point();
				d.getSize(p);
				int x = ran.nextInt(p.x-200);
				int y = ran.nextInt(p.y-100);
				t.setGravity(Gravity.LEFT|Gravity.TOP, x, y);
				
				final TextView tv = (TextView) view.findViewById(R.id.textView2);
				Button b = (Button) view.findViewById(R.id.button1);
				b.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						tv.setText("나이는 "+num++  +"살이다.");
						setTitle(num+"살");
					}
				});
				t.show();
			}
			
		});
	} // end of onCreate
	@Override
	protected void onPause() {
		super.onPause();
		if(t != null){ // 토스트 
			t.cancel();
		}
	}
} // end of class
