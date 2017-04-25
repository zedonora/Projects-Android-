package com.example.a112_camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
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
import android.widget.Toast;

public class MainActivity extends Activity {

	private Camera camera;
	private CameraPreView cPreView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 카메라 사용하기
//		1. 권한 획득 AndroidManifest.xml 카메라, 파일 메모리 저장
//		2. 카메라가 달려있는 디바이스인지 확인
//		3. 카메라 자원을 획득
//		4. PreView 보여주기 위한 SurfaceView 작성
//		5. 캡쳐 이벤트 처리 (소리, 파일 저장)
//		6. 카메라 해제
		
		// 카메라 있는지 확인
		if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(getApplicationContext(), 
					"카메라 장치가 없습니다.", Toast.LENGTH_SHORT).show();
			finish();
		}
		try {
			camera = Camera.open(); // 후면 카메라
			// 카메라 모두 사용후에는 release() 해줘야 다른 앱에서 카메라를 사용할 수 있음
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), 
					"Camera 객체를 얻지 못함", Toast.LENGTH_SHORT).show();
		}
		
		// preView 보여주기 위한 작업
		cPreView = new CameraPreView(getApplicationContext(), camera);
		cPreView.setZOrderOnTop(false); // 최상위에 그려질지여부 결정
		
		FrameLayout flPreView = (FrameLayout) findViewById(R.id.frameLayout1);
		flPreView.addView(cPreView); // FrameLayout의 자식 뷰로 등록
		
		Button bCapture = (Button) findViewById(R.id.button1);
		bCapture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 캡쳐버튼
				camera.takePicture(null, null, pc); // 캡쳐하기
//				ShutterCallback shutter : 셔터를 눌렀을 때 이벤트 처리
//				PictureCallback raw : raw 형식의 파일이 생성됐을 때 이벤트 처리
//				PictureCallback jpeg : jpeg 형식의 파일이 생성됐을 때 이벤트 처리
			}
		});
	} // end of onCreate
	PictureCallback pc = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// 사진을 찍은 데이터와 카메라 객체를 넘겨줌 => 원하는 곳에 저장하면 됨
			// 파일명 : Pictures 폴더\CameraCapture
			File sdDir = Environment.getExternalStoragePublicDirectory
										(Environment.DIRECTORY_PICTURES);
			File pictureFileDir = new File(sdDir, "CameraCapture");
			if (!pictureFileDir.exists()) {
				pictureFileDir.mkdirs();
			}
			if (!pictureFileDir.exists()) {
				Toast.makeText(getApplicationContext(),
						"폴더를 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
				return; // 종료
			}
			
			// 파일명
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date/* 현재 날짜*/());
			
			String filename = pictureFileDir.getPath() + "MyPic" + date + ".jpg";
			File pictureFile = new File(filename);
			
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
				getApplicationContext().sendBroadcast(new Intent
				( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(pictureFile)));
				Toast.makeText(getApplicationContext(), 
						"저장완료", Toast.LENGTH_SHORT).show();
			} catch (FileNotFoundException e) {
				Toast.makeText(getApplicationContext(), 
						"저장실패", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
			}
		}
	};
} // end of class

/** 미리보기를 위한 SurfaceView */
class CameraPreView extends SurfaceView implements Callback{
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
			Log.d("test","surfaceCreated - IOException");
		} 
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, 
			int height) {
		// 서피스뷰의 크기 or 포맷이 변경되었을 때 호출된다.
		if (holder.getSurface() == null) {
			return;
		}
		try {
			camera.setPreviewDisplay(holder); 
			camera.startPreview(); // 미리보기 보여주기 시작
		} catch (IOException e) {
			Log.d("test","surfaceChanged- IOException");
		} 
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) { // 서피스 뷰 종료되었을 때
		
	}
}