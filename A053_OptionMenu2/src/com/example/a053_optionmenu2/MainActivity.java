package com.example.a053_optionmenu2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.textView1);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) { // 메뉴키를 눌렀을 때 호출되는 콜백메서드
		getMenuInflater().inflate(R.menu.main, menu);
		Log.d("optionMenu","onCreateOptionsMenu");
		return true;
	}
	boolean isLogin; // 로그인 여부
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) { // 메뉴가 뜰 때마다 호출되는 콜백메서드
		Log.d("optionMenu","onPrepareOptionsMenu");
		if (isLogin) { // 로그인 상태
//			MenuItem mi = menu.getItem(1);
			menu.getItem(1).setTitle("로그아웃");
			menu.getItem(2).setVisible(true); // 항목을 감춤
			menu.getItem(3).setEnabled(true); // 비활성화
		} else { // 로그아웃 상태 
			menu.getItem(1).setTitle("로그인");
			menu.getItem(2).setVisible(false); // 항목을 감춤
			menu.getItem(3).setEnabled(false); // 비활성화
		}
		return super.onPrepareOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { // 메뉴항목을 선택했을 때 호출
		Log.d("optionMenu","onOptionItemSelected");
		switch (item.getItemId()) {
		case R.id.menu_guest:
			tv.setText("손님입장");
			return true;
		case R.id.menu_loginout:
			tv.setText(isLogin?"로그인":"로그아웃");
			isLogin = !isLogin;
			return true;
		case R.id.menu_update:
			tv.setText("개인정보수정");
			return true;
		case R.id.menu_cart:
			tv.setText("장바구니");
			return true;
		default:
			return false;
		}
	}
} // end of class
