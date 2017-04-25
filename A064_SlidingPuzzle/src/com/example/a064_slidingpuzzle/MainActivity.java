package com.example.a064_slidingpuzzle;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener {
	ImageView iv[] = new ImageView[16];
	/** 이미지 뷰에 몇번째 img의 이미지가 들어있는지 index 를 저장
	 * 
	 */
	int index[] = new int[iv.length];
	int img[] = {R.drawable.img0,R.drawable.img1,R.drawable.img2,R.drawable.img3
			,R.drawable.img4,R.drawable.img5,R.drawable.img6,R.drawable.img7
			,R.drawable.img8,R.drawable.img9,R.drawable.img10,R.drawable.img11
			,R.drawable.img12,R.drawable.img13,R.drawable.img14,R.drawable.ic_launcher
	};
	int indexImgBlank = iv.length-1 ; // 빈칸의  위치
	Random ran = new Random(); // 랜덤으로 섞을 때 사용
	TextView tvMoveCount;
	TextView tvMessage;
	Button bShuffle;
	Button bStart;
	Button bHint;
	int moveCount; // 이동회수
	SoundPool sp;
	Vibrator vibrator;
	int streamID;
	MediaPlayer mp;
	TextView tvTimer;
	Chronometer chronometer;
	final int TIME_OUT = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myFindViewById();
		for (int i = 0; i < iv.length; i++) {
			index[i] = i; // 이미지뷰가 가질 이미지의 index
		}
		// 이미지 버튼의 이미지 초기화
		for (int i = 0; i < iv.length; i++) {
			iv[i].setImageResource(img[index[i]]); 
			iv[i].setTag(i);
			iv[i].setOnTouchListener(this); // 내 클래스에서 이벤트를 처리함
		}
		
		ivEnable(false); // 이미지뷰를 클릭해서 옮기지 못하게 비활성화
		bShuffle.setEnabled(false); // 버튼 비활성화
		bHint.setEnabled(false);
		
		chronometer.setOnChronometerTickListener(new OnChronometerTickListener() {
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				// 크로노미터 값이 변경되었을 때 호출되는 콜백 메서드
				long time = SystemClock.elapsedRealtime() - chronometer.getBase();
				if(time/1000 >= TIME_OUT) {
					// 게임종료
					ivEnable(false); // 이미지뷰를 클릭해서 옮기지 못하게 비활성화
					bStart.setText("게임시작");
					bShuffle.setEnabled(false);
					bHint.setEnabled(false);
					tvMessage.setText("실패 ㅠㅠ 게임을 포기하셨습니다."
							+"\n다시 도전해 보세요\n");
					mp.release();
					chronometer.stop();
				}
			}
		});
		bStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 게임시작 버튼을 클릭했을 때
				Button b = (Button) v;
				if("게임시작".equals(b.getText().toString())){ // 게임시작하기
					mp = MediaPlayer.create(getApplicationContext(), R.raw.bgm);
					mp.start(); // 배경음악 시작
					ivEnable(true); // 이미지뷰 활성화
					bShuffle.setEnabled(true); // 버튼 활성화
					bHint.setEnabled(true);
					b.setText("게임종료"); // 버튼의 글자 변경
					shuffle(); // 이미지 순서 섞기
					
					// 이동회수 초기화
					moveCount = 0;
					tvMoveCount.setText("이동회수: "+moveCount); // TextView 에 출력
					tvMessage.setText("이미지를 클릭해서 순서를 바꿔보세요"
							+ "\n정답보기 버튼을 누르는 동안 \n원래 이미지를 잠시 볼수 있어요"); // 상세설명
					chronometer.setBase(SystemClock.elapsedRealtime()); // 초기화
					chronometer.start();
				} else { // 게임 종료
					ivEnable(false); // 이미지뷰를 클릭해서 옮기지 못하게 비활성화
					b.setText("게임시작");
					bShuffle.setEnabled(false);
					bHint.setEnabled(false);
					tvMessage.setText("실패 ㅠㅠ 게임을 포기하셨습니다."
							+"\n다시 도전해 보세요\n");
					mp.release();
					chronometer.stop();
				}
			}
		});
		bShuffle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { // 순서 섞기 버튼을 클릭할 경우
				shuffle();
			}
		});
		bHint.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) { // 정답보기 터치시
				if (MotionEvent.ACTION_DOWN == event.getAction()) { // down 손을 댔을 때
					// 정답화면 보여주기
					for (int i = 0; i < iv.length; i++) {
						iv[i].setImageResource(img[i]);
					}
					ivEnable(false); // 이미지뷰 비활성화
					tvMessage.setText("이미지를 기억해 두세요\n이렇게 배치하시면 돼요\n");
				} else if (MotionEvent.ACTION_UP == event.getAction()) { // up 손을 뗐을 때
					// index배열의 값 대로 imageView에 이밎 표현
					for (int i = 0; i < iv.length; i++) {
						iv[i].setImageResource(img[index[i]]);
					}
					ivEnable(true); // 이미지뷰 활성화
					tvMessage.setText("이미지를 클릭해서 순서를 바꿔보세요"
							+ "\n정답보기 버튼을 누르는 동안 \n원래 이미지를 잠시 볼수 있어요"); // 상세설명
				}
				return false;
			}
		});
	} // end of onCreate
	/** ImageView 16개를 활성화/비활성화
	 * 
	 */
	public void ivEnable(boolean b) {
		for (int i = 0; i < iv.length; i++) {
			iv[i].setEnabled(b);
		}
	}
	public void shuffle() {
		// shuffle 순서 섞기
		for (int i = 0; i < iv.length * 2; i++) {
			int r1 = ran.nextInt(iv.length);
			int r2 = ran.nextInt(iv.length);
			int temp = index[r1];
			index[r1] = index[r2];
			index[r2] = temp;
		}
		// 섞은 index 대로 iv에 img를 뿌리기
		for (int i = 0; i < iv.length; i++) {
			iv[i].setImageResource(img[index[i]]);
		}
		// 빈칸의 위치 구하기
		for (int i = 0; i < iv.length; i++) {
			if (index[i] == iv.length-1) { // 15: 빈칸의 이미지 index
				indexImgBlank = i;
				break;
			}
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// 이미지 버튼을 클릭시 호출되는 콜백 메서드
		// 터치한 이미지뷰가 몇번째 이미지 뷰인지 알아내기
		ImageView touchIV = (ImageView) v;
		int indexTouch = (Integer) touchIV.getTag(); // 터치한 이미지뷰의 index를 저장
//		for (int i = 0; i < iv.length; i++) {
//			if(touchIV.getId() == iv[i].getId()) {
//				indexTouch = i;
//				break;
//			}
//		}
		// 빈칸을 기준으로 
		if (indexTouch==indexImgBlank-4) { // 빈칸보다 상
			// swap indexImgBlank <-> indexTouch
			swap(indexTouch);
		} else if (indexTouch==indexImgBlank+4) { // 빈칸보다 하
			// swap indexImgBlank <-> indexTouch
			swap(indexTouch);
		} else if (indexTouch==indexImgBlank-1&&indexTouch%4!=3) { // 빈칸보다 좌
			swap(indexTouch);
		} else if (indexTouch==indexImgBlank+1&&indexTouch%4!=0) { // 빈칸보다 우
			swap(indexTouch);
		}
		if (isComplete()) { // 퍼즐을 완성했는지 확인
			// 게임 종료
			Toast.makeText(getApplicationContext(), 
					"완성~!ㅊㅋㅊㅋ", Toast.LENGTH_SHORT).show();
		}
		return false;
	}
	/** swap indexImgBlank <-> indexTouch 
	 * 
	 */
	public void swap(int indexTouch) {
		// index[~] -> 그림의 index, indexTouch & indexImgBlank-> 위치의 index
		int tmp = index[indexTouch]; 
		index[indexTouch] = index[indexImgBlank];
		index[indexImgBlank] = tmp;
		
		iv[indexTouch].setImageResource(img[index[indexTouch]]);
		iv[indexImgBlank].setImageResource(img[index[indexImgBlank]]);
		
		indexImgBlank = indexTouch; // 빈칸의 위치 정정
		
		sp.play(streamID, 1, 1, 0, 0, 1);
		vibrator.vibrate(1000);
		moveCount++;
		tvMoveCount.setText("이동회수: "+moveCount); // TextView 에 출력
	} // end of swap
	/** 퍼즐을 다 맞췄는지 확인하는 메소드
	 * 
	 */
	public boolean isComplete() { 
		boolean result = true;
		for (int i = 0; i < iv.length; i++) {
			if(index[i] != i) {
				result = false;
				break;
			}
		}
		return result;
	}
	/** xml 자원 객체로 선언하기
	 * findViewById
	 */
	public void myFindViewById() {
		
		tvMoveCount = (TextView)findViewById(R.id.textView1);
		tvMessage = (TextView) findViewById(R.id.textView2);
		bStart = (Button)findViewById(R.id.button1);
		bShuffle = (Button)findViewById(R.id.button2);
		bHint = (Button)findViewById(R.id.button3);
		sp = new SoundPool(1, 0, 0);
		streamID = sp.load(this, R.raw.click, 1);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		chronometer = (Chronometer) findViewById(R.id.chronometer1);
		
		iv[0] = (ImageView) findViewById(R.id.imageView1);
		iv[1] = (ImageView) findViewById(R.id.imageView2);
		iv[2] = (ImageView) findViewById(R.id.imageView3);
		iv[3] = (ImageView) findViewById(R.id.imageView4);
		iv[4] = (ImageView) findViewById(R.id.imageView5);
		iv[5] = (ImageView) findViewById(R.id.imageView6);
		iv[6] = (ImageView) findViewById(R.id.imageView7);
		iv[7] = (ImageView) findViewById(R.id.imageView8);
		iv[8] = (ImageView) findViewById(R.id.imageView9);
		iv[9] = (ImageView) findViewById(R.id.imageView10);
		iv[10] = (ImageView) findViewById(R.id.imageView11);
		iv[11] = (ImageView) findViewById(R.id.imageView12);
		iv[12] = (ImageView) findViewById(R.id.imageView13);
		iv[13] = (ImageView) findViewById(R.id.imageView14);
		iv[14] = (ImageView) findViewById(R.id.imageView15);
		iv[15] = (ImageView) findViewById(R.id.imageView16);
		
	}
	@Override
	protected void onPause() {
		super.onPause();
		if(mp!=null){
			mp.release();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
} // end of class
