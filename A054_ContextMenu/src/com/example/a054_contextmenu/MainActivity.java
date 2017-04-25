package com.example.a054_contextmenu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button b;
	int cnt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		b = (Button) findViewById(R.id.button1);
		
		// View 를 오랫동안 눌렀을 때 메뉴를 띄우기 ContextMenu
		registerForContextMenu(b);
	} // end of onCreate
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// 최초로 한번 오랫동안 눌렀을 때 호출되는 ContextMenu 작성
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.context_menu_button, menu); // xml의 메뉴를 등록
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// 위젯을 오랫동안 누르면 나오는 context 메뉴를 선택했을 때 호출되는 콜백메서드
		switch (item.getItemId()) {
		case R.id.color:
			b.setBackgroundColor(Color.RED);
			return true;
		case R.id.count:
			b.setText(cnt+++"");
			return true;
		default:
			return false;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
} // end of class
