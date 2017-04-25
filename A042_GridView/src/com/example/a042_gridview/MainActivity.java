package com.example.a042_gridview;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	GridView gv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 1. 다량의 데이터
		// 2. AdapterView 선언
		// 3. Adapter 생성, 설정 (한 셀을 디자인한 layout)
		
		final Nation nation[] = {new Nation(R.drawable.canada,"캐나다"),
						new Nation(R.drawable.france,"프랑스"),
						new Nation(R.drawable.korea,"한국"),
						new Nation(R.drawable.mexico,"멕시코"),
						new Nation(R.drawable.poland,"폴란드"),
						new Nation(R.drawable.saudi_arabia,"사우디아라비아"),
						new Nation(R.drawable.canada,"캐나다"),new Nation(R.drawable.france,"프랑스"),new Nation(R.drawable.korea,"한국"),new Nation(R.drawable.mexico,"멕시코"),new Nation(R.drawable.poland,"폴란드"),new Nation(R.drawable.saudi_arabia,"사우디아라비아"),
						new Nation(R.drawable.canada,"캐나다"),new Nation(R.drawable.france,"프랑스"),new Nation(R.drawable.korea,"한국"),new Nation(R.drawable.mexico,"멕시코"),new Nation(R.drawable.poland,"폴란드"),new Nation(R.drawable.saudi_arabia,"사우디아라비아"),
						new Nation(R.drawable.canada,"캐나다"),new Nation(R.drawable.france,"프랑스"),new Nation(R.drawable.korea,"한국"),new Nation(R.drawable.mexico,"멕시코"),new Nation(R.drawable.poland,"폴란드"),new Nation(R.drawable.saudi_arabia,"사우디아라비아"),
						new Nation(R.drawable.canada,"캐나다"),new Nation(R.drawable.france,"프랑스"),new Nation(R.drawable.korea,"한국"),new Nation(R.drawable.mexico,"멕시코"),new Nation(R.drawable.poland,"폴란드"),new Nation(R.drawable.saudi_arabia,"사우디아라비아"),
		};
		gv = (GridView) findViewById(R.id.gridView1);
		
		MyAdapter ma = new MyAdapter(
									getApplicationContext(),
									R.layout.cell,
									nation);
		gv.setAdapter(ma);
		
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						Show.class);
				intent.putExtra("nation", nation[position]);
				startActivity(intent);
			}
		});
		Resources r = Resources.getSystem();
		Configuration newConfig = r.getConfiguration();
		onConfigurationChanged(newConfig);
		
	} // end of onCreate
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
			gv.setNumColumns(4);
		}
		super.onConfigurationChanged(newConfig);
	}
} // end of class
class MyAdapter extends BaseAdapter{
	int cell;
	Nation nation[];
	LayoutInflater lif;
	public MyAdapter() {
	}
	public MyAdapter(Context context, int cell, Nation[] nation) {
		this.cell = cell;
		this.nation = nation;
		this.lif = (LayoutInflater) context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		return nation.length;
	}
	@Override
	public Object getItem(int position) {
		return nation[position];
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = lif.inflate(cell, null);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
		iv.setImageResource(nation[position].img);
		TextView tv = (TextView) convertView.findViewById(R.id.textView1);
		tv.setText(nation[position].name);
		return convertView;
	}
}
class Nation implements Serializable{
	int img;
	String name = "";
	public Nation() {
	}
	public Nation(int img, String name) {
		this.img = img;
		this.name = name;
	}
}