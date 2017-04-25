package com.example.a021_imageview;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Handler handler = new Handler(); // 별도의 쓰레드에서 화면을 변경하려고 할때 사용
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final TextView tv =(TextView) findViewById(R.id.textView1);
		Button b1 =(Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			int cnt;
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "클릭함", Toast.LENGTH_SHORT).show();
				tv.setText(cnt++ + "");
			}
		});
	// 인터넷에서 이미지 파일을 가져오기
//		1. 권한(인터넷 사용 권한)을 얻어와야 함. AndroidMnifest.xml 에 권한 등록
//		2. UI쓰레드 이외의 별도 쓰레드를 생성하여 파일을 다운받아야 함 -> 안드로이드 3.0 버전부터 강제적으로 적용하게 되어 있다.
//		3. Handler 를 사용하여 화면을 변경한다.
		final Button b2 = (Button) findViewById(R.id.button2);
		final ImageView iv = (ImageView) findViewById(R.id.imageView1);
		b2.setOnClickListener(new OnClickListener() {
			int count;
			@Override
			public void onClick(View v) {
				Button b = (Button) v;
				b.setClickable(false);
				// 버튼 클릭시 웹에서 이미지 파일을 받아와서 화면에 이미지를 변경시켜주기
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() { // 별도 쓰레드에서 해야할 작업을 기술
						try {
							URL url[] = { new URL("https://cafeptthumb-phinf.pstatic.net/20140826_73/minsseam_1409063115379HHO89_PNG/canada.png?type=w740"),
									new URL("https://cafeptthumb-phinf.pstatic.net/20140826_72/minsseam_1409063115656jokHG_PNG/france.png?type=w740"),
									new URL("https://cafeptthumb-phinf.pstatic.net/20140826_2/minsseam_1409063115830BpOeg_PNG/korea.png?type=w740"),
									new URL("https://cafeptthumb-phinf.pstatic.net/20140826_229/minsseam_1409063115937R8O0i_PNG/mexico.png?type=w740"),
									new URL("https://cafeptthumb-phinf.pstatic.net/20140826_279/minsseam_1409063116122Xhfah_PNG/poland.png?type=w740"),
									new URL("https://cafeptthumb-phinf.pstatic.net/20140826_37/minsseam_1409063116289y7hzS_PNG/saudi_arabia.png?type=w740")
							};
							InputStream is = url[count].openStream();
							final Bitmap bm = BitmapFactory.decodeStream(is);
							
							// handler를 통해서 화면을 변경해야 한다.
							handler.post(new Runnable() {
								@Override
								public void run() {
									iv.setImageBitmap(bm); // 에러
									count++;
								}
							});
							is.close();
						} catch (MalformedURLException e) {
							Log.d("www", "URL 경로로 파싱 불가");
						} catch (IOException e) {
							Log.d("www", "InputStream을 열지 못함");
						}
					}
				});
				t.start();
			}
		});
		
	} // end of onCreate	
} // end of class
