package com.example.a014_quiz;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	int result ;
	Display d;
	Point p;
	Toast t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText et1 = (EditText) findViewById(R.id.editText1); // 1번째 입력창 -> 문자 입력 방지
		final EditText et2 = (EditText) findViewById(R.id.editText2); // 2번째 입력창 -> 문자 입력 방지
		
		Button bP = (Button) findViewById(R.id.button1); // +버튼
		Button bMi = (Button) findViewById(R.id.button2); // -버튼
		Button bMu = (Button) findViewById(R.id.button3); // *버튼 
		Button bD = (Button) findViewById(R.id.button4); // /버튼 -> 0으로 나누면 문제 발생
		
		d = getWindowManager().getDefaultDisplay(); // 화면 좌표 얻기
		p = new Point();
		d.getSize(p);
		
		final TextView tv = (TextView) findViewById(R.id.textView1); // 계산내용 출력
		
		class MyEvent implements OnClickListener{
			@Override
			public void onClick(View v) {
				Button b = (Button) v;
				String str = b.getText().toString();
				String strNum1 = et1.getText().toString();
				String strNum2 = et2.getText().toString();
				if("".equals(strNum1)||"".equals(strNum2)) {
//					Toast.makeText(getApplicationContext(), "숫자를 입력하세요", Toast.LENGTH_SHORT).show();
					t = Toast.makeText(getApplicationContext(), "숫자를 입력하세요", Toast.LENGTH_SHORT);
					t.setGravity(Gravity.TOP, 0, 130);
					t.show();
					return;
				}
				int num1 = Integer.valueOf(strNum1);
				int num2 = Integer.valueOf(strNum2);
				if("+".equals(str)){
					result = num1 + num2;
				}else if("-".equals(str)){
					result = num1 - num2;
				}else if("*".equals(str)){
					result = num1 * num2;
				}else if("/".equals(str)){
					if(num2==0) {
						tv.setText("0이 아닌 수를 나눠주세요");
					} else {
						result = num1 / num2;
					}
				}
				tv.setText("결과 :" + result);
				t = Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT); // 결과값
				t.setGravity(Gravity.CENTER, 0, 0);
				t.show();
			}
		};
		MyEvent me = new MyEvent();
		bP.setOnClickListener(me);
		bMi.setOnClickListener(me);
		bMu.setOnClickListener(me);
		bD.setOnClickListener(me);
//		OnClickListener me = new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Button b = (Button) v;
//				String str = b.getText().toString();
//				String strNum1 = et1.getText().toString();
//				String strNum2 = et2.getText().toString();
//				if("".equals(strNum1)||"".equals(strNum2)) {
////					Toast.makeText(getApplicationContext(), "숫자를 입력하세요", Toast.LENGTH_SHORT).show();
//					t = Toast.makeText(getApplicationContext(), "숫자를 입력하세요", Toast.LENGTH_SHORT);
//					t.setGravity(Gravity.TOP, 0, 130);
//					t.show();
//					return;
//				}
//				int num1 = Integer.valueOf(strNum1);
//				int num2 = Integer.valueOf(strNum2);
//				if("+".equals(str)){
//					result = num1 + num2;
//				}else if("-".equals(str)){
//					result = num1 - num2;
//				}else if("*".equals(str)){
//					result = num1 * num2;
//				}else if("/".equals(str)){
//					if(num2==0) {
//						tv.setText("0이 아닌 수를 나눠주세요");
//					} else {
//						result = num1 / num2;
//					}
//				}
//				tv.setText("" + result);
//				t = Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT); // 결과값
//				t.setGravity(Gravity.CENTER, 0, 0);
//				t.show();
//			}
//		}
	} // end of onCreate
} // end of class
