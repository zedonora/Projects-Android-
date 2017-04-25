package com.example.a077_internalstorage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final EditText et = (EditText) findViewById(R.id.editText1);
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = et.getText().toString(); // editText의 값을 읽어와서
				// 내부저장소의 파일로 저장하기
//			패키지명 : com.example.a077_internalstorage
// 내부메모리 저장 위치 : 내부메모리/data/data/패키지명/files/파일명
// 나의 앱에서만 파일 접근이 가능하다
// 앱을 삭제하면 파일이 지워진다.
				try {
					FileOutputStream fos =
							openFileOutput("internal.txt", Context.MODE_APPEND); // 추가하기
//															Context.MODE_APPEND // 덮어쓰기
					PrintWriter out = new PrintWriter(fos); // 보조스트림
					out.println(text); // 파일에 문자열 + 엔터 작성
					out.close(); // 파일 닫기
					fos.close();
					Toast.makeText(getApplicationContext(), 
							"파일쓰기 성공", Toast.LENGTH_SHORT).show();
					et.setText(""); // editText 초기화
					// 키보드 내리기
					InputMethodManager imm = (InputMethodManager) getSystemService
							(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							getCurrentFocus().getWindowToken(), 0);
					// 포커스를 갖고 있는 위젯의 키보드 내리기
				} catch (FileNotFoundException e) {
					Toast.makeText(getApplicationContext(), 
							"파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
				}
			}
		});
		
		// 파일 읽기
		Button b2 = (Button) findViewById(R.id.button2);
		final TextView tv = (TextView) findViewById(R.id.textView1);
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 파일을 읽어서
				try {
					FileInputStream fis =
							openFileInput("internal.txt"); // 파일 읽어오기
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					StringBuilder sb = new StringBuilder(); // 싱글쓰레드용
//					while(true) {
//						String str = br.readLine(); // 파일의 끝이면 null 리턴
//						if(str == null) {
//							break;
//						}
//						sb.append(str+"\n");
//					}
					String str = br.readLine(); // 파일의 끝이면 null 리턴
					while((str = br.readLine())!=null){
						sb.append(str+"\n");
					}
					
					tv.setText(sb); // TextView 에 보여주기
					br.close();
					fis.close();
				} catch (FileNotFoundException e) {
					Toast.makeText(getApplicationContext(), 
							"파일을 찾을 수 없습니다.",	Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					e.printStackTrace();
				} 
				
			}
		});
		
	} // end of onCreate
} // end of class
