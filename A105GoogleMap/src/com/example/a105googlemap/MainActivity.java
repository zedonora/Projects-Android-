package com.example.a105googlemap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
// sdk 설치폴더 \android\extras\google\google_play_services\libproject 폴더가 있어야 함
// google_play_services.zip 참고
// sdk 설치폴더 \android\extras\android\support\v4\android-support-4.0.jar 파일이 있어야 함
// android-support-4.0.jar 참고
// 프로젝트 생성시 Google APIs 버전으로 생성 <= SDK Manager에서 해당버전을 install 해야 함
// google-play-servies_lib 라이브러리 프로젝트 import
// 프로젝트 환경설정 - 구글맵 사용하기위한 라이브러리 추가 2개
		
	} // end of onCreate
} // end of class
