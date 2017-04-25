package com.example.a031_test_intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText et1 = (EditText) findViewById(R.id.editText1);
		final EditText et2 = (EditText) findViewById(R.id.editText2);
		Button b = (Button) findViewById(R.id.button1);
		
		b.setOnClickListener(new OnClickListener() {
			int num1;
			int num2;
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),Cal.class);
				String str1 = et1.getText().toString();
				String str2 = et2.getText().toString();
				num1 = Integer.valueOf(str1);
				num2 = Integer.valueOf(str2);
				intent.putExtra("num1", num1);
				intent.putExtra("num2", num2);
				startActivity(intent);
			}
		});
	} // end of onCreate
} // end of class
