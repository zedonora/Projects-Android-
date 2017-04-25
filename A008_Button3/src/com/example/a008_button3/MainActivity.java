package com.example.a008_button3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView)findViewById(R.id.textView1);
	} // end of onCreate
	public void myClick(View v) { // xml의 버튼의 onClick 속성으로 지정한 메서드 명
		Button b = (Button)v;
		String str = (String) b.getTag();
		tv.setText(tv.getText()+str);
	}
} // end of class
