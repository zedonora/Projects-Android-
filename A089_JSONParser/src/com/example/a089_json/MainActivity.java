
package com.example.a089_json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.button1);
		tv = (TextView) findViewById(R.id.textView1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goJSONParser();
			}
		});
	} // end of onCreate
	public void goJSONParser() {
//		JSON : JavaScript Object Notation
//				대부분 언어에서 사용가능, 개방형 포맷
//				Text로 되어 있어서, 사람, 기계 모두가 읽고 쓰기 편하다
//		AJAX 용으로 유용, xml 대체하기 위한 포맷
//		기본자료형 : 정수, 실수, 문자열 String"", boolean(true/false), null
//		배열Array : []대괄호로 묶음, 순서는 의미없음, 각요소를 ,로 구분
//					각요소는 기본자료형 or 배열 or 객체
//		객체Object: {}중괄호로 묶음, 순서는 의미없음, 각요소를 ,로 구분
//					각요소는 이름:값, 이름-"문자열", 값-기본자료형 or 배열 or 객체
		try {
			StringBuilder sb = new StringBuilder();
			// res/raw 폴더 xml 자원 가져오기
			String str = getResources().getString(R.string.mem); // xml에서 작성이 간단함
			JSONArray jarray = new JSONArray(str);
			for (int i = 0; i < jarray.length(); i++) {
				JSONObject jObject = jarray.getJSONObject(i);
				String name = jObject.getString("name");
				int age = jObject.getInt("age");
				String address = jObject.getString("address");
				sb.append("[이름:"+name+", 나이:"+age+",주소:"+address+"]\n");
			}
			tv.setText(sb.toString());
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), 
					"JSONException", Toast.LENGTH_SHORT).show();
		}
		
	}
} // end of class
