package com.example.a099_layoutanimation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// ListView
		String str[] = {"월요일","화요일","수요일","목요일","금요일","토요일","일요일",
						"월요일","화요일","수요일","목요일","금요일","토요일","일요일"};
		ListView lv = (ListView) findViewById(R.id.listView1);
		MyAdapter adapter = new MyAdapter(getApplicationContext(),
										R.layout.row,
										str);
		lv.setAdapter(adapter);
	} // end of onCreate
	
} // end of class

class MyAdapter extends BaseAdapter {
	int row;
	String[] str;
	LayoutInflater lif;
	public MyAdapter(Context context, int row, String[] str) {
		this.row = row;
		this.str = str;
		this.lif = (LayoutInflater) context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() { // (필수) 리스트뷰에 보여줄 항목의 개수
		return str.length;
	}
	@Override
	public Object getItem(int position) { // (선택) 해당 position 의 데이터
		return str[position];
	}
	@Override
	public long getItemId(int position) { // (선택) 해당 position의 고유한 id
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// (필수) 해당 position 의 row를 설정
		if(convertView == null) {
			convertView = lif.inflate(row, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.textView1);
		tv.setText(str[position]);
		return convertView;
	}
}