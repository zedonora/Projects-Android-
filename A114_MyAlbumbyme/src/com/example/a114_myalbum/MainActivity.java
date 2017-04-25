package com.example.a114_myalbum;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String filename;
	ArrayList<String> filePath = new ArrayList<String>();
	private ListView lv;
	private MyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bCapture = (Button) findViewById(R.id.bCapture);
		bCapture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // Camera class로 intent 넘겨서 사진 찍게 함
				Intent intent = new Intent(getApplicationContext(), MyCamera.class);
				startActivityForResult(intent,123); 
			}
		});
		// ListView
//		filePath
		lv = (ListView) findViewById(R.id.listView1);
		adapter = new MyAdapter(getApplicationContext(),
										R.layout.row,
										filePath);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, 
					long id) {
				Intent intent = new Intent(getApplicationContext(), Select.class);
				intent.putExtra("filename",filePath.get(position));
				startActivity(intent);
			}
		});
	} // end of onCreate
	@Override
	protected void onResume() {
		super.onResume();
		lv.setAdapter(adapter);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// startActivityForResult로 인텐트 날린 후 되돌아 왔을 때 호출되는 콜백 메서드
		if(requestCode == 123 && resultCode == Activity.RESULT_OK) {
//			Bitmap bm = BitmapFactory.decodeFile(filename); // 한줄로 작성도 가능
			filename = data.getStringExtra("filename");	
			filePath.add(filename);
			Toast.makeText(getApplicationContext(), 
					filePath.size()+"", Toast.LENGTH_SHORT).show();
		}
	} // end of onActivityResult
} // end of class
class MyAdapter extends BaseAdapter {
	int row;
	ArrayList<String> filePath;
	LayoutInflater lif;
	public MyAdapter(Context context, int row, ArrayList<String> filePath) {
		this.row = row;
		this.filePath = filePath;
		this.lif = (LayoutInflater) context.getSystemService
				(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() { // (필수) 리스트뷰에 보여줄 항목의 개수
		return filePath.size();
	}
	@Override
	public Object getItem(int position) { // (선택) 해당 position 의 데이터
		return filePath.get(position);
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
		TextView tvAlbumList = (TextView) convertView.findViewById(R.id.tvAlbumList);
		ImageView ivAlbumList = (ImageView) convertView.findViewById(R.id.ivAlbumList);
		// 사진의 경로에서 이미지 가져오는 방법 + 사진의 이름 가져오는 방법
		tvAlbumList.setText(filePath.get(position));
		Bitmap bm = BitmapFactory.decodeFile(filePath.get(position));
		ivAlbumList.setImageBitmap(bm);
		return convertView;
	}
}