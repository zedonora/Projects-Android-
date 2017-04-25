package com.example.a078_externalstorage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	File path;
	File f;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
// 외부저장소 사용하기
//		1. 외부저장소의 유무
//		2. 파일 읽기/쓰기 권한 얻어오기
//		3. 경로 지정 후 파일 사용
		
		final TextView tv = (TextView) findViewById(R.id.textView1);
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String result = "";
				// 외부저장소 상태 조회
				if(Environment.MEDIA_MOUNTED.equals
						(Environment.getExternalStorageState())) { // 읽기/쓰기 가능모드
					result = "외부저장소 읽기/쓰기 가능";
				} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals
						(Environment.getExternalStorageState())) { // 읽기 전용 모드
					result = "외부저장소 읽기 전용";
				} else {
					result = "읽기 쓰기 불가능";
//					finish(); // 앱 종료
				}
				tv.setText(result);
			}
		});

		final EditText et = (EditText) findViewById(R.id.editText1);
		Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = et.getText().toString();
				
//				File path = Environment.getExternalStoragePublicDirectory
//						(Environment.DIRECTORY_PICTURES); // 공용폴더
				String ss = Environment.getExternalStorageDirectory().getAbsolutePath()
						+"/a"; // 원하는 폴더 작성
				path = new File(ss);
				// 폴더가 실제로 존재하지 않는 경우 폴더 생성하기
				if(!path.exists()) { // 폴더가 존재하지 않으면
					path.mkdirs(); // 존재하지 않는 폴더를 모두 생성해줌
				}
				
				f = new File(path, "external.txt");
				try {
					FileWriter writer = new FileWriter(f,true); // true: 추가, false: 덮어쓰기
					PrintWriter out = new PrintWriter(writer);
					out.println(text);
					out.close();
					writer.close();
					Toast.makeText(getApplicationContext(), 
							"외부 저장소 쓰기 성공\n"
							+"파일경로: "+f.toString(),
							Toast.LENGTH_SHORT).show();
					et.setText(""); // EditText 초기화
					
					// 키보드 내리기
					InputMethodManager imm = (InputMethodManager) getSystemService
							(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							getCurrentFocus().getWindowToken(), 0); // 키보드 내리기
				} catch (IOException e) {
				}
			}
		});
		Button b3 = (Button) findViewById(R.id.button3);
		final TextView tv2 = (TextView) findViewById(R.id.textView2);
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 외부저장소 파일 읽기
//				path = Environment.getExternalStorageDirectory().getAbsoluteFile();
//				f = new File(path, "external.txt");
				String ss = Environment.getExternalStorageDirectory().getAbsolutePath()
						+"/a"; // 원하는 폴더 작성
				path = new File(ss);
				// 폴더가 실제로 존재하지 않는 경우 폴더 생성하기
				if(!path.exists()) { // 폴더가 존재하지 않으면
					path.mkdirs(); // 존재하지 않는 폴더를 모두 생성해줌
				}
				try {
					BufferedReader br = new BufferedReader(new FileReader(f));
					StringBuilder sb = new StringBuilder();
					while(true) {
						String str = br.readLine();
						if(str == null) {
							break;
						}
						sb.append(str+"\n");
					}
					tv2.setText(sb.toString());
					br.close();
				} catch (FileNotFoundException e) {
					Toast.makeText(getApplicationContext(), 
							"파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
				}
			}
		});
	} // end of onCreate
} // end of class
