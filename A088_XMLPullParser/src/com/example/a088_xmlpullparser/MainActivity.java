package com.example.a088_xmlpullparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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
				goXMLPullParser();
			}
		});
	} // end of onCreate
	public void goXMLPullParser() {
		// XMLPullParser : SAX와 비슷한 이벤트 기반의 파서 (한줄씩 파싱),
		// SAX와는 다르게 특정 부분까지만 파싱가능하다.
		// 안드로이드에서 사용하기 위해 추가된 파서 (안드로이드에서 사용권장)
		// 장점 : 메모리소모가 적다, 원하는 부분까지만 파싱 가능
		// 단점 : 이전 Element 를 다시 읽기 위해서는, 처음부터 다시 파싱해야한다.
		
		try {
			StringBuilder sb = new StringBuilder();
			// res/raw 폴더의 xml 자원 가져오기
			InputStream is = getResources().openRawResource(R.raw.student);
			XmlPullParser xpp = XmlPullParserFactory.newInstance().newPullParser();
			xpp.setInput(new InputStreamReader(is,"utf-8"));
			while(xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType()==XmlPullParser.START_TAG) {
					String name = xpp.getName(); // 태그의 name 얻어오기
					if ("mem".equals(name)) {
						sb.append("id:"+xpp.getAttributeValue(0)+","); // 속성값 얻어오기
						sb.append("pw:"+xpp.getAttributeValue(1)+"\n"); // 속성값 얻어오기
					} else if ("name".equals(name)) {
						sb.append("abe"+xpp.getAttributeValue(0)+","); // 속성값
						sb.append("name:"+xpp.nextText()+"\n"); // 내용 얻어오기
					} else if ("address".equals(name)) {
						sb.append("address:"+xpp.nextText()+"\n"); // 내용 얻어오기
					} else if ("age".equals(name)) {
						sb.append("age:"+xpp.nextText()+"\n"); // 내용 얻어오기
					}
				}
				xpp.next(); // 다음라인 읽기
			} // end of while
			tv.setText(sb.toString());
		} catch (XmlPullParserException e) {
			Toast.makeText(getApplicationContext(), 
					"XmlPullParserException", Toast.LENGTH_SHORT).show();
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		}
		
	}
} // end of class
