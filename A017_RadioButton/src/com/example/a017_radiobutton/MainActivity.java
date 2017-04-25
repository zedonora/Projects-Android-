package com.example.a017_radiobutton;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	int preID = -1;
	Toast t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		final TextView tv = (TextView) findViewById(R.id.textView2);
		Button b = (Button) findViewById(R.id.button1);
		
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = rg.getCheckedRadioButtonId();
				if (id == -1) {
					tv.setText("골라주세요");
					return;
				}
				RadioButton rb = (RadioButton)findViewById(id);
				tv.setText(rb.getText()+"을 선택함");
			}
		});
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				String str = "";
				if(preID==-1) {
					preID = checkedId;
				} else {
					RadioButton rb = (RadioButton)findViewById(preID);
					str = rb.getText().toString()+"해제됨\n";
					preID = checkedId;
				}
				RadioButton rb1 = (RadioButton)findViewById(checkedId);
				str += rb1.getText().toString();
				t = (Toast) Toast.makeText(getApplicationContext(),
						str+"선택됨", Toast.LENGTH_LONG);
				t.show();
//				t1.makeText(getApplicationContext(),
//						rb1.getText().toString()+"선택됨", Toast.LENGTH_LONG).show();
			}
		});
	} // end of onCreate
	@Override
	protected void onPause() {
		t.cancel();
		super.onPause();
	}
} // end of class
