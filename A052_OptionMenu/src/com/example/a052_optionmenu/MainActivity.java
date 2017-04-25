package com.example.a052_optionmenu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	private RelativeLayout rl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		rl = (RelativeLayout) findViewById(R.id.relativeLayout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) { // 최초로 메뉴버튼을 눌렀을 때 호출
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu); // 레이아웃에서 메뉴 가져오기
		return true;
	}

	@Override // 옵션메뉴를 선택했을 때 호출 되는 콜백 메서드
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			rl.setBackgroundColor(Color.RED);
			return true;
		case R.id.menu1: // 로그인
			rl.setBackgroundColor(Color.GREEN);
			return true;
		case R.id.menu2: // 로그아웃
			rl.setBackgroundColor(Color.YELLOW);
			return true;
		case R.id.menu3: // 회원가입
			rl.setBackgroundColor(Color.BLUE);
			return true;
		default: // 그외
			return false;
		}
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return false;
	}
} // end of class
