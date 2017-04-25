package com.example.a111_myprofile;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class MainActivity extends Activity {

   MysurfaceView mysurfaceview;
   boolean isLogin;
   MediaPlayer mp;
   SharedPreferences spf;
   int num = 0;
   int soundID1;
   SoundPool sp;
   Vibrator vt;

   Paint paint = new Paint();
   int introduce[] = { R.drawable.name, R.drawable.phone2, R.drawable.address2, R.drawable.happybirth,
         R.drawable.academy };

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      mysurfaceview = (MysurfaceView) findViewById(R.id.myView);// 꽃잎을 뿌려줄 화면
      vt = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);// 클릭시 진동객체
      sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);// itemSelect시 버튼 음
                                             // 생성
      soundID1 = sp.load(this, R.raw.buttonmusic, 1);

      mp = MediaPlayer.create(getApplicationContext(), R.raw.musicss);// 배경음악
      mp.setVolume(0.3f, 0.3f);
      mp.setLooping(true);
      mp.start();

      setTitle("Let me introduce myself");

      // gallery설정
      Gallery gallery = (Gallery) findViewById(R.id.gallery1);
      MyAdapter adapter = new MyAdapter(getApplicationContext(), R.layout.name, introduce);
      gallery.setAdapter(adapter);
      gallery.setOnItemSelectedListener(new OnItemSelectedListener() {// gallery에서
                                                      // 포커스
                                                      // 맞춰질때마다
                                                      // soundPool이벤트
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            sp.play(soundID1, 1, 1, 0, 0, 1);
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {
         }
      });
      gallery.setOnItemClickListener(new OnItemClickListener() {// gallery에서
                                                   // 클릭시 진동이벤트
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            vt.vibrate(500);
         }
      });
   }// end of void onCreate

   @Override
   protected void onPause() {
      super.onPause();
      if (mp != null)
         mp.release();
      mp = null;
   }

   @Override
   protected void onResume() {
      super.onResume();
      // 배경음 다시 재생
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {// sound On/Off option메뉴
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

   @Override
   public boolean onPrepareOptionsMenu(Menu menu) {
      return super.onPrepareOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {// optionMenu 선택시 sound
                                             // 이벤트
      switch (R.menu.main) { 
      case R.id.soundOff:// 배경음 종료할 때
         isLogin = !isLogin;
         return true;
      default:
         return false;
      }
   }

   class MyAdapter extends BaseAdapter {// 종류별 프로필 이미지 gallery에 뿌리기
      LayoutInflater lif;
      int name;
      int introduce[];

      public MyAdapter(Context context, int name, int[] introduce) {
         this.lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         this.name = name;
         this.introduce = introduce;
      }

      @Override
      public int getCount() {
         return introduce.length;
      }

      @Override
      public Object getItem(int position) {
         return introduce[position];
      }

      @Override
      public long getItemId(int position) {
         return position;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
         if (convertView == null) {
            convertView = lif.inflate(name, null);
         }
         ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
         iv.setImageResource(introduce[position]);
         return convertView;
      }
   }// end of class MyAdapter

}// end of class MainActivity

class MysurfaceView extends SurfaceView {// 벚꽃잎 떨어뜨리기 위한 SurfaceView
   MyThread mt;// 화면 변경할 쓰레드
   Point point = new Point();
   Display d;
   // boolean b;
   Point p[] = new Point[10];
   Canvas canvas = null;
   Random r = new Random();
   Bitmap bitmap2; // 배경화면 메인배경 이미지
   Bitmap bitmap3; // 배경화면 메인배경 늘린 이미지

   class MyThread extends Thread {// SurfaceView에서 실행시킬 Thread
      SurfaceHolder holder;
      boolean go = true;

      public MyThread(SurfaceHolder holder) {
         this.holder = holder;
      }

      @Override
      public void run() {
         for (int i = 0; i < p.length; i++) {
            p[i] = new Point(r.nextInt(1000) + 100, r.nextInt(1000) + 100);
         }
         while (go) {
            try {
               canvas = holder.lockCanvas(null);
               canvas.drawBitmap(bitmap3, 0, 0, null); // 배경화면 그리기

               for (int i = 0; i < p.length; i++) {
                  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bom);
                  canvas.drawBitmap(bitmap, p[i].x, p[i].y, null);
                  p[i].x += r.nextInt(20) - 5;
                  p[i].y += r.nextInt(10) + 1;
                  if (p[i].x > canvas.getWidth() | p[i].x < 0) {
                     p[i].x = 0;
                  }
                  if (p[i].y > canvas.getHeight()) {
                     p[i].y = 0;
                  }
               }
            } catch (Exception e) {

            } finally {
            	if(canvas!=null)
               holder.unlockCanvasAndPost(canvas);
            }
         }
      }
   }

   public MysurfaceView(Context context) {
      super(context);
      flowerGo();
   }

   public MysurfaceView(Context context, AttributeSet attrs, int defStyle) {
      super(context, attrs, defStyle);
      flowerGo();
   }

   public MysurfaceView(Context context, AttributeSet attrs) {
      super(context, attrs);
      d = ((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
      d.getSize(point);
      bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.bombom2); // 기본
      bitmap3 = Bitmap.createScaledBitmap(bitmap2, point.x, point.y, false); // 각
      flowerGo();
   }

   public void flowerGo() {

      SurfaceHolder holder = getHolder();
      mt = new MyThread(holder);
      holder.addCallback(new Callback() {
         @Override
         public void surfaceDestroyed(SurfaceHolder holder) {
            mt.go = false;
         }

         @Override
         public void surfaceCreated(SurfaceHolder holder) {
            mt.start();
         }

         @Override
         public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
         }
      });
   }

}// end of class MySurfaceView