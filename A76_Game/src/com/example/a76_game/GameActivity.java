package com.example.a76_game;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 */
public class GameActivity extends Activity{ 

	// 생명주기 onCreate() 오버라이드 메서드
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// MainActivity 클래스에서 넘어온 intent 데이터 받기 (노래 제목, 이름)
		Intent intent = getIntent();
		int num = intent.getIntExtra("music", 0);
		String str = intent.getStringExtra("name");
		
		setContentView(R.layout.activity_game);
		MySurfaceView.soundIndex(num);
		
		// xml의 component 자료 가져옴
		final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
		Button b = (Button)findViewById(R.id.button1);
		
		
		b.setOnClickListener(new OnClickListener() {
	         
			@Override
			public void onClick(View v) {
				
				MySurfaceView.threadnotify();
	            linearLayout.setVisibility(View.GONE);
	         }
	      });

	} // end of onCreate
	
	// 뒤로가기 키를 누를시 호출되는 오버라이드 메서드
	@Override
	public void onBackPressed() { 
		
		// MainActivity 화면이동을 위한 Intent
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent); // intent 시작
		finish(); // 현재 activity 닫기
	}
	
} // end of class


// MySurfaceView 클래스
class MySurfaceView extends SurfaceView {
	static MyThread mt; // 화면 변경할 쓰레드
	private static boolean firstTimeInForever = true;
	Bitmap bitmap; // 배경화면 물방울 이미지
	Bitmap bitmap2; // 배경화면 메인배경 이미지
	Bitmap bitmap3; // 배경화면 메인배경 늘린 이미지
	Bitmap bitmap4; // 배경화면 물방울 터진 이미지
	Display display; // 전체화면 정보
	Point screen = new Point(); // 화면 크기
	Canvas c = null; // 캔버스 객체
	SoundPool sp; // 소리 재생 객체
	int soundID[]; // 재생할 소리 아이디 배열
	static int num; // MainActivity 에서 넘어온 재생할 노래 인덱스

	// 현재 Activity에 접근하기 위해 저장하는 객체
	Context context;
	Activity activity;

	Rect[] rec; // 출현할 사각형의 배열
	int first = 0; // 첫 인덱스
	int touchNum = 0; // 터치한 인덱스
	int index = 0; // 사각형의 인덱스
	Point point[][] = new Point[4][4]; // 터치할 사각형의 좌표
	
	LayoutInflater lif; // LayoutInflater 객체
	View view;// 뷰
	TextView motioncount; // 성공 카운터를 표시할 TextView 객체
	ImageView motioniv; // 성공 이미지를 표시할 ImageView 객체
	Toast toast = null; // Toast 객체
	int pre_touchNum = 0; // 이전에 터치한 인덱스번호

///////////////////////////////////////
	boolean stop = false; // 사용하는 곳 없음
	boolean istouch = false; // 사용하는 곳 없음
///////////////////////////////////////
	
	public static void threadnotify() {// 쓰레드 시작 
      synchronized (mt) { // 어서 일해!
         mt.notifyAll();
         firstTimeInForever = false;
      }
   }
	
	int speed = 8; // 사각형의 속도 8
	
	// 화면 변경작업을 할 쓰레드 , PPT 자료 활용하여 설명
	class MyThread extends Thread {
		SurfaceHolder holder;
		boolean go = true; // while 반복을 위한 Flag 변수

		// 생성자
		public MyThread(SurfaceHolder holder) {
			this.holder = holder;
		}

		// 반복을 위한 run() 메서드
		public void run() {
			
			Random ran = new Random(); // 랜덤 객체 선언
			Point p[] = new Point[5]; // 배경화면에서 이동하는 방울의 좌표를 저장할 배열
			for (int i = 0; i < p.length; i++) { // 방울 좌표 초기화
				p[i] = new Point(ran.nextInt(500) + 50, ran.nextInt(800) + 50);
			}

			first = ran.nextInt(4); // 첫 사각형이 출현할 위치 (랜덤)
			if (first != 3) {
				rec[index] = new Rect(point[0][first].x, point[0][first].y, point[1][first + 1].x,
						point[1][first + 1].y);
			} else {
				rec[index] = new Rect(point[0][first].x, point[0][first].y, screen.x, screen.y / 4);
			}

			// while 반복
			while (go) {
				
				Paint paint = new Paint(); // 그리기에 사용될 paint 객체
				paint.setColor(Color.MAGENTA); // 사각형의 색
				try { // 예외 처리
					c = holder.lockCanvas(null); // 화면변경작업을 시작
					c.drawBitmap(bitmap3, 0, 0, null); // 배경화면 그리기

					// 방울 그리기
					for (int i = 0; i < p.length; i++) {
						c.drawBitmap(bitmap, p[i].x + 5, p[i].y, paint);
						p[i].y -= ran.nextInt(10) + 1;
						p[i].x += ran.nextInt(10) + 1;

						if (p[i].y <= 0) { // 범위에서 벗어나면  방울 터짐
							c.drawBitmap(bitmap4, p[i].x + 5, p[i].y, paint);
							p[i].y = c.getHeight();
						} else if (p[i].x + 200 >= c.getWidth()) {
							c.drawBitmap(bitmap4, p[i].x, p[i].y, paint);
							p[i].x = 0;
						}
					}
					
					/**
					 *  첫번째 출발한 사각형의 꼭대기 지점이 첫번째 행에 닿으면
					 *  새로운 사각형을 그린다.
					 */
					if (rec[index].top == point[1][0].y) {
						first = ran.nextInt(4); // 0
						index++;

						if (first != 3) {
							rec[index] = new Rect(point[0][first].x, point[0][first].y, point[1][first + 1].x,
									point[1][first + 1].y);
						} else { // 3번째 열에 그려진느 사각형 예외처리
							rec[index] = new Rect(point[0][first].x, point[0][first].y, screen.x, screen.y / 4);
						}
					}

					paint.setColor(Color.LTGRAY); // 터치한 사각형의 색
					paint.setAlpha(100); // 터치한 사각형의 투명도
					c.drawRect(rec[pre_touchNum], paint); // 이전 터치한 사각형 그리기
					rec[pre_touchNum].top += speed; // 터치한 사각형  상단좌표 이동
					rec[pre_touchNum].bottom += speed; // 터치한 사각형 하단좌표 이동

					// 배열에 저장된 사각형 그리기
					for (int i = touchNum; i <= index; i++) {
						paint.setColor(Color.MAGENTA); // 사각형의 색
						c.drawRect(rec[i], paint); // 사각형 그리기

						rec[i].top += speed; // 사각형 상단좌표 이동
						rec[i].bottom += speed; // 사각형 하단좌표 이동
					}

					// 사각형의 하단부분이 화면의 바닥에 닿을 경우 
					if(rec[touchNum].bottom >= screen.y + (speed)+50 ){
						go = false; // 쓰레드 중지
						GameOver(); // GameOver() 메서드 호출
					}
				} catch (Exception e) {
				} finally { // 항상 실행되는 구문
					if (c != null) 
						holder.unlockCanvasAndPost(c); // 화면변경작업 종료
				}
	            if(firstTimeInForever) { // 맨 처음 시작일 때만 실행
	                try {
	                   synchronized (mt) { // 쓰레드 기다려!
	                      mt.wait();
	                   }
	                } catch (InterruptedException e) {
	                   // TODO Auto-generated catch block
	                   e.printStackTrace();
	                }
	            }
			}
		}

	}


