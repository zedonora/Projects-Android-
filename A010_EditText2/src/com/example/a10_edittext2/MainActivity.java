package com.example.a10_edittext2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final EditText etID = (EditText) findViewById(R.id.editText1);
		final EditText etPW = (EditText) findViewById(R.id.editText2);
		final TextView tv = (TextView) findViewById(R.id.textView1);
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String strID = etID.getText().toString();
				String strPW = etPW.getText().toString();
				String result = "";
				if(!"영관".equals(strID)) { // id를 가져와서 "영관" 과 같은지
					result = "ID가 틀렸습니다. 다시 확인해 주세요";
				} else if (!"멋짐".equals(strPW)) { // pw를 가져와서 "멋짐" 과 같은지
					result = "PW가 틀렸습니다. 다시 확인해 주세요";
				} else {
					result = "로그인 성공";
				}
				tv.setText(result); // textView에 로그인 결과 출력
			}
		});
	} // end of onCreate
} // end of class
