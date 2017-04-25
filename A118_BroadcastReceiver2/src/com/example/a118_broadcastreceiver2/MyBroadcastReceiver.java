package com.example.a118_broadcastreceiver2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver{
	Context context;
	@Override
	public void onReceive(Context context, Intent intent) {
//		BoradcastReceiver : Android의 4대 컴포넌트 중 하나
//		onReceive() 메서드 오버라이딩 - Broadcast 수신시 호출되는 콜백 메서드
//		수신하고자 하는 내용을 등록하기
//		1. AndroidManifest.xml 에 등록 - 항상 활성화 된 상태
//		2. Activity 코드상에서 등록 		- Activity 가 실행된 상태에서만 수신가능
		
		this.context = context;
		
		// 직접 앱을 실행시키기가 어렵기 때문에, 노티로 사용자에 알림을 준다.
		NotificationManager manager = (NotificationManager) context.getSystemService
				(Context.NOTIFICATION_SERVICE);
		Notification noti = new Notification(R.drawable.ic_launcher, // 상단의 알림 아이콘
											 "노티 메세지",
											 System.currentTimeMillis());
		Intent intent2 = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent2, 0);
		noti.setLatestEventInfo(context, "이걸 눌러봐 제목", "실행 시켜줄게", pIntent);
		manager.notify(1234, noti);
		
	} // end of onReceive
} // end of class
