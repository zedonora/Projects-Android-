package com.example.a046_spinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 1. 다량의 데이터
		// 2. AdapterView 선언 (ListView, GridView, Gallery, Spinner)
		// 3. Adapter 생성 후 연결 (setAdapter())
		
		final String str[] = {"서울시","인천시","부산시","대구시","대전시","광주시","울산시","수원시"};
		
		Spinner spin = (Spinner) findViewById(R.id.spinner1);
		
		ArrayAdapter adapter = new ArrayAdapter(
								getApplicationContext(),
								R.layout.spin, // 접혀있을 때 layout
								str);
//		ArrayAdapter adapter = new ArrayAdapter(
//				getApplicationContext(),
//				android.R.layout.simple_spinner_item,
//				str);
		adapter.setDropDownViewResource(R.layout.dropdown);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// dropDown 됐을 때 layout
		
		spin.setAdapter(adapter);
		
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// 선택되었을 때 호출되는 콜백 메서드
				Toast.makeText(getApplicationContext(), 
						str[position]+"선택하였습니다.", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// 아무것도 선택되지 않았을 때 호출되는 콜백 메서드
			}
		});
	} // end of onCreate
} // end of class
