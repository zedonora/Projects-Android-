package com.example.a058_alertdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	final int DIALOG_ID_DELETE = 1;
	final int DIALOG_ID_UPDATE = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button bDelete = (Button) findViewById(R.id.button1);
		Button bUpdate = (Button) findViewById(R.id.button2);
		
		bDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ID_DELETE);
			}
		});
		bUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ID_UPDATE);
			}
		});
	} // end of onCreate
	@Override
	protected Dialog onCreateDialog(int id) { 
		// 각 id 당 한번씩 다이얼로그 객체생성을 위해 호출되는 콜백 메서드
		Log.d("dialog","onCreateDialog() "+id);
		switch (id) {
		case DIALOG_ID_DELETE: 
		case DIALOG_ID_UPDATE:
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("삭제");
			dialog.setMessage("정말 삭제하시겠습니까?");
			dialog.setPositiveButton("예", null);
			dialog.setNegativeButton("아니오", null);
			return dialog.create();
//		case DIALOG_ID_UPDATE:
//			AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
//			dialog2.setTitle("수정");
//			dialog2.setMessage("정말 수정하시겠습니까?");
//			dialog2.setPositiveButton("예", null);
//			dialog2.setNegativeButton("아니오", null);
//			return dialog2.create();
		}
		return null;
	}
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		// 다이얼로그가 화면에 띄워질 때마다 호출되는 콜백 메서드
		Log.d("dialog","onPrepareDialog() "+id);
		switch (id) {
		case DIALOG_ID_UPDATE:
			AlertDialog ad = (AlertDialog) dialog;
			ad.setTitle("수정");
			ad.setMessage("정말 수정하시겠습니까?");
		}
	}
} // end of class
