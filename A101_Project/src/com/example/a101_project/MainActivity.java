package com.example.a101_project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
public class MainActivity extends Activity implements SensorEventListener {
	
	// 공통 변수
	private int count = 0; // 걸음수
	
	// 센서관련 변수 
	private long lastTime;
	private float speed;
	private float lastX;
	private float lastY;
	private float lastZ;
	private float x, y, z;
	private static final int SHAKE_THRESHOLD = 800;
	@SuppressWarnings("deprecation")
	private static final int DATA_X = SensorManager.DATA_X;
	@SuppressWarnings("deprecation")
	private static final int DATA_Y = SensorManager.DATA_Y;
	@SuppressWarnings("deprecation")
	private static final int DATA_Z = SensorManager.DATA_Z;
	private SensorManager sensorManager;
	private Sensor accelerormeterSensor;

	// 기록 관련 변수
	private ArrayList<DataSet> al = new ArrayList<DataSet>(); // 기존 기록 리스트
	private String nowDate = "";
	
	// 디자인 관련 변수
	private TextView timeTv;
	private TextView tv[] = new TextView[4];
	private AnimationDrawable ad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 오늘 날짜 얻기
		Date now = new Date(); 
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		nowDate = format.format(now);
		
		// View 객체 연결
		ImageView iv = (ImageView) findViewById(R.id.imageView1);// 애니메이션
		timeTv = (TextView) findViewById(R.id.textView1); // 현재시간
		tv[0] = (TextView) findViewById(R.id.textView2); // 걸음수
		tv[1] = (TextView) findViewById(R.id.textView3); // 걸은 거리
		tv[2] = (TextView) findViewById(R.id.textView4); // 칼로리
		tv[3] = (TextView) findViewById(R.id.textView5); // 동기화 정보
		Button syncB = (Button) findViewById(R.id.button1); // 동기화 버튼

		// AnimationDrawable 객체 연결
		ad = (AnimationDrawable) iv.getDrawable();// src
		
		// 센서 서비스 얻어오기
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		// 파일을 읽어서  배열에 넣기
		goJSONParser();
		
		// 배열을 읽어서 오늘 데이터 넣기
		if (al.size() == 0) { // 기존 데이터가 없으면 (초기설치시)
			dataCal(0); // 화면에 정보 뿌리기
			tv[3].setText("동기화 데이터가 없습니다."); // 동기화 정보 부분
		}else{ // 기존 데이터가 있으면
			for (int i = 0; i < al.size(); i++) { // goJSONParser()를 통해서 받아온 기존 정보 배열중에
				if (al.get(i).date.equals(nowDate)) { // 기존 데이터중에 오늘 데이터 있으면
					String date = al.get(i).date;
					String time = al.get(i).time;
					String step = al.get(i).step;
					
					// 데이터 계산 입력
					dataCal(Integer.valueOf(step)); // 화면에 정보 뿌리기
					String temp = (date);
					temp = temp.substring(0,4)+"/"+temp.substring(4,6)+"/"+temp.substring(6,8);
					tv[3].setText(temp + " " + (time)+"에 마지막 동기화되었습니다."); // 정보화 정보 부분
					break;
				} else { // 기존 데이터가 있지만 오늘 데이터 없으면
					dataCal(0); // 화면에 정보 뿌리기
					tv[3].setText("동기화 데이터가 없습니다."); // 동기화 정보 부분
				}
			} 
		} // 배열을 읽어서 오늘 데이터 넣기 끝

