package com.example.a029_sharedpreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class SPView extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spview);
		
		TextView tv = (TextView) findViewById(R.id.textView1);
		SharedPreferences sp = getSharedPreferences("set.dat", Context.MODE_PRIVATE);
		String str = "[설정값]";
		// 저장값 읽어오기
		str += "\n배경음악: "+sp.getBoolean("배경음악", false);
		str += "\n효과음: "+sp.getBoolean("효과음", false);
		str += "\n이메일: "+sp.getString("이메일", "");
		
		tv.setText(str);
	} // end of onCreate
} // end of class
