package com.example.a029_sharedpreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

public class SPSet extends Activity {
	private SharedPreferences sp; // 설정 값을 간단히 저장할 수 있는 방법
	private CheckBox cb;
	private Switch sc;
	private EditText et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spset);
		
		cb = (CheckBox) findViewById(R.id.checkBox1);
		sc = (Switch) findViewById(R.id.switch1);
		et = (EditText) findViewById(R.id.editText1);
		sp = getSharedPreferences("set.dat", Context.MODE_PRIVATE); // 덮어쓰기
//	getSharedPreferences
//		Activity 내부에서 쉽게 사용 가능하다.
//		data/data/패키지명/shared_prefs/파일명
//		여러개를 작성할 수 있다.
		
//	PreferenceManager.getDefaultSharedPreferences(this);
//		PreferenceManager 를 통해서만 객체를 얻어올 수 있다.
//		data/data/패키지명/shared_prefs/패키지명_preferences.xml
//		하나만 작성가능
//		context 객체가 필요하다.
		
	} // end of onCreate
	@Override
	protected void onResume() {
		super.onResume();
		// 사용자가 설정화면에 진입시 SharePreferences 파일에 저장되어 있는 설정값 읽어서 적용
		if (sp == null) {
			return;
		}
		cb.setChecked(sp.getBoolean("배경음악", false));
		sc.setChecked(sp.getBoolean("효과음", false));
		et.setText(sp.getString("이메일", ""));
	}
	@Override
	protected void onPause() {
		super.onPause();
		// 사용자가 설정화면을 빠져나가게 되면 저장하기
		// SharedPreferences 에 저장하기
		Editor editor = sp.edit();
		editor.putBoolean("배경음악", cb.isChecked());
		editor.putBoolean("효과음", sc.isChecked());
		editor.putString("이메일", et.getText().toString());
		
		editor.commit(); // 변경사항을 저장하기
	} // end of onPause
} // end of class
