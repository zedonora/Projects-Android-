package com.example.a074_draw_surfaceview;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MySurfaceView(this));
	} // end of onCreate
} // end of class

class MySurfaceView extends SurfaceView{
	MyThread mt; // 화면 변경할 쓰레드
	public MySurfaceView(Context context) {
		super(context);
		SurfaceHolder holder = getHolder(); // SurfaceHolder 객체를 얻어옴
		holder.addCallback(new Callback() {
			@Override
			public void surfaceCreated(SurfaceHolder holder) { // SurfaceView 생성시
				mt.start(); // 쓰레드 시작
			}
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) { // SurfaceView 소멸시
				mt.go = false; // 쓰레드 종료
			}
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			} // SurfaceView에 대한 구조적인 변경 발생시 (크기 or 포멧)
		});
		mt = new MyThread(holder); // 쓰레드 생성자에 SurfaceHolder 객체를 전달
	} // 생성자
	class MyThread extends Thread{ // 화면 변경작업을 할 쓰레드
		SurfaceHolder holder;
		boolean go = true;
		public MyThread(SurfaceHolder holder) {
			this.holder = holder;
		}
		public void run() {
			Random ran = new Random();
			Point p[] = new Point[10]; // 원들의 중심 좌표저장할 배열
			for (int i = 0; i < p.length; i++) {
//				p[i] = new Point(ran.nextInt(1000)+100,0);
				
				p[i] = new Point(ran.nextInt(holder.getSurfaceFrame().width()),0);
			}
			while(go){
				Paint paint = new Paint();
				paint.setColor(Color.MAGENTA);
				Canvas c = null;
				try {
					synchronized (holder) {
						c = holder.lockCanvas(null); // 화면 변경작업을 시작
						c.drawColor(Color.WHITE); // 배경 색 초기화
						// 화면 변경 작업
						for (int i = 0; i < p.length; i++) {
							c.drawCircle(p[i].x, p[i].y, 100, paint);
							p[i].y+= ran.nextInt(10)+1;
							if (p[i].y>c.getHeight()) {
								p[i].y = 0;
							}
						}
					}
				} catch (Exception e) {
				} finally {
					if(c!=null){
						holder.unlockCanvasAndPost(c); // 화면변경작업 종료
					}
				}
			}
		}
	}
} // end of class MySurfaceView