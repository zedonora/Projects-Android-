package com.example.a98_animationintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Next extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.next);
		Button b = (Button)findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//				startActivity(intent);
				finish();//현재화면 메모리에서 제거
				
				//화면전환 애니메이션
				overridePendingTransition(R.anim.rightout, R.anim.leftin);
			}
		});
	}//end of void onCreate
}//end of class Activity
