package com.example.a055_popupmenu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final TextView tv = (TextView) findViewById(R.id.textView1);
		registerForContextMenu(tv); // 컨텍스트 메뉴 등록(롱클릭 했을 때 나옴)
		
		final Button b = (Button) findViewById(R.id.button1);
		b.setOnTouchListener(new OnTouchListener() {
			int colors[] = {Color.RED,Color.GREEN,Color.BLUE};
			int index;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_DOWN!=event.getAction()) {
					return false;
				}
				// 팝업 메뉴 띄우기
				// 1. 팝업메뉴 객체 생성
				// 2. xml의 레이아웃을연결, 메뉴 띄우기
				// 3. 선택메뉴에 대한 이벤트 처리
				PopupMenu popupMenu = new PopupMenu(getApplicationContext(), 
							v); // 닻 : 팝업메뉴를 띄울 위치
				MenuInflater mi = popupMenu.getMenuInflater();
				mi.inflate(R.menu.pop, popupMenu.getMenu());
				
				popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
						case R.id.menu_color:
							index++;
							if(index==colors.length){
								index = 0;
							}
							b.setBackgroundColor(colors[index]);
							return true;
						case R.id.submenu1 :
							return true;
						default:
							return false;
						}
					}
				});
				popupMenu.show(); // 메뉴 띄우기
				return true;
			}
		});
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// ContextMenu는 HeaderTitle 영역을 구성할 수 있다.
//		menu.setHeaderIcon(R.drawable.ic_launcher); // 2.3 이전만 가능
//		menu.setHeaderTitle("좋아하는 과일을 고르세요");
		LayoutInflater lif = (LayoutInflater) 
				getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = lif.inflate(R.layout.title, null);
		menu.setHeaderView(view); // 마지막에 설정한 Header 만 등록됨
		menu.add(0,1,0,"오렌지");
		menu.add(0,2,0,"딸기");
		menu.add(0,3,0,"키위");
		menu.add(0,4,0,"바나나");
//		MenuInflater mi = getMenuInflater();
//		mi.inflate(R.menu.xx, menu);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
} // end of class
