package com.example.a031_textbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("메인 액티비티");
		
		Button btnNewActivity = (Button) findViewById(R.id.button1);
		btnNewActivity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText edtNum1 = (EditText) findViewById(R.id.editText1);
				EditText edtNum2 = (EditText) findViewById(R.id.editText2);
				Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
				intent.putExtra("Num1", Integer.parseInt(edtNum1.getText().toString()));
				intent.putExtra("Num2", Integer.parseInt(edtNum2.getText().toString()));
				startActivityForResult(intent, 0);
			}
		});
		
	} // end of onCreate
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			int hap = data.getIntExtra("Hap", 0);
			Toast.makeText(getApplicationContext(), "합계 :" +hap, Toast.LENGTH_SHORT).show();
		}
	}
} // end of class
