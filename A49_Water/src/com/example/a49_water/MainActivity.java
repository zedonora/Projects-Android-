package com.example.a49_water;

import java.util.LinkedList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	Random ran = new Random(); // 랜덤
	Handler handler = new Handler(); // 핸들러
	// Handler handler2 = new Handler(); // 핸들러
	Point point; // 현재 위치 좌표
	LinkedList<Water> alImage = new LinkedList<Water>(); // 각 풍선에 대한 리스트
	RelativeLayout ll; // 메인 레이아웃 객체
	final int MAX = 20; // 최대 20개 풍선
	// 다양한 풍선 종류
	int img[] = { R.drawable.colorboonhong, R.drawable.colorbora, R.drawable.colorgreen, R.drawable.colorjuhwang,
			R.drawable.colorred, R.drawable.coloryellow, R.drawable.colorking };
	// 캐릭터 종류
	int imgChar[] = { R.drawable.char1, R.drawable.char2, R.drawable.char3, R.drawable.char4, R.drawable.char5 };
	int count = 1; // 풍선 맞춘 카운트 변수
	SoundPool sp; // 효과음
	MediaPlayer mp, mpMission; // 배경음
	boolean isX = true; // 좌우
	float moveX; // 좌우
	boolean isRotate = true; // 회전
	float rotate; // 회전
	int timer, n;
	TextView timeTV;
	Intent intent;

	Runnable r = new Runnable() {
		@Override
		public void run() {

			for (int i = 0; i < alImage.size(); i++) {

				if (alImage.get(i).iv.getY() < point.y - 100) {

					alImage.get(i).iv.setY(alImage.get(i).iv.getY() + alImage.get(i).speed);
					alImage.get(i).iv.setRotation(rotate); // 물방울 회전
					alImage.get(i).iv.setTranslationX(alImage.get(i).iv.getX() + moveX); // 물방울
																							// 이동

				} else {

					if (alImage.size() < MAX) {
						alImage.add(new Water(new ImageView(getApplicationContext())));
						init(alImage.size() - 1);
					}

					alImage.get(i).iv.setBackgroundResource(img[ran.nextInt(img.length)]);
					alImage.get(i).iv.setX(ran.nextInt(point.x));
					alImage.get(i).iv.setY(0);
					alImage.get(i).speed = ran.nextInt(5) + 1;
					alImage.get(i).imgNum = 0;
				}
			} // end of for

			// 회전
			if (isRotate) {
				rotate += 1;
				if (rotate >= 360) {
					isRotate = false;
				}
			} else {
				rotate -= 1;
				if (rotate <= 0) {
					isRotate = true;
				}
			}

			// 좌우
			if (timer > 600) {
				if (isX) {
					moveX += 0.1;
					if (moveX >= 4) {
						isX = false;
					}
				} else {
					moveX -= 0.1;
					if (moveX <= -4) {
						isX = true;
					}
				}
			}

			if (mp != null && !mp.isPlaying() && !mpMission.isPlaying()) {
				mp.start();
				mpMission = null;
			}

			int ms = timer % 60;
			int sec = timer / 60;
			int min = sec / 60;
			String strTime = String.format("%02d : %02d : %02d", min, sec % 60, ms);
			timeTV.setText(strTime);

			timer++;

			handler.postDelayed(r, 10);
		} // end of run
	}; // end of Runnable

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 화면 크기 얻기
		Display display = getWindowManager().getDefaultDisplay();
		point = new Point();
		display.getSize(point);

		// 레이아웃 객체 선언
		ll = (RelativeLayout) findViewById(R.id.mainLayout);
		// 초기 물방울 설정
		setting();
		// 풍선 생성 및 초기화 - 좌표 , 스피드 지정
		for (int i = 0; i < alImage.size(); i++) {
			init(i);
		}
		handler.post(r);

		// 소리 기본설정
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		final int buble = sp.load(this, R.raw.buble, 1);

		// 배경음악 기본설정
		mp = MediaPlayer.create(this, R.raw.lovehouse);
		mp.setLooping(true);
		mp.start();

		// 맞춘 풍선 갯수 보여주는 텍스트뷰
		final TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText("맞춘 갯수 : " + count);

		// 시간 텍스트뷰
		timeTV = (TextView) findViewById(R.id.textView2);

		// 터치 리스너
		ll.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN // 터치 한번 했을때
						|| event.getAction() == MotionEvent.ACTION_MOVE) { // 드래그
																			// 했을때

					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						sp.play(buble, 1, 1, 0, 0, 1); // 터치 시 음향소리
					}

					for (int i = 0; i < alImage.size(); i++) {

						float imgX = alImage.get(i).iv.getX();
						float imgY = alImage.get(i).iv.getY();

						if (event.getX() + 30 > imgX && event.getX() - 70 < imgX && event.getY() + 30 > imgY
								&& event.getY() - 70 < imgY) {

							sp.play(buble, 1, 1, 0, 0, 1); // 맞추는 음향소리

							if (alImage.get(i).imgNum == 0) {
								tv.setText("맞춘 갯수 : " + count++);

								if (count % 13 == 0) { // 랜덤으로 다른 케릭터 이미지
									int temp = (int) (Math.random() * imgChar.length);
									alImage.get(i).iv.setBackgroundResource(imgChar[temp]);
									alImage.get(i).imgNum = imgChar[temp];

									mp.pause();

									if (mpMission == null) {
										mpMission = MediaPlayer.create(getApplicationContext(), R.raw.mission);
										mpMission.start();
									}
								} else if (count % 15 == 0) { // 랜덤으로 다른 케릭터 이미지
									int temp = (int) (Math.random() * imgChar.length);
									alImage.get(i).iv.setBackgroundResource(imgChar[temp]);
									alImage.get(i).imgNum = imgChar[temp];
								} else { // 그냥 뽀로로
									alImage.get(i).iv.setBackgroundResource(R.drawable.char0);
									alImage.get(i).imgNum = R.drawable.char0;
								}
							}
							break;
						}
					} // end of for

					if (count >= 60) {
						handler.removeCallbacks(r);
						if (intent == null) {
							intent = new Intent(getApplicationContext(), Web.class);
							startActivity(intent);
							finish();
						}
					}
				} // end of if - TouchDown

				return true;
			} // end of onTouch
		}); // end of setOnTouchListener

	} // end of onCreate

	// 초기 풍선 물량
	public void setting() {
		alImage.add(new Water(new ImageView(this)));
		alImage.add(new Water(new ImageView(this)));
		alImage.add(new Water(new ImageView(this)));
		alImage.add(new Water(new ImageView(this)));
		alImage.add(new Water(new ImageView(this)));
		alImage.add(new Water(new ImageView(this)));
		alImage.add(new Water(new ImageView(this)));
		alImage.add(new Water(new ImageView(this)));
	} // end of setting

	// 풍선 생성 메서드
	public void init(int i) {
		alImage.get(i).iv.setLayoutParams(
				new RelativeLayout.LayoutParams((int) (Math.random() * 50 + 90), (int) (Math.random() * 50 + 90))); // 사진크기
		alImage.get(i).iv.setBackgroundResource(img[ran.nextInt(img.length)]); // 풍선
																				// 랜덤
		alImage.get(i).iv.setX(ran.nextInt(point.x)); // x좌표 랜덤
		alImage.get(i).iv.setY(0); // y좌표
		alImage.get(i).speed = ran.nextInt(5) + 1; // 스피드 랜덤
		ll.addView(alImage.get(i).iv); // 메인 레이아웃(릴레티브)에 올리기
	} // end of init

	@Override
	protected void onPause() {
		super.onPause();

		if (mp != null) {
			mp.stop();
		}
		if (mpMission != null) {
			mpMission.stop();
		}
		handler.removeMessages(0);
	} // end of onPause

} // end of class

// 이미지와 속도를 가진 클래스
class Water {
	ImageView iv;
	int speed;
	int imgNum;

	public Water(ImageView iv) {
		super();
		this.iv = iv;
	}
} // end of class