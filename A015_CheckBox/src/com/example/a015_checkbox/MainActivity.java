package com.example.a015_checkbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// 버튼 클릭시 체크된 체크 박스 text를 TextView에 나타내기
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final CheckBox cb[] = new CheckBox[4];
		cb[0] = (CheckBox) findViewById(R.id.checkBox1);
		cb[1] = (CheckBox) findViewById(R.id.checkBox2);
		cb[2] = (CheckBox) findViewById(R.id.checkBox3);
		cb[3] = (CheckBox) findViewById(R.id.checkBox4);
		final TextView tv = (TextView) findViewById(R.id.textView2);
		Button b = (Button) findViewById(R.id.button1);
//		cb1.isChecked(); // 체크가 된 상태
//		cb1.getText().toString(); // 체크박스 문자열 읽어오기
		OnClickListener me = new OnClickListener() {
			@Override
			public void onClick(View v) {
				String saveName = "";
				for (int i = 0; i < cb.length; i++) {
					if(cb[i].isChecked()) {
						saveName += cb[i].getText().toString()+",";
					}
				}
				tv.setText(saveName);
			}
		};
		b.setOnClickListener(me);
		
		// 상태를 저장하는 위젯의 이벤트 처리
		OnCheckedChangeListener me1 = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(getApplicationContext(), 
						buttonView.getText().toString() + (isChecked?" 선택했음":" 해제했음")
						, Toast.LENGTH_SHORT).show();
			}
		};
		for (int i = 0; i < cb.length; i++) {
			cb[i].setOnCheckedChangeListener(me1);
		}
	} // end of onCreate
} // end of class
