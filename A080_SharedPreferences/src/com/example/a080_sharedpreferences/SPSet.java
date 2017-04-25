package com.example.a080_sharedpreferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SPSet extends PreferenceActivity { // 편리하게 Preference를 관리할 수 있다.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.spset);
//		getSharedPreferences
//		Activity 내부에서 쉽게 사용 가능하다.
//		data/data/패키지명/shared_prefs/파일명
//		여러개를 작성할 수 있다.
		
//	PreferenceManager.getDefaultSharedPreferences(this);
//		PreferenceManager 를 통해서만 객체를 얻어올 수 있다.
//		data/data/패키지명/shared_prefs/패키지명_preferences.xml
//		하나만 작성가능
//		context 객체가 필요하다.
	} // end of onCreate
} // end of class
