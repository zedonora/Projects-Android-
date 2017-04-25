package com.example.a086_domparser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// res/raw폴더 : 오디오, 비디오 파일, 텍스트 파일
		// DOM Parser : Document Object Model
		// 		전체 구조를 메모리에 Tree 구조로 모두 올려놓고, 작업한다.
		//		장점 : 원하는 데이터를 쉽게 꺼낼 수 있다.
		// 		단점 : 메모리 소모가 크다, 속도가 느리다.
		Button b = (Button) findViewById(R.id.button1);
		tv = (TextView) findViewById(R.id.textView1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goDOMParser();
			}
		});
	} // end of onCreate
	public void goDOMParser(){
		// raw 에 있는 자원을 읽어오기
		try {
			InputStream is = getResources().openRawResource(R.raw.student);
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(is); // 데이터를 모두 메모리에 올림
			
			NodeList nlStudent = doc.getElementsByTagName("student"); // 노드 읽음
			NodeList nlName = doc.getElementsByTagName("name");
			NodeList nlAge = doc.getElementsByTagName("age");
			NodeList nlAddress = doc.getElementsByTagName("address");
			
			String result = "";
			for (int i = 0; i < nlStudent.getLength(); i++) { // 노드 길이 or 개수만큼 반복
				result += "\n이름: "+nlName.item(i).getFirstChild().getNodeValue();
				result += "\n나이: "+nlAge.item(i).getFirstChild().getNodeValue();
				result += "\n주소: "+nlAddress.item(i).getFirstChild().getNodeValue();
				result += "\n";
			}
			tv.setText(result);
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
	}
} // end of class
