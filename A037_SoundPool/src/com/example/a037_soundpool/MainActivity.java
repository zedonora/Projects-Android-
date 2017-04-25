package com.example.a037_soundpool;


//import java.util.ArrayList;
//
//import android.app.Activity;
//import android.media.AudioManager;
//import android.media.SoundPool;
//import android.media.SoundPool.OnLoadCompleteListener;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//public class MainActivity extends Activity {
//   ArrayList<Integer> al;
//   SoundPool sp;
//   int soundID1;
//   int soundID2;
//   int soundID3; 
//   int soundID4; 
//   int soundID5; 
//   int soundID[];
//   @Override
//   protected void onCreate(Bundle savedInstanceState) {
//      super.onCreate(savedInstanceState);
//      setContentView(R.layout.activity_main);
//      soundID = new int[5];
//      final Button b1 = (Button)findViewById(R.id.button1);
//      final Button b2 = (Button)findViewById(R.id.button2);
//      final Button b3 = (Button)findViewById(R.id.button3);
//      final Button b4 = (Button)findViewById(R.id.button4);
//      final Button b5 = (Button)findViewById(R.id.button5);
//      sp = new SoundPool(1,AudioManager.STREAM_MUSIC,0);//0:음질 기본값
//      soundID1  = sp.load(this, R.raw.gun,1);
//      soundID2  = sp.load(this, R.raw.cat,1);
//      soundID3  = sp.load(this, R.raw.audio,1);
//      soundID4  = sp.load(this, R.raw.chacha,1);
//      soundID5  = sp.load(this, R.raw.gun3,1);
//      
//      b1.setOnClickListener(new OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            b1.setEnabled(true);
//            if(soundID[0]==0){
//            soundID[0] = soundID1;
//            sp.play(soundID[0], //로딩 해 놓은 음악파일
//                  1, //왼쪽 볼륨      (작은) 0.0f~.1.0f (큰)
//                  1, //오른쪽볼륨      (작은) 0.0f~.1.0f (큰)
//                  0, //우선순위      int
//                  0, //반복횟수      0:무반복, -1:반복안함,   양의 정수:반복횟수
//                  1);
//            soundID[0] = 0;
//         }}
//      });
//      b2.setOnClickListener(new OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            if(soundID[1]==0){
//               soundID[1] = soundID2;
//            sp.play(soundID[1], //로딩 해 놓은 음악파일
//                  1, //왼쪽 볼륨      (작은) 0.0f~.1.0f (큰)
//                  0, //오른쪽볼륨      (작은) 0.0f~.1.0f (큰)
//                  0, //우선순위      int
//                  0, //반복횟수      0:무반복, -1:반복안함,   양의 정수:반복횟수
//                  0.5f);//재생속도      (느림)0.5f~2.0f(빠름)   몇배속
//            soundID[1] = 0;
//            }
//         }
//      });
//      b3.setOnClickListener(new OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            if(soundID[2]==0){
//               soundID[2] = soundID3;
//            sp.play(soundID[2], //로딩 해 놓은 음악파일
//                  0, //왼쪽 볼륨      (작은) 0.0f~.1.0f (큰)
//                  1, //오른쪽볼륨      (작은) 0.0f~.1.0f (큰)
//                  0, //우선순위      int
//                  0, //반복횟수      0:무반복, -1:반복안함,   양의 정수:반복횟수
//                  1);//재생속도      (느림)0.5f~2.0f(빠름)   몇배속
//            soundID[2] = 0;
//            }
//         }
//      });
//      b4.setOnClickListener(new OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            if(soundID[3]==0){
//               soundID[3] = soundID4;
//            sp.play(soundID[3], //로딩 해 놓은 음악파일
//                  1, //왼쪽 볼륨      (작은) 0.0f~.1.0f (큰)
//                  1, //오른쪽볼륨      (작은) 0.0f~.1.0f (큰)
//                  0, //우선순위      int
//                  0, //반복횟수      0:무반복, -1:반복안함,   양의 정수:반복횟수
//                  0.5f);//재생속도      (느림)0.5f~2.0f(빠름)   몇배속
//            soundID[3] = 0;
//            }
//         }
//      });
//      b5.setOnClickListener(new OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            if(soundID[4]==0){
//               soundID[4] = soundID5;
//            sp.play(soundID3, //로딩 해 놓은 음악파일
//                  1, //왼쪽 볼륨      (작은) 0.0f~.1.0f (큰)
//                  1, //오른쪽볼륨      (작은) 0.0f~.1.0f (큰)
//                  0, //우선순위      int
//                  0, //반복횟수      0:무반복, -1:반복안함,   양의 정수:반복횟수
//                  2.0f);//재생속도      (느림)0.5f~2.0f(빠름)   몇배속
//            soundID[4] = 0;
//            }
//         }
//      });
//   }
//   @Override
//   protected void onPause() {
//      super.onPause();
//      if(soundID1!=0 || soundID2!=0 || soundID3!=0 || soundID4!=0 || soundID5!=0){
//      sp.stop(soundID1);
//      sp.stop(soundID2);
//      sp.stop(soundID3);
//      sp.stop(soundID4);
//      sp.stop(soundID5);
//      }
//   }
//}
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	int streamID;
	SoundPool sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // 0: 음질 기본값
		final int soundID1 = sp.load(this, R.raw.gun, 1);
		final int soundID2 = sp.load(this, R.raw.cat, 1);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		Button b5 = (Button) findViewById(R.id.button5);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				streamID = sp.play(soundID1,  // 로딩해 놓은 음악파일
						1,  // 왼쪽 볼륨	작은 0.0f ~ 1.0f 큰
						1,  // 오른쪽 볼륨	작은 0.0f ~ 1.0f 큰
						0,  // 우선순위	int
						0,  // 반복회수	0:노반복, -1:무한반복, 양의 정수: 반복회수
						1); // 재생속도	(느림) 0.5f ~ 2.0f (빠름 2배속)
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				streamID = sp.play(soundID1, 1, 0, 0, 0, 1);
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					streamID = sp.play(soundID1, 0, 1, 0, 0, 1);
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					streamID = sp.play(soundID1, 1, 1, 0, 0, 0.5f);
			}
		});
		b5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					streamID = sp.play(soundID1, 1, 1, 0, 0, 2.0f);
			}
		});
	} // end of onCreate
	@Override
	protected void onPause() {
		super.onPause();
		if(streamID!=0){
			sp.stop(streamID);
		}
	}
} // end of class
//

