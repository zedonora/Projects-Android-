package com.example.a90_frameanimation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	int num=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button b = (Button)findViewById(R.id.button1);
		ImageView iv = (ImageView)findViewById(R.id.imageView1);
	
		//Background Drawable객체를 얻어옴
		final AnimationDrawable ad = (AnimationDrawable)iv.getDrawable();//src에 저장했을 때
//		final AnimationDrawable ad = (AnimationDrawable)iv.getBackground();//background에 저장했을 때
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(ad.isRunning()){b.setText("애니메이션 시작");ad.stop();}//실행중지 여부확인 후, 애니메이션 멈춤
				else{ad.start();b.setText("애니메이션 멈춤");}//현재 멈춰있는 상태확인 후, 애니메이션 시작
			}
		});
	}//end of void onCreate
}//end of class MainActivity
