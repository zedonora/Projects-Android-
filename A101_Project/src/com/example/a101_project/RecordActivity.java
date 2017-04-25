package com.example.a101_project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RecordActivity extends Activity{
	
	DataSet dataSet[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		
		// 파일을 읽어서 일반 배열에 넣기
		goJSONParser();
		
		// AdapterView 선언
		ListView lv = (ListView)findViewById(R.id.listView1); 
		// 커스텀어댑터 생성
		MyAdapter ma = new MyAdapter(getApplicationContext(), R.layout.row, dataSet); 
		// 커스텀어댑터 연결
		lv.setAdapter(ma);
	} // end of onCreate
	
	/** JSONParser */
	protected void goJSONParser() {
		// JSON: Javascript Object Notation
		//		대부분 언어에서 사용가능, 개발형 포맷, Text로 되어있어서, 사람, 기계 모두가 읽고 쓰기 편하다
		// AJAX 용으로 유용, xml 대체하기위한 포맷
		// 기본자료형: 정수, 실수, 문자열 String, boolean(true/false), null
		// 배열Array: []대괄호로 묶음, 순서는 의미없음, 각요소를 ,로 구분
		//			각 요소는 기본자료형 or 배열 or 객체
		// 객체Object: {}중괄호로 묶음, 순서는 의미없음, 각요소를 ,로 구분
		//			각 요소는 이름:값, 이름-"문자열", 값-기본자료형 or 배열 or 객체
		
		// 파일을 읽어서 
		String read = "";
		try {
			FileInputStream fis = openFileInput("record.txt"); // 파일 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			StringBuilder sb = new StringBuilder(); // 싱글쓰레드용
			
			while (true) {
				String str = br.readLine(); // 파일의 끝이면 null 리턴
				if (str == null) break;
				sb.append(str+"\n");
			}
			read = sb.toString(); // 파일 내용 저장 
			
			br.close();
			fis.close();
		} catch (FileNotFoundException e) {
			Log.d("A101","RecordActivity - onCreate() - FileNotFoundException");
		} catch (IOException e) {
			Log.d("A101","RecordActivity - onCreate() - IOException");
		}

		// 파일 내용 배열로 변환
		if(read!=null || !"".equals(read)){
			try {
				
				// 읽은 데이터 배열화
				JSONArray jarray = new JSONArray(read);
	
				// 배열 초기화
				dataSet = new DataSet[jarray.length()];
				
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject jObject = jarray.getJSONObject(i);
					String date = jObject.getString("date");
					String time = jObject.getString("time");
					String step = jObject.getString("step");
					
					// 대~량의 데이터 생성!!
					dataSet[i] = new DataSet(date,time,step);
				}
			} catch (JSONException e) {
				Log.d("A101","RecordActivity - onCreate() - JSONException");
			}
		}
	} // end of goJSONParser()
	
	// 커스텀 리스트뷰 동작부분
	class MyAdapter extends BaseAdapter{
		int row;
		DataSet[] dataSet;
		LayoutInflater lif; 
		
		// 생성자
		public MyAdapter(Context context, int row, DataSet[] dataSet) {
			this.row = row;
			this.dataSet = dataSet;
			// LayoutInflater 객체 얻어오기
			this.lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		// (필수) ListView 에 몇 항목을 보여줄지
		public int getCount() { 
			return dataSet.length;
		}
		// (선택) position번째의 내용를 리턴
		public Object getItem(int position) { 
			return dataSet[position].date;
		}
		// (선택) position번째의 유니크한 id를 리턴
		public long getItemId(int position) { 
			return position;
		}
		// (필수) 해당 position번째의 레이아웃에 어떤 데이터를 보여줄지를 설정
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) { // 처음 한번만 
				convertView = lif.inflate(row, null); // xml 레이아웃을 객체로 변환
				
				if(position%2 == 0){
					convertView.setBackgroundColor(0x00ffffff);
				}else if (position%2 == 1){
					convertView.setBackgroundColor(0x77ffff00);
				}
			}
			
			// 사용자가 클릭시 Music 클래스 객체 내의 정보를 활용해 커스텀 리스트뷰 리턴
			ImageView im = (ImageView) convertView.findViewById(R.id.imageView1);
			im.setImageResource(R.drawable.m12);
			TextView tv1 = (TextView)convertView.findViewById(R.id.textView01);
			String temp = dataSet[position].date;
			temp = temp.substring(0,4)+"/"+temp.substring(4,6)+"/"+temp.substring(6,8);
			tv1.setText(temp);
			TextView tv2 = (TextView)convertView.findViewById(R.id.textView02);
			tv2.setText(dataSet[position].step + " 걸음");
			int count = Integer.valueOf(dataSet[position].step);
			TextView tv3 = (TextView)convertView.findViewById(R.id.textView03);
			tv3.setText(String.format("%.2f",count* 30 * 0.01)+" m");
			TextView tv4 = (TextView)convertView.findViewById(R.id.textView04);
			tv4.setText((5.5*70*count/1000)+" cal");
			
			// 만들어진 커스텀 리스트뷰 리턴
			return convertView;
		}
	} // end of class MyAdapter
} // end of class
