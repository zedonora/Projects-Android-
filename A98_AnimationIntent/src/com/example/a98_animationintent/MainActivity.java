package com.example.a98_animationintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b  = (Button)findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//다음화면으로 넘어가기
				Intent intent = new Intent(getApplicationContext(),Next.class);
				startActivity(intent);
				//화면전환 애니메이션
				overridePendingTransition(R.anim.leftin, R.anim.rightout);
//				finish();//현재화면 메모리에서 지우기
			}
		});
	}//end of void onCreate
}
