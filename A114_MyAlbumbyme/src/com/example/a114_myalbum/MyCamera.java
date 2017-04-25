package com.example.a114_myalbum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyCamera extends Activity {
	private Camera camera;
	private CameraPreView cPreView;
	String filename;
	private MyHandler myhandler;
	String date, city, section;
	TextView tvSelect;
	double lat, lon;
	private LocationManager lm; // 위치 관리자

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);
		// 카메라 있는지 확인
		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(getApplicationContext(), "카메라 장치가 없습니다.", Toast.LENGTH_SHORT).show();
			finish();
		}
		try {
			camera = Camera.open(); // 후면 카메라
			// 카메라 모두 사용후에는 release() 해줘야 다른 앱에서 카메라를 사용할 수 있음
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Camera 객체를 얻지 못함", Toast.LENGTH_SHORT).show();
		}

		// preView 보여주기 위한 작업
		cPreView = new CameraPreView(getApplicationContext(), camera);
		cPreView.setZOrderOnTop(false); // 최상위에 그려질지여부 결정

		FrameLayout flPreView = (FrameLayout) findViewById(R.id.frameLayout1);
		flPreView.addView(cPreView); // FrameLayout의 자식 뷰로 등록

		lm = (LocationManager) 
				getSystemService(Context.LOCATION_SERVICE);
		
		// DB sql문을 관리하는 별도의 클래스 사용
		myhandler = new MyHandler(getApplicationContext());
		Button bCapture = (Button) findViewById(R.id.button1);
		
		bCapture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { // 캡쳐버튼
				camera.takePicture(null, null, pc); // 캡쳐하기
				// ShutterCallback shutter : 셔터를 눌렀을 때 이벤트 처리
				// PictureCallback raw : raw 형식의 파일이 생성됐을 때 이벤트 처리
				// PictureCallback jpeg : jpeg 형식의 파일이 생성됐을 때 이벤트 처리
			}
		});
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 
				0, 0, ll);
	} // end of onCreate
	LocationListener ll = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) { 
			// 위치제공자에 의해 위치정보가 변경되었을 때 호출되는 콜백 메서드
			lat = location.getLatitude();
			lon = location.getLongitude();
		} 
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// 위치제공자의 상태가 바뀌었을 때 호출되는 콜백 메서드
		} 	
		@Override
		public void onProviderEnabled(String provider) {
			// 위치제공자가 활성화 되었을 때 호출되는 콜백 메서드
		}
		@Override
		public void onProviderDisabled(String provider) {
			// 위치제공자가 비활성화 되었을 때 호출되는 콜백 메서드
		}
	};
	Geocoder geocoder;
	PictureCallback pc = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// 사진을 찍은 데이터와 카메라 객체를 넘겨줌 => 원하는 곳에 저장하면 됨
			// 파일명 : Pictures 폴더\CameraCapture
			File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			File pictureFileDir = new File(sdDir, "CameraCapture");
			if (!pictureFileDir.exists()) {
				pictureFileDir.mkdirs();
			}
			if (!pictureFileDir.exists()) {
				Toast.makeText(getApplicationContext(), "폴더를 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
				return; // 종료
			}
			
			// 파일명
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			date = sdf.format(new Date/* 현재 날짜 */());
			filename = pictureFileDir.getPath() + "MyPic" + date + ".jpg";
			File pictureFile = new File(filename);
			
			
			geocoder = new Geocoder(getApplicationContext()); // 지오코더
			// 객체
			// 준비
			try {
				List<Address> list = geocoder.getFromLocation(lat, lon, 10);
				if (list != null && list.size() > 0) {
					Address address = list.get(0);
					String city = address.getAdminArea();
					String section = address.getLocality();
					// +"\n위도 :"+address.getLatitude()
					// +"\n경도 :"+address.getLongitude()
					// +"\n전체주소 :"+address.getAddressLine(0));
					String mydate = date.substring(0, 7);
					String mytime = date.substring(8, 13);
					String result = myhandler.insert(filename, mydate, mytime, city, section);
					Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
				}
			} catch (IOException e1) {
			}
//			tvSelect.setText(result);
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
				getApplicationContext()
						.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, 
								Uri.fromFile(pictureFile)));
				Intent intent = new Intent();
				intent.putExtra("filename", filename);
				setResult(Activity.RESULT_OK, intent);
				finish();
			} catch (FileNotFoundException e) {
				Toast.makeText(getApplicationContext(), "저장실패", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
			}
		}
	};
	@Override
	protected void onResume() {
		super.onResume();
		// 위치관리자에 리스너 이벤트를 등록한다.
//		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 사용할 위치 제공자
//				0, // 통지의 최소 시간간격(ms) long, 0입력하면, 가능한 자주 통지 받음 -> 배터리 사용량 증가
//				0, // 통지의 최소 거리변경기준(m) float, 0입력하면, 가능한 자주 통지 받음 -> 배터리 사용량 증가
//				ll);
	} // end of onResume
	@Override
	protected void onPause() {
		super.onPause();
		if(lm != null) {
			lm.removeUpdates(ll); // 리스너 제거
		}
	} // end of onPause
} // end of class