	public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MySurfaceView(Context context, AttributeSet attrs) { // xml에서 자동으로 생성자 실행
		super(context, attrs);
		
		// 현재 Activity에 접근하기 위해 저장
		this.context = context;
		this.activity = (Activity) context;

		

		// 화면 크기 구하기
		display = ((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
		display.getSize(screen);

		
		toast = new Toast(context.getApplicationContext()); // Toast 객체 생성
		lif = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);// 레이아웃 시스템을 가지고옴
		view = lif.inflate(R.layout.cust_toast,null); // 뷰를포함해줌
		motioncount = (TextView) view.findViewById(R.id.textView1); // 성공 카운트를 표시할 TextView id 담기
		motioniv = (ImageView) view.findViewById(R.id.imageView1); // 성공시 표시할 ImageView id 담기

		// 바탕화면에서 사용할 비트맵 파일 연결
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water);
		bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.back); // 기본 이미지
		bitmap3 = Bitmap.createScaledBitmap(bitmap2, screen.x, screen.y, false); // 각 열에 들어갈 이미지를 늘림 
		bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_pang);
		

		SurfaceHolder holder = getHolder(); // SurfaceHolder 객체를 얻어옴
		mt = new MyThread(holder); // MyThread 생성자에 SurfaceHolder 객체를 전달
		holder.addCallback(new Callback() {
			public void surfaceCreated(SurfaceHolder holder) { // surfaceCreated 시작시
				sound();
				mt.start(); // 쓰레드 시작
			}
			
			public void surfaceDestroyed(SurfaceHolder holder) { // surfaceDestroyed 종료시
				mt.go = false; // 쓰레드 종료
				firstTimeInForever = true;
			}
			
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			} // SurfaceView 에 대한 구조적인 변경 발생시 (크기or 포멧)
		});
	}
	
	// 소리관련 메서드
	void sound() {
		// 소리 재생시 사용될 객체 연결
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0); // 0: 음질 기본값
		
		// 재생할 노래 인덱스별 사용할 음악 조각 담기
		switch (num) {
		case 0: // 소
			soundID = new int[cowMusic.length]; // 소 소리 조각의 갯수만큼 배열 생성
			rec = new Rect[soundID.length-1]; // 소리조각의 배열만큼 사각형의 배열 생성
			for (int i = 0; i < soundID.length; i++) { // 소리조각 배열에 소리 담기
				soundID[i] = sp.load(context, cowMusic[i], 1);
			}
			break;
		case 1: // 코끼리
			soundID = new int[elephantMusic.length]; // 코끼리 소리 조각의 갯수만큼 배열 생성
			rec = new Rect[soundID.length-1]; // 소리조각의 배열만큼 사각형의 배열 생성
			for (int i = 0; i < soundID.length; i++) { // 소리조각 배열에 소리 담기
				soundID[i] = sp.load(context, elephantMusic[i], 1);
			}
			break;
		case 2: // 사자
			soundID = new int[lionMusic.length]; // 사자 소리 조각의 갯수만큼 배열 생성
			rec = new Rect[soundID.length-1]; // 소리조각의 배열만큼 사각형의 배열 생성
			for (int i = 0; i < soundID.length; i++) { // 소리조각 배열에 소리 담기
				soundID[i] = sp.load(context, lionMusic[i], 1);
			}
			break;
		case 3: // 원숭이
			soundID = new int[monkeyMusic.length]; // 원숭이 소리 조각의 갯수만큼 배열 생성
			rec = new Rect[soundID.length-1]; // 소리조각의 배열만큼 사각형의 배열 생성
			for (int i = 0; i < soundID.length; i++) { // 소리조각 배열에 소리 담기
				soundID[i] = sp.load(context, monkeyMusic[i], 1);
			}
			break;
		case 4: // 양
			soundID = new int[sheepMusic.length]; // 양 소리 조각의 갯수만큼 배열 생성
			rec = new Rect[soundID.length-1]; // 소리조각의 배열만큼 사각형의 배열 생성
			for (int i = 0; i < soundID.length; i++) { // 소리조각 배열에 소리 담기
				soundID[i] = sp.load(context, sheepMusic[i], 1);
			}
			break;
		case 5: // 호랑이
			soundID = new int[tigerMusic.length]; // 호랑이 소리 조각의 갯수만큼 배열 생성
			rec = new Rect[soundID.length-1]; // 소리조각의 배열만큼 사각형의 배열 생성
			for (int i = 0; i < soundID.length; i++) { // 소리조각 배열에 소리 담기
				soundID[i] = sp.load(context, tigerMusic[i], 1);
			}
			break;
		}
		
		// 출현할 사각형의 배열에 화면 대비 좌표값 저장
		point[0][0] = new Point(0, 0);
		point[0][1] = new Point(screen.x / 4, 0);
		point[0][2] = new Point(screen.x / 4 * 2, 0);
		point[0][3] = new Point(screen.x / 4 * 3, 0);

		point[1][0] = new Point(0, screen.y / 4);
		point[1][1] = new Point(screen.x / 4, screen.y / 4);
		point[1][2] = new Point(screen.x / 4 * 2, screen.y / 4);
		point[1][3] = new Point(screen.x / 4 * 3, screen.y / 4);

		point[2][0] = new Point(0, screen.y / 4 * 2);
		point[2][1] = new Point(screen.x / 4, screen.y / 4 * 2);
		point[2][2] = new Point(screen.x / 4 * 2, screen.y / 4 * 2);
		point[2][3] = new Point(screen.x / 4 * 3, screen.y / 4 * 2);

		point[3][0] = new Point(0, screen.y / 4 * 3);
		point[3][1] = new Point(screen.x / 4, screen.y / 4 * 3);
		point[3][2] = new Point(screen.x / 4 * 2, screen.y / 4 * 3);
		point[3][3] = new Point(screen.x / 4 * 3, screen.y / 4 * 3);
	}
	
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// 인덱스 전달
	static void soundIndex(int num1){
		// MainActivity 에서 넘어온 재생할 노래 인덱스
		num = num1;
		
	}
	
	// GameOver() 메서드
	void GameOver() {
		if(toast!=null)toast.cancel();
		toast.setGravity(Gravity.TOP|Gravity.CENTER,0 ,0);
		motioniv.setImageResource(R.drawable.gameover);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
		
		// 토스트가 소비될때까지 일시정지
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// MainActivity 화면이동을 위한 Intent
		Intent intent = new Intent(context, MainActivity.class);
		activity.startActivity(intent); // intent 시작
		activity.finish(); // 현재 activity 닫기
	}

	// onTouchEvent 오버라이드 메서드
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		// 터치할 순서의 이미지가 아닐 경우 되돌려보내기
		if (touchNum == index) {
			return false;
		}

		// 터치한 좌표 저장
		float x = event.getX();
		float y = event.getY();

		// 사각형을 터치했을 경우
		if (x >= rec[touchNum].left && x <= rec[touchNum].right && y >= rec[touchNum].top
				&& y <= rec[touchNum].bottom) {
			//			Toast.makeText(getContext(), "Touch", Toast.LENGTH_SHORT).show();
			pre_touchNum = touchNum;
			sp.play(soundID[touchNum], 1, 1,0,0, 1.0f);
			touchNum++;
			
			// 모든 사각형을 터치했을 경우 종료
			if(touchNum == rec.length){
				GameOver();
			}
			
			// 터치성공 10번 이상
			if(touchNum<10){
				motioncount.setText(touchNum+"");
				toast.setGravity(Gravity.TOP|Gravity.CENTER,0 ,0);
				toast.setView(view);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.show();
			}
			// 터치성공 10번 이상 30번 미만
			else if(touchNum>=10&&touchNum<30){
				if(touchNum == 15){ speed +=0.5;
					toast.cancel();
					motioncount.setText("속도업!");
					motioniv.setImageResource(R.drawable.speedup);
					return false;
				}
				toast.setGravity(Gravity.TOP|Gravity.CENTER,0 ,0);
				motioniv.setImageResource(R.drawable.cool);
				motioncount.setText(touchNum+"");
				toast.setView(view);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.show();
			}
			// 터치성공 30번 이상
			else if(touchNum>=30){
				if(touchNum == 30 || touchNum == 50){ speed += 0.5;
				toast.cancel();
				motioncount.setText("속도업!");
				motioniv.setImageResource(R.drawable.speedup);
				return false;
				}
				toast.setGravity(Gravity.TOP|Gravity.CENTER,0 ,0);
				motioniv.setImageResource(R.drawable.good);
				motioncount.setText(touchNum+"");
				toast.setView(view);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		return super.onTouchEvent(event);
	}

	// 호랑이 소리 조각
	int tigerMusic[] = {
		R.raw.tigermusic_00m_00s__00m_01s,
		R.raw.tigermusic_00m_01s__00m_02s,
		R.raw.tigermusic_00m_02s__00m_03s,
		R.raw.tigermusic_00m_03s__00m_04s,
		R.raw.tigermusic_00m_04s__00m_05s,
		R.raw.tigermusic_00m_05s__00m_06s,
		R.raw.tigermusic_00m_06s__00m_07s,
		R.raw.tigermusic_00m_07s__00m_08s,
		R.raw.tigermusic_00m_08s__00m_09s,
		R.raw.tigermusic_00m_09s__00m_10s,
		R.raw.tigermusic_00m_10s__00m_11s,
		R.raw.tigermusic_00m_11s__00m_12s,
		R.raw.tigermusic_00m_12s__00m_13s,
		R.raw.tigermusic_00m_13s__00m_14s,
		R.raw.tigermusic_00m_14s__00m_15s,
		R.raw.tigermusic_00m_15s__00m_16s,
		R.raw.tigermusic_00m_16s__00m_17s,
		R.raw.tigermusic_00m_17s__00m_18s,
		R.raw.tigermusic_00m_18s__00m_19s,
		R.raw.tigermusic_00m_19s__00m_20s,
		R.raw.tigermusic_00m_20s__00m_21s,
		R.raw.tigermusic_00m_21s__00m_22s,
		R.raw.tigermusic_00m_22s__00m_23s,
		R.raw.tigermusic_00m_23s__00m_24s,
		R.raw.tigermusic_00m_24s__00m_25s,
		R.raw.tigermusic_00m_25s__00m_26s,
		R.raw.tigermusic_00m_26s__00m_27s,
		R.raw.tigermusic_00m_27s__00m_28s,
		R.raw.tigermusic_00m_28s__00m_29s,
		R.raw.tigermusic_00m_29s__00m_30s,
		R.raw.tigermusic_00m_30s__00m_31s,
		R.raw.tigermusic_00m_31s__00m_32s,
		R.raw.tigermusic_00m_32s__00m_33s,
		R.raw.tigermusic_00m_33s__00m_34s,
		R.raw.tigermusic_00m_34s__00m_35s,
		R.raw.tigermusic_00m_35s__00m_36s,
		R.raw.tigermusic_00m_36s__00m_37s,
		R.raw.tigermusic_00m_37s__00m_38s,
		R.raw.tigermusic_00m_38s__00m_39s,
		R.raw.tigermusic_00m_39s__00m_40s,
		R.raw.tigermusic_00m_40s__00m_41s,
		R.raw.tigermusic_00m_41s__00m_42s,
		R.raw.tigermusic_00m_42s__00m_43s,
		R.raw.tigermusic_00m_43s__00m_44s,
		R.raw.tigermusic_00m_44s__00m_45s,
		R.raw.tigermusic_00m_45s__00m_46s,
		R.raw.tigermusic_00m_46s__00m_47s,
		R.raw.tigermusic_00m_47s__00m_48s,
		R.raw.tigermusic_00m_48s__00m_49s,
		R.raw.tigermusic_00m_49s__00m_50s,
		R.raw.tigermusic_00m_50s__00m_51s,
		R.raw.tigermusic_00m_51s__00m_52s,
		R.raw.tigermusic_00m_52s__00m_53s,
		R.raw.tigermusic_00m_53s__00m_54s,
		R.raw.tigermusic_00m_54s__00m_55s,
		R.raw.tigermusic_00m_55s__00m_56s,
		R.raw.tigermusic_00m_56s__00m_57s,
		R.raw.tigermusic_00m_57s__00m_58s,
		R.raw.tigermusic_00m_58s__00m_59s,
		R.raw.tigermusic_00m_59s__01m_00s,
		R.raw.tigermusic_01m_00s__01m_01s,
		R.raw.tigermusic_01m_01s__01m_02s,
		R.raw.tigermusic_01m_02s__01m_03s,
		R.raw.tigermusic_01m_03s__01m_04s,
		R.raw.tigermusic_01m_04s__01m_05s,
		R.raw.tigermusic_01m_05s__01m_06s,
		R.raw.tigermusic_01m_06s__01m_07s,
		R.raw.tigermusic_01m_07s__01m_08s,
		R.raw.tigermusic_01m_08s__01m_09s,
		R.raw.tigermusic_01m_09s__01m_10s,
		R.raw.tigermusic_01m_10s__01m_11s,
		R.raw.tigermusic_01m_11s__01m_12s,
		R.raw.tigermusic_01m_12s__01m_13s,
		R.raw.tigermusic_01m_13s__01m_14s,
		R.raw.tigermusic_01m_14s__01m_15s,
		R.raw.tigermusic_01m_15s__01m_16s,
		R.raw.tigermusic_01m_16s__01m_17s,
		R.raw.tigermusic_01m_17s__01m_18s,
		R.raw.tigermusic_01m_18s__01m_19s,
		R.raw.tigermusic_01m_19s__01m_20s,
		R.raw.tigermusic_01m_20s__01m_21s,
		R.raw.tigermusic_01m_21s__01m_22s,
		R.raw.tigermusic_01m_22s__01m_23s,
		R.raw.tigermusic_01m_23s__01m_24s,
		R.raw.tigermusic_01m_24s__01m_25s,
		R.raw.tigermusic_01m_25s__01m_26s,
		R.raw.tigermusic_01m_26s__01m_27s,
		R.raw.tigermusic_01m_27s__01m_28s,
		R.raw.tigermusic_01m_28s__01m_29s,
		R.raw.tigermusic_01m_29s__01m_30s,
		R.raw.tigermusic_01m_30s__01m_31s,
		R.raw.tigermusic_01m_31s__01m_32s,
		R.raw.tigermusic_01m_32s__01m_33s,
		R.raw.tigermusic_01m_33s__01m_34s,
		R.raw.tigermusic_01m_34s__01m_35s,
		R.raw.tigermusic_01m_35s__01m_36s,
		R.raw.tigermusic_01m_36s__01m_37s,
		R.raw.tigermusic_01m_37s__01m_38s,
		R.raw.tigermusic_01m_38s__01m_39s,
		R.raw.tigermusic_01m_39s__01m_40s,
		R.raw.tigermusic_01m_40s__01m_40s_16h,
	};

	// 양 소리 조각
	int sheepMusic[] = {
		R.raw.sheepmusic_00m_00s__00m_01s,
		R.raw.sheepmusic_00m_01s__00m_02s,
		R.raw.sheepmusic_00m_02s__00m_03s,
		R.raw.sheepmusic_00m_03s__00m_04s,
		R.raw.sheepmusic_00m_04s__00m_05s,
		R.raw.sheepmusic_00m_05s__00m_06s,
		R.raw.sheepmusic_00m_06s__00m_07s,
		R.raw.sheepmusic_00m_07s__00m_08s,
		R.raw.sheepmusic_00m_08s__00m_09s,
		R.raw.sheepmusic_00m_09s__00m_10s,
		R.raw.sheepmusic_00m_10s__00m_11s,
		R.raw.sheepmusic_00m_11s__00m_12s,
		R.raw.sheepmusic_00m_12s__00m_13s,
		R.raw.sheepmusic_00m_13s__00m_14s,
		R.raw.sheepmusic_00m_14s__00m_15s,
		R.raw.sheepmusic_00m_15s__00m_16s,
		R.raw.sheepmusic_00m_16s__00m_17s,
		R.raw.sheepmusic_00m_17s__00m_18s,
		R.raw.sheepmusic_00m_18s__00m_19s,
		R.raw.sheepmusic_00m_19s__00m_20s,
		R.raw.sheepmusic_00m_20s__00m_21s,
		R.raw.sheepmusic_00m_21s__00m_22s,
		R.raw.sheepmusic_00m_22s__00m_23s,
		R.raw.sheepmusic_00m_23s__00m_24s,
		R.raw.sheepmusic_00m_24s__00m_25s,
		R.raw.sheepmusic_00m_25s__00m_26s,
		R.raw.sheepmusic_00m_26s__00m_27s,
		R.raw.sheepmusic_00m_27s__00m_28s,
		R.raw.sheepmusic_00m_28s__00m_29s,
		R.raw.sheepmusic_00m_29s__00m_30s,
		R.raw.sheepmusic_00m_30s__00m_31s,
		R.raw.sheepmusic_00m_31s__00m_32s,
		R.raw.sheepmusic_00m_32s__00m_33s,
		R.raw.sheepmusic_00m_33s__00m_34s,
		R.raw.sheepmusic_00m_34s__00m_35s,
		R.raw.sheepmusic_00m_35s__00m_36s,
		R.raw.sheepmusic_00m_36s__00m_37s,
		R.raw.sheepmusic_00m_37s__00m_38s,
		R.raw.sheepmusic_00m_38s__00m_39s,
		R.raw.sheepmusic_00m_39s__00m_40s,
		R.raw.sheepmusic_00m_40s__00m_41s,
		R.raw.sheepmusic_00m_41s__00m_42s,
		R.raw.sheepmusic_00m_42s__00m_43s,
		R.raw.sheepmusic_00m_43s__00m_44s,
		R.raw.sheepmusic_00m_44s__00m_45s,
		R.raw.sheepmusic_00m_45s__00m_46s,
		R.raw.sheepmusic_00m_46s__00m_47s,
		R.raw.sheepmusic_00m_47s__00m_48s,
		R.raw.sheepmusic_00m_48s__00m_49s,
		R.raw.sheepmusic_00m_49s__00m_50s,
		R.raw.sheepmusic_00m_50s__00m_51s,
		R.raw.sheepmusic_00m_51s__00m_52s,
		R.raw.sheepmusic_00m_52s__00m_53s,
		R.raw.sheepmusic_00m_53s__00m_54s,
		R.raw.sheepmusic_00m_54s__00m_55s,
		R.raw.sheepmusic_00m_55s__00m_56s,
		R.raw.sheepmusic_00m_56s__00m_57s,
		R.raw.sheepmusic_00m_57s__00m_58s,
		R.raw.sheepmusic_00m_58s__00m_59s,
		R.raw.sheepmusic_00m_59s__01m_00s,
		R.raw.sheepmusic_01m_00s__01m_01s,
		R.raw.sheepmusic_01m_01s__01m_02s,
		R.raw.sheepmusic_01m_02s__01m_03s,
		R.raw.sheepmusic_01m_03s__01m_04s,
		R.raw.sheepmusic_01m_04s__01m_05s,
		R.raw.sheepmusic_01m_05s__01m_06s,
		R.raw.sheepmusic_01m_06s__01m_07s,
		R.raw.sheepmusic_01m_07s__01m_08s,
		R.raw.sheepmusic_01m_08s__01m_09s,
		R.raw.sheepmusic_01m_09s__01m_10s,
		R.raw.sheepmusic_01m_10s__01m_11s,
		R.raw.sheepmusic_01m_11s__01m_12s,
		R.raw.sheepmusic_01m_12s__01m_13s,
		R.raw.sheepmusic_01m_13s__01m_14s,
		R.raw.sheepmusic_01m_14s__01m_15s,
		R.raw.sheepmusic_01m_15s__01m_16s,
		R.raw.sheepmusic_01m_16s__01m_17s,
		R.raw.sheepmusic_01m_17s__01m_18s,
		R.raw.sheepmusic_01m_18s__01m_19s,
		R.raw.sheepmusic_01m_19s__01m_20s,
		R.raw.sheepmusic_01m_20s__01m_21s,
		R.raw.sheepmusic_01m_21s__01m_22s,
		R.raw.sheepmusic_01m_22s__01m_23s,
		R.raw.sheepmusic_01m_23s__01m_24s,
		R.raw.sheepmusic_01m_24s__01m_25s,
		R.raw.sheepmusic_01m_25s__01m_26s,
		R.raw.sheepmusic_01m_26s__01m_27s,
		R.raw.sheepmusic_01m_27s__01m_28s,
		R.raw.sheepmusic_01m_28s__01m_29s,
		R.raw.sheepmusic_01m_29s__01m_30s,
		R.raw.sheepmusic_01m_30s__01m_31s,
		R.raw.sheepmusic_01m_31s__01m_31s_67h,
	};
	
	// 원숭이 소리 조각
	int monkeyMusic[] = {
		R.raw.monkeymusic_00m_00s__00m_01s,
		R.raw.monkeymusic_00m_01s__00m_02s,
		R.raw.monkeymusic_00m_02s__00m_03s,
		R.raw.monkeymusic_00m_03s__00m_04s,
		R.raw.monkeymusic_00m_04s__00m_05s,
		R.raw.monkeymusic_00m_05s__00m_06s,
		R.raw.monkeymusic_00m_06s__00m_07s,
		R.raw.monkeymusic_00m_07s__00m_08s,
		R.raw.monkeymusic_00m_08s__00m_09s,
		R.raw.monkeymusic_00m_09s__00m_10s,
		R.raw.monkeymusic_00m_10s__00m_11s,
		R.raw.monkeymusic_00m_11s__00m_12s,
		R.raw.monkeymusic_00m_12s__00m_13s,
		R.raw.monkeymusic_00m_13s__00m_14s,
		R.raw.monkeymusic_00m_14s__00m_15s,
		R.raw.monkeymusic_00m_15s__00m_16s,
		R.raw.monkeymusic_00m_16s__00m_17s,
		R.raw.monkeymusic_00m_17s__00m_18s,
		R.raw.monkeymusic_00m_18s__00m_19s,
		R.raw.monkeymusic_00m_19s__00m_20s,
		R.raw.monkeymusic_00m_20s__00m_21s,
		R.raw.monkeymusic_00m_21s__00m_22s,
		R.raw.monkeymusic_00m_22s__00m_23s,
		R.raw.monkeymusic_00m_23s__00m_24s,
		R.raw.monkeymusic_00m_24s__00m_25s,
		R.raw.monkeymusic_00m_25s__00m_26s,
		R.raw.monkeymusic_00m_26s__00m_27s,
		R.raw.monkeymusic_00m_27s__00m_28s,
		R.raw.monkeymusic_00m_28s__00m_29s,
		R.raw.monkeymusic_00m_29s__00m_30s,
		R.raw.monkeymusic_00m_30s__00m_31s,
		R.raw.monkeymusic_00m_31s__00m_32s,
		R.raw.monkeymusic_00m_32s__00m_33s,
		R.raw.monkeymusic_00m_33s__00m_34s,
		R.raw.monkeymusic_00m_34s__00m_35s,
		R.raw.monkeymusic_00m_35s__00m_36s,
		R.raw.monkeymusic_00m_36s__00m_37s,
		R.raw.monkeymusic_00m_37s__00m_38s,
		R.raw.monkeymusic_00m_38s__00m_39s,
		R.raw.monkeymusic_00m_39s__00m_40s,
		R.raw.monkeymusic_00m_40s__00m_41s,
		R.raw.monkeymusic_00m_41s__00m_42s,
		R.raw.monkeymusic_00m_42s__00m_43s,
		R.raw.monkeymusic_00m_43s__00m_44s,
		R.raw.monkeymusic_00m_44s__00m_45s,
		R.raw.monkeymusic_00m_45s__00m_46s,
		R.raw.monkeymusic_00m_46s__00m_47s,
		R.raw.monkeymusic_00m_47s__00m_48s,
		R.raw.monkeymusic_00m_48s__00m_49s,
		R.raw.monkeymusic_00m_49s__00m_50s,
		R.raw.monkeymusic_00m_50s__00m_51s,
		R.raw.monkeymusic_00m_51s__00m_52s,
		R.raw.monkeymusic_00m_52s__00m_53s,
		R.raw.monkeymusic_00m_53s__00m_54s,
		R.raw.monkeymusic_00m_54s__00m_55s,
		R.raw.monkeymusic_00m_55s__00m_56s,
		R.raw.monkeymusic_00m_56s__00m_57s,
		R.raw.monkeymusic_00m_57s__00m_58s,
		R.raw.monkeymusic_00m_58s__00m_59s,
		R.raw.monkeymusic_00m_59s__01m_00s,
		R.raw.monkeymusic_01m_00s__01m_01s,
		R.raw.monkeymusic_01m_01s__01m_02s,
		R.raw.monkeymusic_01m_02s__01m_03s,
		R.raw.monkeymusic_01m_03s__01m_04s,
		R.raw.monkeymusic_01m_04s__01m_05s,
		R.raw.monkeymusic_01m_05s__01m_06s,
		R.raw.monkeymusic_01m_06s__01m_07s,
		R.raw.monkeymusic_01m_07s__01m_08s,
		R.raw.monkeymusic_01m_08s__01m_08s_31h,
	};

	// 사자 소리 조각
	int lionMusic[] = {
		R.raw.lionmusic_00m_00s__00m_01s,
		R.raw.lionmusic_00m_01s__00m_02s,
		R.raw.lionmusic_00m_02s__00m_03s,
		R.raw.lionmusic_00m_03s__00m_04s,
		R.raw.lionmusic_00m_04s__00m_05s,
		R.raw.lionmusic_00m_05s__00m_06s,
		R.raw.lionmusic_00m_06s__00m_07s,
		R.raw.lionmusic_00m_07s__00m_08s,
		R.raw.lionmusic_00m_08s__00m_09s,
		R.raw.lionmusic_00m_09s__00m_10s,
		R.raw.lionmusic_00m_10s__00m_11s,
		R.raw.lionmusic_00m_11s__00m_12s,
		R.raw.lionmusic_00m_12s__00m_13s,
		R.raw.lionmusic_00m_13s__00m_14s,
		R.raw.lionmusic_00m_14s__00m_15s,
		R.raw.lionmusic_00m_15s__00m_16s,
		R.raw.lionmusic_00m_16s__00m_17s,
		R.raw.lionmusic_00m_17s__00m_18s,
		R.raw.lionmusic_00m_18s__00m_19s,
		R.raw.lionmusic_00m_19s__00m_20s,
		R.raw.lionmusic_00m_20s__00m_21s,
		R.raw.lionmusic_00m_21s__00m_22s,
		R.raw.lionmusic_00m_22s__00m_23s,
		R.raw.lionmusic_00m_23s__00m_24s,
		R.raw.lionmusic_00m_24s__00m_25s,
		R.raw.lionmusic_00m_25s__00m_26s,
		R.raw.lionmusic_00m_26s__00m_27s,
		R.raw.lionmusic_00m_27s__00m_28s,
		R.raw.lionmusic_00m_28s__00m_29s,
		R.raw.lionmusic_00m_29s__00m_30s,
		R.raw.lionmusic_00m_30s__00m_31s,
		R.raw.lionmusic_00m_31s__00m_32s,
		R.raw.lionmusic_00m_32s__00m_33s,
		R.raw.lionmusic_00m_33s__00m_34s,
		R.raw.lionmusic_00m_34s__00m_35s,
		R.raw.lionmusic_00m_35s__00m_36s,
		R.raw.lionmusic_00m_36s__00m_37s,
		R.raw.lionmusic_00m_37s__00m_38s,
		R.raw.lionmusic_00m_38s__00m_39s,
		R.raw.lionmusic_00m_39s__00m_40s,
		R.raw.lionmusic_00m_40s__00m_41s,
		R.raw.lionmusic_00m_41s__00m_42s,
		R.raw.lionmusic_00m_42s__00m_43s,
		R.raw.lionmusic_00m_43s__00m_44s,
		R.raw.lionmusic_00m_44s__00m_45s,
		R.raw.lionmusic_00m_45s__00m_46s,
		R.raw.lionmusic_00m_46s__00m_47s,
		R.raw.lionmusic_00m_47s__00m_48s,
		R.raw.lionmusic_00m_48s__00m_49s,
		R.raw.lionmusic_00m_49s__00m_50s,
		R.raw.lionmusic_00m_50s__00m_51s,
		R.raw.lionmusic_00m_51s__00m_52s,
		R.raw.lionmusic_00m_52s__00m_53s,
		R.raw.lionmusic_00m_53s__00m_54s,
		R.raw.lionmusic_00m_54s__00m_55s,
		R.raw.lionmusic_00m_55s__00m_56s,
		R.raw.lionmusic_00m_56s__00m_57s,
		R.raw.lionmusic_00m_57s__00m_58s,
		R.raw.lionmusic_00m_58s__00m_59s,
		R.raw.lionmusic_00m_59s__01m_00s,
		R.raw.lionmusic_01m_00s__01m_01s,
		R.raw.lionmusic_01m_01s__01m_02s,
		R.raw.lionmusic_01m_02s__01m_03s,
		R.raw.lionmusic_01m_03s__01m_04s,
		R.raw.lionmusic_01m_04s__01m_05s,
		R.raw.lionmusic_01m_05s__01m_06s,
		R.raw.lionmusic_01m_06s__01m_07s,
		R.raw.lionmusic_01m_07s__01m_08s,
		R.raw.lionmusic_01m_08s__01m_09s,
		R.raw.lionmusic_01m_09s__01m_10s,
		R.raw.lionmusic_01m_10s__01m_11s,
		R.raw.lionmusic_01m_11s__01m_12s,
		R.raw.lionmusic_01m_12s__01m_13s,
		R.raw.lionmusic_01m_13s__01m_14s,
		R.raw.lionmusic_01m_14s__01m_15s,
		R.raw.lionmusic_01m_15s__01m_16s,
		R.raw.lionmusic_01m_16s__01m_17s,
		R.raw.lionmusic_01m_17s__01m_18s,
		R.raw.lionmusic_01m_18s__01m_19s,
		R.raw.lionmusic_01m_19s__01m_20s,
		R.raw.lionmusic_01m_20s__01m_21s,
		R.raw.lionmusic_01m_21s__01m_22s,
		R.raw.lionmusic_01m_22s__01m_23s,
		R.raw.lionmusic_01m_23s__01m_24s,
		R.raw.lionmusic_01m_24s__01m_25s,
		R.raw.lionmusic_01m_25s__01m_26s,
		R.raw.lionmusic_01m_26s__01m_27s,
		R.raw.lionmusic_01m_27s__01m_28s,
		R.raw.lionmusic_01m_28s__01m_29s,
		R.raw.lionmusic_01m_29s__01m_30s,
		R.raw.lionmusic_01m_30s__01m_31s,
		R.raw.lionmusic_01m_31s__01m_32s,
		R.raw.lionmusic_01m_32s__01m_33s,
		R.raw.lionmusic_01m_33s__01m_34s,
		R.raw.lionmusic_01m_34s__01m_35s,
		R.raw.lionmusic_01m_35s__01m_36s,
		R.raw.lionmusic_01m_36s__01m_37s,
		R.raw.lionmusic_01m_37s__01m_38s,
		R.raw.lionmusic_01m_38s__01m_39s,
		R.raw.lionmusic_01m_39s__01m_40s,
		R.raw.lionmusic_01m_40s__01m_41s,
		R.raw.lionmusic_01m_41s__01m_42s,
		R.raw.lionmusic_01m_42s__01m_43s,
		R.raw.lionmusic_01m_43s__01m_44s,
		R.raw.lionmusic_01m_44s__01m_45s,
		R.raw.lionmusic_01m_45s__01m_46s,
		R.raw.lionmusic_01m_46s__01m_47s,
		R.raw.lionmusic_01m_47s__01m_48s,
		R.raw.lionmusic_01m_48s__01m_48s_28h,
	};

	// 코끼리 소리 조각
	int elephantMusic[] = {
		R.raw.elephantmusic_00m_00s__00m_01s,
		R.raw.elephantmusic_00m_01s__00m_02s,
		R.raw.elephantmusic_00m_02s__00m_03s,
		R.raw.elephantmusic_00m_03s__00m_04s,
		R.raw.elephantmusic_00m_04s__00m_05s,
		R.raw.elephantmusic_00m_05s__00m_06s,
		R.raw.elephantmusic_00m_06s__00m_07s,
		R.raw.elephantmusic_00m_07s__00m_08s,
		R.raw.elephantmusic_00m_08s__00m_09s,
		R.raw.elephantmusic_00m_09s__00m_10s,
		R.raw.elephantmusic_00m_10s__00m_11s,
		R.raw.elephantmusic_00m_11s__00m_12s,
		R.raw.elephantmusic_00m_12s__00m_13s,
		R.raw.elephantmusic_00m_13s__00m_14s,
		R.raw.elephantmusic_00m_14s__00m_15s,
		R.raw.elephantmusic_00m_15s__00m_16s,
		R.raw.elephantmusic_00m_16s__00m_17s,
		R.raw.elephantmusic_00m_17s__00m_18s,
		R.raw.elephantmusic_00m_18s__00m_19s,
		R.raw.elephantmusic_00m_19s__00m_20s,
		R.raw.elephantmusic_00m_20s__00m_21s,
		R.raw.elephantmusic_00m_21s__00m_22s,
		R.raw.elephantmusic_00m_22s__00m_23s,
		R.raw.elephantmusic_00m_23s__00m_24s,
		R.raw.elephantmusic_00m_24s__00m_25s,
		R.raw.elephantmusic_00m_25s__00m_26s,
		R.raw.elephantmusic_00m_26s__00m_27s,
		R.raw.elephantmusic_00m_27s__00m_28s,
		R.raw.elephantmusic_00m_28s__00m_29s,
		R.raw.elephantmusic_00m_29s__00m_30s,
		R.raw.elephantmusic_00m_30s__00m_31s,
		R.raw.elephantmusic_00m_31s__00m_32s,
		R.raw.elephantmusic_00m_32s__00m_33s,
		R.raw.elephantmusic_00m_33s__00m_34s,
		R.raw.elephantmusic_00m_34s__00m_35s,
		R.raw.elephantmusic_00m_35s__00m_36s,
		R.raw.elephantmusic_00m_36s__00m_37s,
		R.raw.elephantmusic_00m_37s__00m_38s,
		R.raw.elephantmusic_00m_38s__00m_39s,
		R.raw.elephantmusic_00m_39s__00m_40s,
		R.raw.elephantmusic_00m_40s__00m_41s,
		R.raw.elephantmusic_00m_41s__00m_42s,
		R.raw.elephantmusic_00m_42s__00m_43s,
		R.raw.elephantmusic_00m_43s__00m_44s,
		R.raw.elephantmusic_00m_44s__00m_45s,
		R.raw.elephantmusic_00m_45s__00m_46s,
		R.raw.elephantmusic_00m_46s__00m_47s,
		R.raw.elephantmusic_00m_47s__00m_47s_96h,
	};

	// 소 소리 조각
	int cowMusic[] = {
		R.raw.cowmusic_00m_00s__00m_01s,
		R.raw.cowmusic_00m_01s__00m_02s,
		R.raw.cowmusic_00m_02s__00m_03s,
		R.raw.cowmusic_00m_03s__00m_04s,
		R.raw.cowmusic_00m_04s__00m_05s,
		R.raw.cowmusic_00m_05s__00m_06s,
		R.raw.cowmusic_00m_06s__00m_07s,
		R.raw.cowmusic_00m_07s__00m_08s,
		R.raw.cowmusic_00m_08s__00m_09s,
		R.raw.cowmusic_00m_09s__00m_10s,
		R.raw.cowmusic_00m_10s__00m_11s,
		R.raw.cowmusic_00m_11s__00m_12s,
		R.raw.cowmusic_00m_12s__00m_13s,
		R.raw.cowmusic_00m_13s__00m_14s,
		R.raw.cowmusic_00m_14s__00m_15s,
		R.raw.cowmusic_00m_15s__00m_16s,
		R.raw.cowmusic_00m_16s__00m_17s,
		R.raw.cowmusic_00m_17s__00m_18s,
		R.raw.cowmusic_00m_18s__00m_19s,
		R.raw.cowmusic_00m_19s__00m_20s,
		R.raw.cowmusic_00m_20s__00m_21s,
		R.raw.cowmusic_00m_21s__00m_22s,
		R.raw.cowmusic_00m_22s__00m_23s,
		R.raw.cowmusic_00m_23s__00m_24s,
		R.raw.cowmusic_00m_24s__00m_25s,
		R.raw.cowmusic_00m_25s__00m_26s,
		R.raw.cowmusic_00m_26s__00m_27s,
		R.raw.cowmusic_00m_27s__00m_28s,
		R.raw.cowmusic_00m_28s__00m_29s,
		R.raw.cowmusic_00m_29s__00m_30s,
		R.raw.cowmusic_00m_30s__00m_31s,
		R.raw.cowmusic_00m_31s__00m_32s,
		R.raw.cowmusic_00m_32s__00m_33s,
		R.raw.cowmusic_00m_33s__00m_34s,
		R.raw.cowmusic_00m_34s__00m_35s,
		R.raw.cowmusic_00m_35s__00m_36s,
		R.raw.cowmusic_00m_36s__00m_37s,
		R.raw.cowmusic_00m_37s__00m_38s,
		R.raw.cowmusic_00m_38s__00m_39s,
		R.raw.cowmusic_00m_39s__00m_40s,
		R.raw.cowmusic_00m_40s__00m_41s,
		R.raw.cowmusic_00m_41s__00m_42s,
		R.raw.cowmusic_00m_42s__00m_43s,
		R.raw.cowmusic_00m_43s__00m_44s,
		R.raw.cowmusic_00m_44s__00m_45s,
		R.raw.cowmusic_00m_45s__00m_46s,
		R.raw.cowmusic_00m_46s__00m_47s,
		R.raw.cowmusic_00m_47s__00m_48s,
		R.raw.cowmusic_00m_48s__00m_49s,
		R.raw.cowmusic_00m_49s__00m_50s,
		R.raw.cowmusic_00m_50s__00m_51s,
		R.raw.cowmusic_00m_51s__00m_52s,
		R.raw.cowmusic_00m_52s__00m_53s,
		R.raw.cowmusic_00m_53s__00m_54s,
		R.raw.cowmusic_00m_54s__00m_55s,
		R.raw.cowmusic_00m_55s__00m_56s,
		R.raw.cowmusic_00m_56s__00m_57s,
		R.raw.cowmusic_00m_57s__00m_58s,
		R.raw.cowmusic_00m_58s__00m_59s,
		R.raw.cowmusic_00m_59s__01m_00s,
		R.raw.cowmusic_01m_00s__01m_01s,
		R.raw.cowmusic_01m_01s__01m_02s,
		R.raw.cowmusic_01m_02s__01m_03s,
		R.raw.cowmusic_01m_03s__01m_04s,
		R.raw.cowmusic_01m_04s__01m_05s,
		R.raw.cowmusic_01m_05s__01m_06s,
		R.raw.cowmusic_01m_06s__01m_07s,
		R.raw.cowmusic_01m_07s__01m_08s,
		R.raw.cowmusic_01m_08s__01m_09s,
		R.raw.cowmusic_01m_09s__01m_10s,
		R.raw.cowmusic_01m_10s__01m_11s,
		R.raw.cowmusic_01m_11s__01m_12s,
		R.raw.cowmusic_01m_12s__01m_13s,
		R.raw.cowmusic_01m_13s__01m_14s,
		R.raw.cowmusic_01m_14s__01m_15s,
		R.raw.cowmusic_01m_15s__01m_16s,
		R.raw.cowmusic_01m_16s__01m_17s,
		R.raw.cowmusic_01m_17s__01m_18s,
		R.raw.cowmusic_01m_18s__01m_19s,
		R.raw.cowmusic_01m_19s__01m_20s,
		R.raw.cowmusic_01m_20s__01m_21s,
		R.raw.cowmusic_01m_21s__01m_22s,
		R.raw.cowmusic_01m_22s__01m_23s,
		R.raw.cowmusic_01m_23s__01m_24s,
		R.raw.cowmusic_01m_24s__01m_25s,
		R.raw.cowmusic_01m_25s__01m_26s,
		R.raw.cowmusic_01m_26s__01m_27s,
		R.raw.cowmusic_01m_27s__01m_27s_5h
	};
} // end of class MySurfaceView