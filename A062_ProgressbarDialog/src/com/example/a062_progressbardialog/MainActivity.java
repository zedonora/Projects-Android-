package com.example.a062_progressbardialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	final int DIALOG_ID_SPINNER = 1;
	final int DIALOG_ID_HORIZONTAL = 2;
	private ProgressDialog dialog2;
	Handler handler = new Handler();
	Runnable r = new Runnable() {
		@Override
		public void run() {
			if(dialog2.getProgress()<dialog2.getMax()) {
//				dialog2.setProgress(dialog2.getProgress()+1); // 프로그래스바를 그려줌
				dialog2.incrementProgressBy(1); // 기존의 값에 1증가 시키기
				handler.postDelayed(r, 10);
			} else { // 최고값 도달
				dialog2.setProgress(0); // 초기화
				dialog2.dismiss(); // 다이얼로그 내리기
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ID_SPINNER);
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ID_HORIZONTAL);
//				handler.post(r);
//				dialog2.setProgress(0); // 초기화
			}
		});
	} // end of onCreate
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ID_SPINNER:
			ProgressDialog dialog = new ProgressDialog(this); 
			dialog.setTitle("복사");
			dialog.setMessage("데이터를 복사중입니다.");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			return dialog;
		case DIALOG_ID_HORIZONTAL:
			dialog2 = new ProgressDialog(this); 
			dialog2.setTitle("이동");
			dialog2.setIcon(R.drawable.ic_launcher);
			dialog2.setMessage("파일을 이동중입니다.");
			dialog2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog2.setMax(50);
			return dialog2;
		}
		return null;
	}
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DIALOG_ID_HORIZONTAL:
			handler.post(r);
			break;
		}
	}
} // end of class
