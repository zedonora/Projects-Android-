package com.example.a042_listview3;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Chat chat[] = {
	            new Chat(R.drawable.choihyeonsik,"최현식","0"),
	            new Chat(R.drawable.daeheejeong,"정대희","1"),
	            new Chat(R.drawable.deokhwan,"박덕환","10"),
	            new Chat(R.drawable.hanhyeyeon,"한혜연","6"),
	            new Chat(R.drawable.jaewoopark,"박재우","0"),
	            new Chat(R.drawable.jeongdarin,"정다린","5"),
	            new Chat(R.drawable.jeongjaehwan,"정재환","5"),
	            new Chat(R.drawable.jeongjinkeun,"정진근","0"),
	            new Chat(R.drawable.jiyunsik,"지윤식","0"),
	            new Chat(R.drawable.joboram,"조보람","0"),
	            new Chat(R.drawable.junghongui,"정홍의","1"),
	            new Chat(R.drawable.jungyuno,"정윤오","1"),
	            new Chat(R.drawable.kangseungwon,"강승원","2"),
	            new Chat(R.drawable.kimjaehyun,"김재현","1"),
	            new Chat(R.drawable.kimjihyea,"김지혜A","5"),
	            new Chat(R.drawable.kimjihyebb,"김지혜B","0"),
	            new Chat(R.drawable.kimyongrea,"김용래","6"),
	            new Chat(R.drawable.leedaehyoung,"이대형","2"),
	            new Chat(R.drawable.leeyoonji,"이윤지","1"),
	            new Chat(R.drawable.leeyoungmin,"이영민","0"),
	            new Chat(R.drawable.minju,"김민주","2"),
	            new Chat(R.drawable.minsseam,"민쌤","0"),
	            new Chat(R.drawable.sangsuban,"반상수","0"),
	            new Chat(R.drawable.seoheyunwoo,"서현우","2"),
	            new Chat(R.drawable.seongyoon,"송승윤","6"),
	            new Chat(R.drawable.yoonjuhee,"윤주희","5"),
	            new Chat(R.drawable.juyeonkim,"김주연","14"),
	            new Chat(R.drawable.younggwan,"김영관","0"),
		};
		ListView lv = (ListView) findViewById(R.id.listView1);
		MyAdapter ma = new MyAdapter(getApplicationContext(),
									R.layout.row,
									chat);
		lv.setAdapter(ma);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(),Next.class);
				intent.putExtra("chat", chat[position]);
				startActivity(intent);
			}
		});
	} // end of onCreate
} // end of class
class MyAdapter extends BaseAdapter{
	int row;
	Chat chat[];
	LayoutInflater lif;
	public MyAdapter(Context context, int row, Chat[] chat) {
		this.row = row;
		this.chat = chat;
		this.lif = (LayoutInflater) context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		return chat.length;
	}
	@Override
	public Object getItem(int position) {
		return chat[position];
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null) {
			convertView = lif.inflate(row, null);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
//		GradientDrawable drawable=
//				(GradientDrawable) convertView(R.drawable.circle_border);
		iv.setImageResource(chat[position].img);
		TextView tv1 = (TextView) convertView.findViewById(R.id.textView1);
		tv1.setText(chat[position].name);
		TextView tv2 = (TextView) convertView.findViewById(R.id.textView2);
		tv2.setText(chat[position].state);
		return convertView;
	}
}
class Chat implements Serializable{
	int img;
	String name = "";
	String state = "";
	public Chat() {
	}
	public Chat(int img, String name, String state) {
		this.img = img;
		this.name = name;
		this.state = state;
	}
	
}
