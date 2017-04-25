package com.example.a066_notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Notification 노티 띄우기
				// 1. NotificationManager를 얻어온다.
				// 2. Notification을 객체생성해서 설정한다.
				//	Intent 객체 => PendingIntent 객체를 준비
				// 3. NotificationManager 에 Notification을 등록한다.
				NotificationManager manager = (NotificationManager) getSystemService
						(Context.NOTIFICATION_SERVICE);
				Notification noti = new Notification(
						R.drawable.ic_launcher, // 상태바 아이콘
						"티커텍스트", // 상태바 표시되는 문자열 (잠깐 나옴) 
						System.currentTimeMillis()); // 알림 발생시간
				
				noti.flags = Notification.FLAG_AUTO_CANCEL; // 노티 선택시 노티 자동해제
				noti.sound = Uri.parse("/storage/emulated/0/Music/감미로운 음악/owen.mp3");
//				noti.sound = Uri.parse("/storage/emulated/0/KaKaoTalkDownload/Bubble.wav");
//				noti.defaults |= Notification.DEFAULT_SOUND; // 소리 내기
				noti.defaults |= Notification.DEFAULT_VIBRATE; // 진동
				// 슬라이딩 퍼즐 앱을 호출하기
				Intent intent = new Intent("sliding"); // 다음 넘어갈 Activity
				PendingIntent contentIntent = PendingIntent.getActivity(
						getApplicationContext(), // 현재화면 제어권자
						0, 			// requestCode -> return값을 정해줄 수 있다.
						intent, 	// 노티 선택시 넘어갈 화면의 정의
						PendingIntent.FLAG_CANCEL_CURRENT); 
						// 여러 개의 PendingIntent가 쌓일경우, 이전 거 취소 후 새롭게 생성
// PendingIntent.FLAG_NO_CREATE : 현재 생성된 PendingIntent를 반환한다.
// PendingIntent.FLAG_ONE_SHOT : 이 플래그로 생성된 PendingIntent는 한번밖에 사용할 수 없다
// PendingIntent.FLAG_UPDATE_CURRENT : 이미 생성된 PendingIntent가 있으면 Intent내용만 변경함
				noti.setLatestEventInfo(
						getApplicationContext(), 
						"노티제목", 
						"노티내용", 
						contentIntent);
				manager.notify(12, noti); // 상태바에 알림을 등록
			}
		});
	} // end of onCreate
} // end of class
