package com.example.a002_textview; // 앱을 구별하는 기준
								//(마켓에 올릴때는 유니크한 패키지명 사용해야 함)
								//example 키워드를 사용하면 안된다.


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
// Activity : 한 화면을 담당하기 위한 컴포넌트
	@Override // onCreate : 화면이 나타날 때 최초로 호출되는 콜백 메서드
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); // 레이아웃 xml 파일과 연결
		
		// 레이아웃 xml 에서 등록한 id의 위젯을 객체로 얻어오기
//		View v = findViewById(R.id.textView1); 
//		TextView tv = (TextView) v;
		TextView tv = (TextView) findViewById(R.id.textView1);
		// 안드로이드 디바이스는 콘솔 창이 없음
		// 코드의 진행을 확인하기 위한 방법으로 Log 클래스를 사용할 수 있다.
		Log.d("test","안녕하세요"); // tag, 출력할 메세지
		String str = tv.getText().toString();
		Log.d("test","tv의 문자열 : "+str); // tag, 출력할 메세지
		
		tv.setText("반갑습니다!!");
//		tv.setTextColor(0xffffff00); // 0xAARRGGBB
		tv.setTextColor(Color.YELLOW);
	}
}