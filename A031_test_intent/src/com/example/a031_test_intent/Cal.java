package com.example.a031_test_intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Cal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cal);
		TextView tv = (TextView) findViewById(R.id.textView1);
		Intent intent = getIntent();
		int num1 = intent.getIntExtra("num1",0);
		int num2 = intent.getIntExtra("num2",0);
		tv.setText("결과값:"+(num1+num2));
		
	} // end of onCreate
} // end of class
