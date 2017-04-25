package com.example.a039_listview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// ListView 띄우기
//		1. 다량의 데이터
//		2. AdapterView 를 선언 (ListView, GridView, Spinner, Gallery ... )
//		3. Adapter 객체 생성 후 연결 (데이터를 어디에 표현할지 설정 후 setAdapter())
		
//		String str[] = {"사과","배","수박","딸기","포도","오렌지","귤","키워","오이","바나나",
//						"사과","배","수박","딸기","포도","오렌지","귤","키워","오이","바나나",
//						"사과","배","수박","딸기","포도","오렌지","귤","키워","오이","바나나",
//						"사과","배","수박","딸기","포도","오렌지","귤","키워","오이","바나나",
//						"사과","배","수박","딸기","포도","오렌지","귤","키워","오이","바나나",
//						"사과","배","수박","딸기","포도","오렌지","귤","키워","오이","바나나"};
		
		ListView lv = (ListView) findViewById(R.id.listView1); // AdapterView 선언
		
//		ArrayAdapter adapter = new ArrayAdapter(
//							getApplicationContext(), 			 // 현재화면의 제어권자
//							android.R.layout.simple_list_item_1, // 표현할 레이아웃
//							str);								 // 다량의 데이터
		
		ArrayAdapter adapter = ArrayAdapter.createFromResource(
				getApplicationContext(), // 현재화면의 제어권자
				R.array.str,// xml 에서 다량의 데이터 가져옴
				// android.R.layout.simple_list_item_1); // 표현할 레이아웃 
				R.layout.customized); //  => android layout -> 커스텀마이즈
		lv.setAdapter(adapter); // 설정방법을 정의한 Adapter 를 등록
	} // end of onCreate
} // end of class