		// 동기화 버튼 클릭시
		syncB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// 데이터 ArrayList에 등록 후 메시지 알림
				addData();
				Toast.makeText(getApplicationContext(), "동기화 되었습니다", Toast.LENGTH_SHORT).show();
			}
		}); // end of syncB.setOnClickListener(new OnClickListener()
		
		// 시간 경과 쓰레드
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					while (!isInterrupted()) {
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// 현재시간을 msec 으로 구한다.
								long now = System.currentTimeMillis();
								// 현재시간을 date 변수에 저장한다.
								Date date = new Date(now);
								// nowDate 변수에 값을 저장한다.
								SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
								String formatDate = sdfNow.format(date);
								// TextView에 현재시간 문자열 할당
								timeTv.setText("현재시각: " + formatDate); 
							} // end of new Runnable() run()
						}); // end of new Runnable()
						Thread.sleep(1000);
					} // end of while (!isInterrupted())
				} catch (InterruptedException e) {
					Log.d("A101","MainActivity - onCreate() - InterruptedException");
				} // end of try
			} // end of Thread t run()
		}; // end of Thread t
		
		// 시간 경과 쓰레드 시작
		t.start();
	} // end of onCreate
	
	/** JSONParser */
	protected void goJSONParser() {
		// JSON: JavaScript Object Notation
		//		대부분 언어에서 사용가능, 개발형 포맷, Text로 되어있어서, 사람, 기계 모두가 읽고 쓰기 편하다
		// AJAX 용으로 유용, XML 대체하기위한 포맷
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
			read = sb.toString(); // 파일 내용 담기 
			
			br.close();
			fis.close();
		} catch (FileNotFoundException e) {
			Log.d("A101","MainActivity - goJSONParser() - FileNotFoundException");
		} catch (IOException e) {
			Log.d("A101","MainActivity - goJSONParser() - IOException");
		}
		
		// 읽은 파일 내용을 바탕으로 ArrayList 배열 생성
		if(read!=null || !"".equals(read)){
			try {
				JSONArray jarray = new JSONArray(read);
	
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject jObject = jarray.getJSONObject(i);
					String date = jObject.getString("date");
					String time = jObject.getString("time");
					String step = jObject.getString("step");
					
					al.add(new DataSet(date,time,step));
				}
			} catch (JSONException e) {
				Log.d("A101","MainActivity - goJSONParser() - JSONException");
			}
		} 
	} // end of goJSONParser()
	
	/** 데이터 ArrayList에 등록 및 파일 저장 */
	protected void addData(){
		
		// 현재시간 가공
		String date = timeTv.getText().toString();
		String word[] = date.split(" ");
		date = word[1].replace("/", "");
		String time = word[2];
		
		// 걸음수
		String step = tv[0].getText().toString(); 
		
		int index = -1;
		
		// 오늘날짜 데이터가 있는지 검색
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i).date.equals(nowDate)) {
				index = i;
				break;
			}
		}
		
		if (index == -1){ // 오늘날짜 데이터가 없으면
			// ArrayList에 추가
			al.add(new DataSet(date,time,step));
		} else { // 있으면
			// ArrayList 데이터 수정
			al.set(index, new DataSet(date,time,step));
		}
		
		tv[3].setText(word[1]+" "+word[2]+"에 마지막 동기화되었습니다."); // 동기화 정보 부분
		
		// ArrayList 내용을 파일로 저장
		JSONObject obj = new JSONObject();
		try {
			JSONArray jArray = new JSONArray();

			for (int i = 0; i < al.size(); i++)//배열
			{
				JSONObject sObject = new JSONObject();
				sObject.put("date", al.get(i).date);
				sObject.put("time", al.get(i).time);
				sObject.put("step", al.get(i).step);
				jArray.put(sObject);
			}
			obj.put("item", jArray);
		} catch (JSONException e) {
			Log.d("A101","MainActivity - addData() - JSONException");
		}
		String result = obj.toString();
		result = result.substring(8, result.length()-1);
		
		try {
			FileOutputStream fos = openFileOutput("record.txt", Context.MODE_PRIVATE); // 덮어쓰기
			
			PrintWriter out = new PrintWriter(fos); // 보조스트림
			out.println(result); 
			out.close(); // 파일 닫기
			fos.close(); 
			
		} catch (FileNotFoundException e) {
			Log.d("A101","MainActivity - addData() - FileNotFoundException");
		} catch (IOException e) {
			Log.d("A101","MainActivity - addData() - IOException");
		} 
	} // end of addData()
	
	/** 데이터 계산 입력 */
	public void dataCal(int count) {
		this.count = count;
		String step = String.format("%d", count);
		tv[0].setText(step);
		String distance = String.format("%.2f", count* 30 * 0.01);
		tv[1].setText(distance);
		String cal = String.format("%.2f", 5.5*70*count/1000);
		tv[2].setText(cal);
	} // end of dataCal(int count)
	
	
	// 생명주기 onStart
	@Override
	public void onStart() {
		super.onStart();

		if (accelerormeterSensor != null) {
			sensorManager.registerListener(this, accelerormeterSensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
	} // end of onStart()

	// 생명주기 onStop
	@Override
	public void onStop() {
		super.onStop();
		
		addData(); // 데이터 입력 및 저장
		if (sensorManager != null) {
			sensorManager.unregisterListener(this);
		}
	} // end of onStop()

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	} // end of onAccuracyChanged(Sensor sensor, int accuracy)

	// 센서값 바뀔때 호출되는 콜백 메서드
	@SuppressWarnings("deprecation")
	@Override
	public void onSensorChanged(SensorEvent event) {

		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			long currentTime = System.currentTimeMillis();

			long gabOfTime = (currentTime - lastTime);

			// 이전 센서값 변경데이터와 0.1초이상 차이나면 반영  
			if (gabOfTime > 100) {
				
				lastTime = currentTime;

				x = event.values[SensorManager.DATA_X];
				y = event.values[SensorManager.DATA_Y];
				z = event.values[SensorManager.DATA_Z];
				speed = Math.abs(x + y + z - lastX - lastY - lastZ) /
						gabOfTime * 10000;

				if (speed > SHAKE_THRESHOLD) {

					count++;
					ad.start(); // 센서값 반영시 애니메이션 동작
					dataCal(count);

				}else{
					ad.stop();
				}

				lastX = event.values[DATA_X];
				lastY = event.values[DATA_Y];
				lastZ = event.values[DATA_Z];

			}
		}
	} // end of onSensorChanged(SensorEvent event)

	// 옵션메뉴 생성시
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	} // end of onCreateOptionsMenu(Menu menu)

	// 옵션메뉴 팝업시마다
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) { // 메뉴가 뜰때마다 호출되는 콜백메서드
		
		if(al.size() == 0){ // 이전 데이터 없음
			menu.getItem(0).setEnabled(false);
		} else { // 이전 데이터 있음
			menu.getItem(0).setEnabled(true);
		}
		
		return super.onPrepareOptionsMenu(menu);
	} // end of onPrepareOptionsMenu(Menu menu)
	
	// 옵션메뉴 아이템 클릭시마다
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.record) {
			addData();
			Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
			startActivity(intent);
		} else if (id == R.id.reset) {
			tv[0].setText("0");
			tv[1].setText("0");
			tv[2].setText("0");
		}
		return super.onOptionsItemSelected(item);
	} // end of onOptionsItemSelected(MenuItem item)
} // end of class

class DataSet {
	String date = "";
	String time = "";
	String step = "";
	
	public DataSet(String date, String time, String step) {
		super();
		this.date = date;
		this.time = time;
		this.step = step;
	}
} // end of class DataSet