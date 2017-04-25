package com.example.a105googlemap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//sdk설치폴더\extras\google\google_play_services\libproject폴더가 있어야 함
		//google_play_services.zip참고
		//sdk설치폴더\extras\android\support\v4\android-support-4.0.jar폴더가 있어야 함
		//android-support-4.0.jar참고
		//프로젝트 생성시 Google APIs 버전으로 생성<=SDK Manager에서 해당 버전을 install해야 함
		//google-play-services_lib라이브러리 프로젝트 import
		
		
		//프로젝트 환경설정 - 구글맵 사용하기 위한 라이브러리 추가 2개
//		https://console.developers.google.com/apis/library
		
		
	}
}
