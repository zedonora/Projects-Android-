package com.example.a114_myalbum;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private MyHandler myhandler; // DB sql문을 관리하는 클래스
	private LocationManager lm; // 위치 관리자
	private String si = "";
	private String gu = "";
	private Cursor cursor;
	private MyAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ListView lv = (ListView) findViewById(R.id.listView1);
		Button bCapture = (Button) findViewById(R.id.button1);

		myhandler = MyHandler.open(getApplicationContext()); // DB sql문을 관리하는 별도의 클래스

		cursor = myhandler.select();

		adapter = new MyAdapter(getApplicationContext(), cursor, true);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Cursor c = (Cursor) lv.getItemAtPosition(position);
				int _id = c.getInt(c.getColumnIndex("_id"));
				String fullpath = c.getString(c.getColumnIndex("fullpath"));
				Intent intent = new Intent(getApplicationContext(), ImageProcess.class);
				intent.putExtra("_id", _id);
				intent.putExtra("fullpath", fullpath);
				startActivityForResult(intent, 100);
			}
		});
		bCapture.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CaptureCamera.class);
				startActivityForResult(intent, 123);
			}
		});
	} // end of onCreate

	@Override
	protected void onDestroy() {
		super.onDestroy(); // 화면 activity 가 종료될때
		myhandler.close(); // database 닫기
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
			String filename = data.getStringExtra("filename");
			String fullpath = data.getStringExtra("fullpath");
			String date = data.getStringExtra("date");
			String time = data.getStringExtra("time");
			geocoder(); // 시, 구 알아오기
			cursor = myhandler.insert(filename, fullpath, date, time, si, gu);
			adapter.changeCursor(cursor); // 변경된 커서 적용
		} else if (requestCode == 100 && resultCode == 5) { // 이미지 처리중, 파일 없을 때
			int _id = data.getIntExtra("delete_id", -1);
			cursor = myhandler.delete(_id);
			adapter.changeCursor(cursor); // 변경된 커서 적용
		}
	}
	/** 시, 구 주소 알아오기 - 역지오코딩 */
	public void geocoder() {
		Geocoder coder = new Geocoder(this); // 지오코더 객체 준비 번지까지만
		// 위치 관리자
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// 위치제공자 목록 가져오기
		List<String> providers = lm.getProviders(true);
		Location location = lm.getLastKnownLocation(providers.get(1)); // GPS
		if (location == null) {
			location = lm.getLastKnownLocation(providers.get(2)); // network
			if (location == null) {
				location = lm.getLastKnownLocation(providers.get(0)); // passive
			}
		}
		// 역지오코딩 : 위도,경도 좌표 => 주소,지명으로 변환
		try {
			List<Address> list = coder.getFromLocation(
				location.getLatitude(), location.getLongitude(), // 위치
				10); // 조회결과 최대개수
			if (list != null && list.size() > 0) {
				Address address = list.get(0);
				si = address.getAdminArea(); // 시
				gu = address.getLocality(); // 구
			}
		} catch (IOException e) {
		}
	}
} // end of class
class MyAdapter extends CursorAdapter {
	public MyAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = lif.inflate(R.layout.row, null);
		return view;
	}
	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		ImageView iv = (ImageView)view.findViewById(R.id.imageView1);
		final String fullpath = cursor.getString(cursor.getColumnIndex("fullpath"));
		iv.setImageBitmap(BitmapFactory.decodeFile(fullpath));
		TextView tv = (TextView)view.findViewById(R.id.textView1);
		tv.setText(cursor.getString(cursor.getColumnIndex("filename")));
		TextView tvDate = (TextView)view.findViewById(R.id.tvDate);
		tvDate.setText(cursor.getString(cursor.getColumnIndex("date")));
		TextView tvTime = (TextView)view.findViewById(R.id.tvTime);
		tvTime.setText(cursor.getString(cursor.getColumnIndex("time")));
		TextView tvSi = (TextView)view.findViewById(R.id.tvSi);
		tvSi.setText(cursor.getString(cursor.getColumnIndex("si")));
		TextView tvGu = (TextView)view.findViewById(R.id.tvGu);
		tvGu.setText(cursor.getString(cursor.getColumnIndex("gu")));
	}
}
/** DB sql문을 관리하는 클래스 */
class MyHandler {
	private static MyHandler myHandler;
	private MySQLiteOpenHelper helper;
	private String tableName = "album";

	private MyHandler(Context context) { // private 생성자 : database 초기화 작업
		helper = new MySQLiteOpenHelper(context, "album.db", null, 1, tableName);
		// 제어권자, 파일명, 커서팩토리, 버전
	}

	public static MyHandler open(Context context) { // static 으로 객체 제공
		if (myHandler == null) {
			myHandler = new MyHandler(context);
		}
		return myHandler;
	}

	public Cursor insert(String filename, String fullpath, String date, String time, String si, String gu) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("filename", filename);
		values.put("fullpath", fullpath);
		values.put("date", date);
		values.put("time", time);
		values.put("si", si);
		values.put("gu", gu);
		long result = db.insert(tableName, null, values); // 결과를 받을 수 있다
		// db.execSQL(sql); // db 명령을 수행후 결과를 리턴받지 못함

		String str;
		if (result == -1) str = "에러 발생";
		else str = result + "번째 row insert 성공";
		Log.d("sqlite3", str);

		return select();
	} // end of insert()
	public Cursor select() { // table 조회
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query(tableName, null, null, null, null, null, null); // null
		Log.d("sqlite3", "select - cursor : " + c);
		return c;
	} // end of select()

	public Cursor delete(int _id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		int result = db.delete(tableName, "_id=?", new String[] { _id+"" });
		// db.execSQL(sql); // db 명령을 수행후 결과를 리턴받지 못함

		String str = result + "개 row delete";
		Log.d("sqlite3", str);

		return select();
	} // end of delete()

	public void close() {
		helper.close(); // database 객체 닫기
	}
} // end of class MyHandler

/** table을 생성하고, database 객체 얻어오는 클래스 */
class MySQLiteOpenHelper extends SQLiteOpenHelper {
	String tableName;

	public MySQLiteOpenHelper(Context context, String name, CursorFactory factory, int version, String tableName) {
		super(context, name, factory, version);
		// Context context : 현재화면 제어권자
		// String name : database의 파일명
		// CursorFactory factory : null 표준 커서팩토리 사용
		// int version : database의 버전
		this.tableName = tableName;
	}

	@Override
	public void onCreate(SQLiteDatabase db) { // 처음 database 만들때 호출되는 콜백메서드
		// table 생성 코드
		String sql = "create table " + tableName
				+ " (_id integer primary key autoincrement, filename text, fullpath text, date text, time text, si text, gu text);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// database 의 변경이 필요할 때 호출되는 콜백메서드
		// Upgrade는 손이 많이 가는 작업이다, 가능하면 처음에 table 디자인을 잘 해야한다
		if (oldVersion == 1 && newVersion == 3) { // version별로 table 변경작업을 해야한다
		} else if (oldVersion == 2 && newVersion == 3) {
		}

		String sql = "drop table if exists " + tableName + ";"; // table이 존재할 경우
																// drop
		db.execSQL(sql);

		onCreate(db); // table 다시 생성
	}
} // end of class MySQLiteOpenHelper
