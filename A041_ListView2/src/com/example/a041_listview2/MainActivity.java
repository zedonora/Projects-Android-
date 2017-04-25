package com.example.a041_listview2;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

class Fruit implements Serializable{
	String name = "";
	int img;
	String info = "";
	public Fruit() {
	}
	public Fruit(String str, int img, String info) {
		this.name = str;
		this.img = img;
		this.info = info;
	}
}
public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Fruit fruit[] = {new Fruit("사과",R.drawable.apple,"빨간색\n원산지 : 한국, 비타민많음"),
						new Fruit("바나나",R.drawable.banana,"노란색\n원산지 : 한국, 섬유질많음"),
						new Fruit("체리",R.drawable.cherry,"빨간색\n원산지 : 프랑스, 비타민적음"),
						new Fruit("자두",R.drawable.jadoo_plum,"빨간색\n원산지 : 미국, 비타민적음"),
						new Fruit("메론",R.drawable.melon,"녹색\n원산지 : 소련, 섬유질적음"),
						new Fruit("레몬",R.drawable.remon,"노란색\n원산지 : 에티오피아, 비타민중간"),
						new Fruit("사과",R.drawable.apple,"빨간색\n원산지 : 한국, 비타민많음"),new Fruit("바나나",R.drawable.banana,"노란색\n원산지 : 한국, 섬유질많음"),new Fruit("체리",R.drawable.cherry,"빨간색\n원산지 : 프랑스, 비타민적음"),new Fruit("자두",R.drawable.jadoo_plum,"빨간색\n원산지 : 미국, 비타민적음"),new Fruit("메론",R.drawable.melon,"녹색\n원산지 : 소련, 섬유질적음"),new Fruit("레몬",R.drawable.remon,"노란색\n원산지 : 에티오피아, 비타민중간"),
						new Fruit("사과",R.drawable.apple,"빨간색\n원산지 : 한국, 비타민많음"),new Fruit("바나나",R.drawable.banana,"노란색\n원산지 : 한국, 섬유질많음"),new Fruit("체리",R.drawable.cherry,"빨간색\n원산지 : 프랑스, 비타민적음"),new Fruit("자두",R.drawable.jadoo_plum,"빨간색\n원산지 : 미국, 비타민적음"),new Fruit("메론",R.drawable.melon,"녹색\n원산지 : 소련, 섬유질적음"),new Fruit("레몬",R.drawable.remon,"노란색\n원산지 : 에티오피아, 비타민중간"),
						new Fruit("사과",R.drawable.apple,"빨간색\n원산지 : 한국, 비타민많음"),new Fruit("바나나",R.drawable.banana,"노란색\n원산지 : 한국, 섬유질많음"),new Fruit("체리",R.drawable.cherry,"빨간색\n원산지 : 프랑스, 비타민적음"),new Fruit("자두",R.drawable.jadoo_plum,"빨간색\n원산지 : 미국, 비타민적음"),new Fruit("메론",R.drawable.melon,"녹색\n원산지 : 소련, 섬유질적음"),new Fruit("레몬",R.drawable.remon,"노란색\n원산지 : 에티오피아, 비타민중간"),
						new Fruit("사과",R.drawable.apple,"빨간색\n원산지 : 한국, 비타민많음"),new Fruit("바나나",R.drawable.banana,"노란색\n원산지 : 한국, 섬유질많음"),new Fruit("체리",R.drawable.cherry,"빨간색\n원산지 : 프랑스, 비타민적음"),new Fruit("자두",R.drawable.jadoo_plum,"빨간색\n원산지 : 미국, 비타민적음"),new Fruit("메론",R.drawable.melon,"녹색\n원산지 : 소련, 섬유질적음"),new Fruit("레몬",R.drawable.remon,"노란색\n원산지 : 에티오피아, 비타민중간"),
		};
		
		ListView lv = (ListView) findViewById(R.id.listView1);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), 
						fruit[position].name+"선택됨", 
						Toast.LENGTH_SHORT).show();
				// 1. 다음화면 작성 java, xml
				// 2. 화면 Activity 를 등록 AndroidManifest.xml
				// 3. Intent 객체 생성해서 화면전환 startActivity();
				Intent intent = new Intent(getApplicationContext(),Information.class);
				intent.putExtra("fruit", fruit[position]);
				startActivity(intent);
			}
		});
		
		NewAdaptor na = new NewAdaptor(getApplicationContext(),
									R.layout.row,
									fruit);
		lv.setAdapter(na);
	} // end of onCreate
} // end of class
class NewAdaptor extends BaseAdapter{
	int row;
	LayoutInflater lif;
	Fruit fruit[];
	public NewAdaptor(Context context, int row, Fruit[] item) {
		this.row = row;
		this.fruit = item;
		this.lif = (LayoutInflater) context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		return fruit.length;
	}

	@Override
	public Object getItem(int position) {
		return fruit[position].name;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = lif.inflate(row, null);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
		iv.setImageResource(fruit[position].img);
		TextView tv = (TextView) convertView.findViewById(R.id.textView1);
		tv.setText(fruit[position].name);
		if(position%2==0){
			convertView.setBackgroundColor(0x3fff6021);
		} else {
			convertView.setBackgroundColor(0xffffffff);
		}
		return convertView;
	}
}