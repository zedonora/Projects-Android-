package com.example.a119_broadcastreceiver3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// Broadcast 수신시 호출되는 콜백 메서드
		// AndroidManifest.xml 등록
		// Intent를 실행하기 위해서 noti를 띄우자
		
//		NotificationManager manager = (NotificationManager) context.getSystemService
//				(Context.NOTIFICATION_SERVICE);
//		Notification noti = new Notification(R.drawable.ic_launcher,
//											 "수신해서 노티 띄움", // 잠깐 띄워주는 메세지
//											 System.currentTimeMillis()); // 현재 시간
//		
//		Intent intent2 = new Intent(context, MainActivity.class);
//		PendingIntent pIntent = PendingIntent.getActivity(context, 
//				0, // requestCode 
//				intent2, // 다음 화면에 대한 intent 정보
//			PendingIntent.FLAG_CANCEL_CURRENT); // flags -> 이전에 생성한 PendingIntent는 취소, 새롭게 생성
////			PendingIntent.FLAG_NO_CREATE); // flags -> 현재 생성된 PendingIntent를 그대로 반환
////			PendingIntent.FLAG_ONE_SHOT); // flags -> 이 flag를 통해 생성된 PendingIntent를 한번만 사용가능
////			PendingIntent.FLAG_UPDATE_CURRENT); // flags -> 이미 생성된 PendingIntent가 존재하면, 변경해서 사용
//		noti.setLatestEventInfo(context, 
//				"펭귄",  // 노티알림의 제목
//				"히잉~! 앱을 실행합니다", // 노티알림의 메시지
//				pIntent); // pendingIntent
//		manager.notify(1234,noti); // 노티 등록				
		
		// noti 없이 바로 화면전환하기
		Intent intent3 = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 123, intent3, 0);
		
		try {
			pIntent.send();
		} catch (CanceledException e) {
		}
	} // end of onReceive
} // end of class
