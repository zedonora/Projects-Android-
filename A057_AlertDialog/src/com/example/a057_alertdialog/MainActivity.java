package com.example.a057_alertdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	final int DIALOG_ID_DELETE = 1;
	final int DIALOG_ID_MOVE = 2;
	final int DIALOG_ID_FRUIT = 3;
	final int DIALOG_ID_BOOK = 4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ID_DELETE); // 다이얼로그 띄우기
			}
		});
		
		Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ID_MOVE);
			}
		});
		
		Button b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ID_FRUIT);
			}
		});
		Button b4 = (Button) findViewById(R.id.button4);
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ID_BOOK);
			}
		});
	} // end of onCreate
	@Override
	protected Dialog onCreateDialog(int id) { // 다이얼로그 객체가 처음 생성될때 호출
		Log.d("dialog", "onCreateDialog");
		switch (id) {
		case DIALOG_ID_DELETE:
			AlertDialog.Builder dialog = new AlertDialog.Builder(this); // this로 해야함
			dialog	.setTitle("삭제")
					.setMessage("정말 삭제하시겠습니까?")
					.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 다이얼로그의 (네) 버튼을 클릭했을 때 호출되는 콜백 메서드
							Toast.makeText(getApplicationContext(), 
									"삭제 고고싱", Toast.LENGTH_SHORT).show();
						}
					})
					.setNegativeButton("네", null)
					.setNeutralButton("취소", null);
			// 다이얼로그가 취소됐을 때
			dialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					Toast.makeText(getApplicationContext(), 
							"다이얼로그 닫힘", Toast.LENGTH_SHORT).show();
				}
			});
			return dialog.create(); // 다이얼로그 객체 리턴
		case DIALOG_ID_MOVE:
			final String items[] = {"바탕화면","c드라이브","내컴퓨터","내문서"};
			AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
			dialog2.setTitle("이동할 폴더를 고르세요");
//			dialog2.setMessage(""); // 메시지 대신 리스트 항목을 사용하겠음
			dialog2.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(getApplicationContext(), 
							items[which]+"를 클릭함", Toast.LENGTH_SHORT).show();
				}
			});
			return dialog2.create();
		case DIALOG_ID_FRUIT:
			final String items2[] = {"사과","딸기","수박","참외"};
			AlertDialog.Builder dialog3 = new AlertDialog.Builder(this);
			dialog3.setTitle("가장 좋아하는 과일을 고르세요");
			dialog3.setSingleChoiceItems(items2, -1, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(getApplicationContext(), 
							items2[which]+"을 선택했습니다.", Toast.LENGTH_SHORT).show();
					dialog.dismiss(); // 다이얼로그를 내림
				}
			});
			dialog3.setPositiveButton("네", null);
			return dialog3.create();
		case DIALOG_ID_BOOK:
			final String book[] = {"자바책","덕혜옹주","삼국지","맛집기행"};
			final boolean checkedItems[] = {false,false,true,false};
			AlertDialog.Builder dialog4 = new AlertDialog.Builder(this);
			dialog4.setTitle("좋아하는 책을 모두 고르세요");
			dialog4.setMultiChoiceItems(book, checkedItems, new OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					Toast.makeText(getApplicationContext(), 
							// which: 는 멀티초이스한 항목이 아님 -> 별도의 이벤트 처리해야 함
							which+(isChecked?"선택":"해제"), Toast.LENGTH_SHORT).show();
					checkedItems[which] = isChecked;
				}
			});
			dialog4.setPositiveButton("완료", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String str = "";
					for (int i = 0; i < checkedItems.length; i++) {
						if(checkedItems[i]) {
							if("".equals(str)) {
								str+=book[i];
							} else {
								str+=","+book[i];
							}
						}
					}
//					if(str.length()>1){
//						str = str.substring(0, str.length()-1);
//					}
					Toast.makeText(getApplicationContext(), 
					// which: 는 멀티초이스한 항목이 아님 -> 별도의 이벤트 처리해야 함
					str+"를 선택하셨습니다.", 
					Toast.LENGTH_SHORT).show();
				}
			});
			return dialog4.create();
		} // end of switch
		return null; // 다이얼로그를 생성해서 리턴
	}
} // end of class
