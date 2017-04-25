package com.example.a059_alertdialog_custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	} // end of onCreate
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		LayoutInflater lif = (LayoutInflater) 
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = lif.inflate(R.layout.exit_dialog, null);
		dialog.setTitle("종료");
		dialog.setView(view);
		dialog.setPositiveButton("네", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		dialog.setNegativeButton("아니오", null);
		return dialog.create();
	}
	@Override
	public void onBackPressed() { // 뒤로가기 키를 눌르면 호출되는 콜백 메서드
		showDialog(1); // 종료 여부를 묻는 다이얼로그 띄우기	
	}
} // end of class
