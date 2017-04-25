package com.example.a005_button;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final TextView tv = (TextView) findViewById(R.id.textView1);
		Button b1 = (Button)findViewById(R.id.button1);
		Button b2 = (Button)findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		// 버튼 클릭 이벤트 달기
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 버튼 클릭시 호출 되는 콜백메서드
				tv.setText("클릭했습니다.");
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tv.setText("");
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			int count=0;
			@Override
			public void onClick(View v) {
				tv.setText(""+count++); // 인자값을 문자열로 작성해야 함
//				tv.setText(R.string.hello_world/*int형*/);
			}
		});
	} // end of onCreate

} // end of class
