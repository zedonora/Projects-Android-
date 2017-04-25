package com.example.a113_sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private MyHandler myhandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final EditText etInsertName = (EditText) findViewById(R.id.etInsertName);
		final EditText etInsertAge = (EditText) findViewById(R.id.etInsertAge);
		final EditText etInsertAddress = (EditText) findViewById(R.id.etInsertAddress);
		Button bInsert = (Button) findViewById(R.id.bInsert);
		
		final EditText etUpdateName = (EditText) findViewById(R.id.etUpdateName);
		final EditText etUpdateAge = (EditText) findViewById(R.id.etUpdateAge);
		Button bUpdate = (Button) findViewById(R.id.bUpdate);
		
		final EditText etDeleteName = (EditText) findViewById(R.id.etDeleteName);
		Button bDelete = (Button) findViewById(R.id.bDelete);
		
		final TextView tvSelect = (TextView) findViewById(R.id.tvSelect);
		
		// DB sql문을 관리하는 별도의 클래스 사용
		myhandler = new MyHandler(getApplicationContext());
		
		bInsert.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = etInsertName.getText().toString();
				int age = Integer.valueOf(etInsertAge.getText().toString());
				String address = etInsertAddress.getText().toString();
				String result = myhandler.insert(name, age, address);
				tvSelect.setText(result);
			}
		});
		bUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = etUpdateName.getText().toString();
				int age = Integer.valueOf(etUpdateAge.getText().toString());
				// 이름이 name 항목의 나이를 age 로 변경하기
				String result = myhandler.update(name,age); 
				tvSelect.setText(result);
			}
		});
		bDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = etDeleteName.getText().toString();
				// 이름이 name 인 항목을 삭제하기
				String result = myhandler.delete(name);
				tvSelect.setText(result);
			}
		});
	} // end of onCreate
	@Override
	protected void onDestroy() {
		super.onDestroy(); // 화면 activity 가 종료될 때
		myhandler.close(); // 데이터베이스 닫기
	}
} // end of class
class MyHandler{
	MySQLiteOpenHelper helper;
	public MyHandler(Context context) {
		// 데이터베이스 초기화 작업
		helper = new MySQLiteOpenHelper(context, "person.db", null, 1);
								// 		제어권자,		파일명,	커서팩토리,	버전
	}
	public String insert(String name, int age, String address) { // 삽입
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("age", age);
		values.put("address", address);
		long result = db.insert("student", null, values); // 결과를 받을 수 있다.
//		db.execSQL(sql); // db 명령을 수행 후 결과를 리턴받지 못함
		String str = "insert : " + result + "번째 row insert 성공";
		Log.d("sqlite3",str);
		
		return str + "\n" + select();
	} // end of insert()
	public String select() { // 테이블 조회
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("student", null, null, null, null, null, null);
		String str = "[name]\t[age]\t[address]";
		while(c.moveToNext()) {
			String name = c.getString(c.getColumnIndex("name"));
			int age = c.getInt(c.getColumnIndex("age"));
			String address = c.getString(c.getColumnIndex("address"));
			str += "\n" + name + "\t" + age +"\t" + address;
			Log.d("sqlite", name+","+age+","+address);
		}
		return str;
	} // end of select()
	public String update(String name, int age) { // 수정
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("age", age); // 기존의 나이를 age로 변경해라
		int result = db.update("student", values, "name=?", new String[]{name});
//		db.execSQL(sql);
		String str = result + "개 row update 성공";
		Log.d("sqlite", str);
		return str + "\n" + select();
	} // end of update()
	public String delete(String name) { // 삭제
		SQLiteDatabase db = helper.getWritableDatabase();
		int result = db.delete("student", "name=?", new String[] {name});
//		db.execSQL(sql);
		String str = result + "개의 행이 delete 성공";
		Log.d("sqlite", str);
		return str + "\n" + select();
	} // end of delete()
	public void close() {
		helper.close(); // 데이터베이스 자원 해제
	}
} // end of class MyHandler
/** database 를 생성하고, 객체 얻어오는 작업하는 클래스*/
class MySQLiteOpenHelper extends SQLiteOpenHelper{
	public MySQLiteOpenHelper(Context context, String name, CursorFactory factory, 
			int version) {
		super(context, name, factory, version);
//		Context context : 현재화면 제어권자
//		String name : 데이터베이스 파일명
//		CursorFactory factory : null 표준 커서팩토리 사용
//		int version : 데이터베이스의 버전
		
	}
	@Override
	public void onCreate(SQLiteDatabase db) { // 처음 데이터 베이스 만들 때
		// 테이블 생성 코드
		String sql = "create table student (name text, age integer, address text);";
		db.execSQL(sql);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 테이블이 변경되었을 때 호출되는 콜백 메서드
		if (oldVersion == 1 && newVersion == 2) {
			// 테이블 변경작업을 해야 한다.
			// 가능하면 처음에 테이블 디자인을 잘 해야한다.
		} 
		String sql = "drop table if exists student;"; // 테이블이 존재할 경우만 드랍
		db.execSQL(sql);
		
		onCreate(db); // 테이블 다시 생성
	}
}
