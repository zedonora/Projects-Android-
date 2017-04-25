package com.example.a007_button2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
//	static String result = "";
	public static TextView tv; // 전역변수
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.TextView1);
		Button b1 = (Button) findViewById(R.id.Button1);
		Button b2 = (Button) findViewById(R.id.Button2);
		Button b3 = (Button) findViewById(R.id.Button3);
		
//		b1.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
////				result += ""+1;
////				tv.setText(result);
//				tv.setText(tv.getText().toString()+1);
//			}
//		});
//		b2.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
////				result += ""+2;
////				tv.setText(result);
//				tv.setText(tv.getText().toString()+2);
//			}
//		});
//		b3.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
////				result += ""+3;
////				tv.setText(result);
//				tv.setText(tv.getText().toString()+3);
//			}
//		});
		// 지역변수로 이벤트 처리
//		class MyEvent implements OnClickListener{
//			@Override
//			public void onClick(View v) {
//				Button b = (Button)v;
//				String str = b.getText().toString();
//				tv.setText(tv.getText().toString()+str);	
//			}
//		}
		
//		MyEvent me = new MyEvent();
		// this : 내 객체 자신에서 이벤트를 처리하겠다.
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		
	} // end of onCreate
	// 전역변수 영역에서 이벤트 처리 - 지역변수에 접근 불가
//	class MyEvent implements OnClickListener{
//		@Override
//		public void onClick(View v) {
//			Button b = (Button)v;
//			String str = b.getText().toString();
//			tv.setText(tv.getText().toString()+str);	
//		}
//	}
	
	// 나의 객체(this) 에서 이벤트를 처리 - 지역변수는 접근 불가, 전역변수, static은 접근 가능
	@Override
	public void onClick(View v) {
		Button b = (Button) v;
		String str = b.getText().toString();
		tv.setText(tv.getText().toString()+str);
	}
} // end of class

// 클래스 밖에 (외부 클래스) 작성 - static 전역변수만 접근가능
//class MyEvent implements OnClickListener{
//	@Override
//	public void onClick(View v) {
//		Button b = (Button)v;
//		String str = b.getText().toString();
//		MainActivity.tv.setText(MainActivity.tv.getText().toString()+str);	
//	}
//}