/** 미리보기를 위한 SurfaceView */
class CameraPreView extends SurfaceView implements Callback {
	Camera camera;
	SurfaceHolder holder;

	public CameraPreView(Context context, Camera camera) { // 생성자
		super(context);
		this.camera = camera;
		this.holder = getHolder();
		this.holder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) { // 서피스뷰가 생성되었을 때
		try {
			camera.setPreviewDisplay(holder); // 카메라에게 프리뷰의 홀더를 제공
			camera.setDisplayOrientation(90); // 세워서 보여줌
			camera.startPreview(); // 미리보기 보여주기 시작
		} catch (IOException e) {
			Log.d("test", "surfaceCreated - IOException");
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// 서피스뷰의 크기 or 포맷이 변경되었을 때 호출된다.
		if (holder.getSurface() == null) {
			return;
		}
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview(); // 미리보기 보여주기 시작
		} catch (IOException e) {
			Log.d("test", "surfaceChanged- IOException");
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) { // 서피스 뷰 종료되었을 때

	}
}

class MyHandler {
	MySQLiteOpenHelper helper;

	public MyHandler(Context context) {
		// 데이터베이스 초기화 작업
		helper = new MySQLiteOpenHelper(context, "image.db", null, 1);
		// 제어권자, 파일명, 커서팩토리, 버전
	}

	public String insert(String filename, String mydate, String mytime, String city, String section) { // 삽입
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("filename", filename);
		values.put("mydate", mydate);
		values.put("mytime", mytime);
		values.put("city", city);
		values.put("section", section);
		long result = db.insert("image", null, values); // 결과를 받을 수 있다.
		// db.execSQL(sql); // db 명령을 수행 후 결과를 리턴받지 못함
		String str = "insert : " + result + "번째 row insert 성공";
		Log.d("sqlite3", str);

		return str + "\n" + select();
	} // end of insert()

	public String select() { // 테이블 조회
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("image", null, null, null, null, null, null);
		String str = "[filename]\t[mydate]\t[mytime]\t[city]\t[section]";
		while (c.moveToNext()) {
			String filename = c.getString(c.getColumnIndex("filename"));
			String mydate = c.getString(c.getColumnIndex("mydate"));
			String mytime = c.getString(c.getColumnIndex("mytime"));
			String city = c.getString(c.getColumnIndex("city"));
			String section = c.getString(c.getColumnIndex("section"));
			str += "\n" + filename + "\t" + mydate + "\t" + mytime + "\t" + city + "\t" + section;
		}
		return str;
	} // end of select()

	public String delete(String filename) { // 삭제
		SQLiteDatabase db = helper.getWritableDatabase();
		int result = db.delete("image", "filename=?", new String[] { filename });
		// db.execSQL(sql);
		String str = result + "개의 행이 delete 성공";
		Log.d("sqlite", str);
		return str + "\n" + select();
	} // end of delete()

	public void close() {
		helper.close(); // 데이터베이스 자원 해제
	}
} // end of class MyHandler

/** database 를 생성하고, 객체 얻어오는 작업하는 클래스 */
class MySQLiteOpenHelper extends SQLiteOpenHelper {
	public MySQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// Context context : 현재화면 제어권자
		// String name : 데이터베이스 파일명
		// CursorFactory factory : null 표준 커서팩토리 사용
		// int version : 데이터베이스의 버전

	}

	@Override
	public void onCreate(SQLiteDatabase db) { // 처음 데이터 베이스 만들 때
		// 테이블 생성 코드
		String sql = "create table image (filename text, mydate text, mytime text, city text, section text);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 테이블이 변경되었을 때 호출되는 콜백 메서드
		if (oldVersion == 1 && newVersion == 2) {
			// 테이블 변경작업을 해야 한다.
			// 가능하면 처음에 테이블 디자인을 잘 해야한다.
		}
		String sql = "drop table if exists image;"; // 테이블이 존재할 경우만 드랍
		db.execSQL(sql);

		onCreate(db); // 테이블 다시 생성
	}
}