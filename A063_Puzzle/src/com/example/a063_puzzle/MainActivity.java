package com.example.a063_puzzle;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
public class MainActivity extends Activity {
	ImageView ivs[] = new ImageView[16];
	int iv[] = {R.id.imageView1,R.id.imageView2,R.id.imageView3,R.id.imageView4,
			R.id.imageView5,R.id.imageView6,R.id.imageView7,R.id.imageView8,
			R.id.imageView9,R.id.imageView10,R.id.imageView11,R.id.imageView12,
			R.id.imageView13,R.id.imageView14,R.id.imageView15,R.id.imageView16};
	int img[] = {R.drawable.cat1,R.drawable.cat2,R.drawable.cat3,R.drawable.cat4,
			R.drawable.cat5,R.drawable.cat6,R.drawable.cat7,R.drawable.cat8,
			R.drawable.cat9,R.drawable.cat10,R.drawable.cat11,R.drawable.cat12,
			R.drawable.cat13,R.drawable.cat14,R.drawable.cat15,R.drawable.ic_launcher};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		for (int i = 0; i < iv.length; i++) {
			ivs[i] = (ImageView) findViewById(iv[i]);
		}
		init();
		Button bStart = (Button) findViewById(R.id.button1);
		Button bShuffle = (Button) findViewById(R.id.button2);
		Button bAnswer = (Button) findViewById(R.id.button3);
		bAnswer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				init();
			}
		});
		bShuffle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				shuffle();
			}
		});
		bStart.setOnClickListener(new OnClickListener() { 
			// 게임 시작 
			// 1.셔플이 된다. 해결
			// 2.타이머 20부터 줄어간다.
			// 3.이미지가 눌릴 때마다 이동 횟수가 증가한다.
			// 4.이미지가 상하 좌우 중에 비어 있으면 이동한다.
			// 5.초기 형태와 완성된 형태가 같으면 게임을 끝낸다.
			@Override
			public void onClick(View v) {
				shuffle();
				
			}
		});
	} // end of onCreate
	private void init() { // 초기모양
		for (int i = 0; i < img.length; i++) {
			ivs[i].setImageResource(img[i]);
		}
	}
	private void shuffle() { // 섞는 거
		ArrayList<Integer> shuArr = new ArrayList<Integer>();
		for (int i = 0; i < img.length; i++) {
			shuArr.add(img[i]);
		}
		Collections.shuffle(shuArr);
		for (int i = 0; i < img.length; i++) {
			ivs[i].setImageResource(shuArr.get(i));
		}
	}
} // end of class
