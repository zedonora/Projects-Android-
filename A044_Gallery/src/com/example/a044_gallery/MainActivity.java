package com.example.a044_gallery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 1. 다량의 데이터
		// 2. AdapterView 선언 (ListView, GridView, Gallery, Spinner)
		// 3. Adapter 객체생성, 연결(setAdapter())
		final int[] imgs = {
	            R.drawable.choihyeonsik,
	            R.drawable.daeheejeong,
	            R.drawable.deokhwan,
	            R.drawable.hanhyeyeon,
	            R.drawable.jaewoopark,
	            R.drawable.jeongdarin,
	            R.drawable.jeongjaehwan,
	            R.drawable.jeongjinkeun,
	            R.drawable.jiyunsik,
	            R.drawable.joboram,
	            R.drawable.junghongui,
	            R.drawable.jungyuno,
	            R.drawable.kangseungwon,
	            R.drawable.kimjaehyun,
	            R.drawable.kimjihyea,
	            R.drawable.kimjihyebb,
	            R.drawable.kimyongrea,
	            R.drawable.leedaehyoung,
	            R.drawable.leeyoonji,
	            R.drawable.leeyoungmin,
	            R.drawable.minju, 
	            R.drawable.minsseam, 
	            R.drawable.sangsuban, 
	            R.drawable.seoheyunwoo, 
	            R.drawable.seongyoon, 
	            R.drawable.yoonjuhee,
	            R.drawable.younggwan,   
	            R.drawable.juyeonkim
	      };
		
		Gallery gal = (Gallery) findViewById(R.id.gallery1);
		
		MyAdapter adapter = new MyAdapter(getApplicationContext(),
									R.layout.cell,
									imgs);
		gal.setAdapter(adapter);
		
		final ImageView iv = (ImageView) findViewById(R.id.imageView1);
		gal.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// item이 선택 (가운데 위치) 되었을 때 호출되는 콜백 메서드
				iv.setImageResource(imgs[position]);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// item이 아무것도 선택되지 않았을 때 호출되는 콜백 메서드
			}
		});
	} // end of onCreate
} // end of class
class MyAdapter extends BaseAdapter{
	int cell;
	int[] imgs;
	LayoutInflater lif;
	public MyAdapter(Context context, int cell, int[] imgs) {
		this.cell = cell;
		this.imgs = imgs;
		this.lif = (LayoutInflater) context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() { // Gallery 에 보여줄 데이터의 개수
		return imgs.length;
	}
	@Override
	public Object getItem(int position) { // position위치의 데이터
		return imgs[position];
	}
	@Override
	public long getItemId(int position) { // position위치의 유니크한 id
		return position;
	}
	// 한 cell 에 데이터를 어떤위젯에 보여줄지 설정
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = lif.inflate(cell, null);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
		iv.setImageResource(imgs[position]);
		return convertView;
	}
}