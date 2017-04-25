package com.example.a034_test;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText edName = (EditText) findViewById(R.id.editText1);
		final EditText edAge = (EditText) findViewById(R.id.editText2);
		final EditText edAddress = (EditText) findViewById(R.id.editText3);
		final EditText edSex = (EditText) findViewById(R.id.editText4);
		Button b = (Button) findViewById(R.id.button1);
		
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 값을 가져오기
				String name = edName.getText().toString();
				String strAge = edAge.getText().toString();
				String address = edAddress.getText().toString();
				String strSex = edSex.getText().toString();
				
				if("".equals(name)||"".equals(strAge)||
						"".equals(address)||"".equals(strSex)) {
					Toast.makeText(getApplicationContext()
							, "정보를 입력하세요", Toast.LENGTH_SHORT).show();
					return;
				}
				
				int age = Integer.valueOf(strAge);
				boolean sex = "남자".equals(strSex);
				
//				Intent intent = new Intent(getApplicationContext(),Info.class);
//				intent.putExtra("name", name);
//				intent.putExtra("age", age);
//				intent.putExtra("address", address);
//				intent.putExtra("sex", sex);
//				startActivity(intent);
				Intent intent = new Intent(getApplicationContext(),Info.class);
				Man man = new Man(name, age, address, sex);
				intent.putExtra("man", man);
				startActivity(intent);
			}
		});
		
	} // end of onCreate
} // end of class
class Man implements Serializable{
	String name="";
	int age;
	String address="";
	boolean sex;
	public Man() {
	}
	public Man(String name, int age, String address, boolean sex) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.sex = sex;
	}
}