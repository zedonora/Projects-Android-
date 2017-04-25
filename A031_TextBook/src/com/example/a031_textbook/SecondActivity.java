package com.example.a031_textbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		setTitle("Second 액티비티");
		
		Intent inIntent = getIntent();
		final int hapValue = inIntent.getIntExtra("Num1", 0)+inIntent.getIntExtra("Num2", 0);
		
		
		Button btnReturn = (Button) findViewById(R.id.button1);
		btnReturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent outIntent = new Intent(getApplicationContext(),MainActivity.class);
				outIntent.putExtra("Hap", hapValue);
				setResult(RESULT_OK,outIntent);
				finish();
			}
		});
	} // end of onCreate
} // end of class
