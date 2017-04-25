package com.kosta.daehee.a76_fishfarming;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String[] fishName = new String[5];
	int[] fishBtnId = {R.id.fish0, R.id.fish1, R.id.fish2};
	Button[] buttons = new Button[3];
	Button btnBack;
	GameView gv;
	Handler handler = new Handler();
	Toast t = null;
	RadioGroup rg;
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		gv = (GameView)findViewById(R.id.gameView);
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = (Button)findViewById(fishBtnId[i]);
			buttons[i].setOnClickListener(myClickListener);
		}
		btnBack = (Button)findViewById(R.id.btnExit);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		tv = (TextView)findViewById(R.id.textView1);
		rg = (RadioGroup)findViewById(R.id.radioGroup1);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.radio0:
					gv.feedSelNum = 0;
					// Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
					break;
				case R.id.radio1:
					gv.feedSelNum = 1;
					// Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("규칙 설명");
		builder.setMessage("- 1초에 10점 씩 점수가 자동으로 증가합니다."
				+ "\n- 화면 터치를 통해 먹이를 줄 수 있습니다. 1개 당 점수 1점을 소모합니다."
				+ "\n- 물고기가 먹이를 먹으면 점수가 1 증가합니다."
				+ "\n- 물고기가 죽으면 점수가 100점 감소합니다.");
		builder.setNegativeButton("확인", null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	View.OnClickListener myClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			for (int i = 0; i < buttons.length; i++) {
				if (v.getId() == buttons[i].getId()) {
					gv.addFish(i);
				}
			} // end of for
		} // onClick
	};
	
	@Override
	public void onBackPressed() {
		handler.post(backKeyRun);
	}	
	
	Runnable backKeyRun = new Runnable() {
		int count = 0;
		@Override
		public void run() {
			if (count < 1) {
				if (t != null) t.cancel();
				t = Toast.makeText(getApplicationContext(), 
						"뒤로가기를 한번 더 누르시면 종료됩니다", 
						Toast.LENGTH_SHORT);
				t.show();
				count++;
			} else {
				finish();
			}
			
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					count = 0;
				}
			}, 2000);
		}
	};
	
	@Override
	protected void onPause() {
		super.onPause();
		if (t != null) t.cancel();
	}
}
















