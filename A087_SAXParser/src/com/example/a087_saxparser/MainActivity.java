package com.example.a087_saxparser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

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
				goSAXParser();
			}
		});
	} // end of onCreate
	public void goSAXParser() {
//		SAX Parser : Simple API for XML
//		이벤트 기반의 파서로 문서가 들어오는 순서대로 한줄씩 순차적으로 파싱
//		장점 : 메모리 소모가 적다
//		단점 : 지나간 Element의 정보를 다시 확인할 수 없다. 다시 확인하기 위해서는 처음부터 읽어야 함.
		
		// res/raw에서 xml 읽어오기
		try {
			InputStream is = getResources().openRawResource(R.raw.student);
			XMLReader xReader = SAXParserFactory.newInstance()
											.newSAXParser().getXMLReader();
			MyEvent me = new MyEvent();
			xReader.setContentHandler(me); // 이벤트 등록
			xReader.parse(new InputSource(is));
		} catch (SAXException e) {
			Toast.makeText(getApplicationContext(), 
					"SAXException", Toast.LENGTH_SHORT).show();
		} catch (ParserConfigurationException e) {
			Toast.makeText(getApplicationContext(), 
					"ParserConfigureationException", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
		}
	} // end of goSAXParser()
	class MyEvent implements ContentHandler{
		StringBuilder sb = new StringBuilder(); // 읽은 자료 저장
		@Override
		public void setDocumentLocator(Locator locator) {
		}
		@Override
		public void startDocument() throws SAXException {
			// 문서의 처음 시작할 때
		}
		@Override
		public void endDocument() throws SAXException {
			// 문서의 끝이 왔을 때
			tv.setText(sb.toString());
			Toast.makeText(getApplicationContext(), 
					"SAX Parser 완료", Toast.LENGTH_SHORT).show();
		}
		@Override
		public void startPrefixMapping(String prefix, String uri) throws SAXException {
		}
		@Override
		public void endPrefixMapping(String prefix) throws SAXException {
		}
		@Override
		public void startElement(String uri, String localName, String qName, 
				Attributes atts) throws SAXException {
			if ("mem".equals(localName)) {
				sb.append("id:"+atts.getValue("id"));
				sb.append("pw:"+atts.getValue("pw"));
			} else if ("name".equals(localName)) {
				sb.append("abe:"+atts.getValue("abe"));
			}
		} // Element의 시작
		@Override
		public void endElement(String uri, String localName, String qName) 
				throws SAXException {
			// Element의 끝
		}
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			// text 노드가 읽혀졌을 때 호출되는 콜백 메서드, 해당 text가 인자로 넘어옴
			sb.append(new String(ch,start,length));
		}
		@Override
		public void ignorableWhitespace(char[] ch, int start, int length) 
				throws SAXException {
		}
		@Override
		public void processingInstruction(String target, String data) 
				throws SAXException {
		}
		@Override
		public void skippedEntity(String name) throws SAXException {
		}
	}
} // end of class
