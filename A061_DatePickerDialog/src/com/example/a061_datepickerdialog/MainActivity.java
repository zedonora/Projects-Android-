package com.example.a061_datepickerdialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity {
	final int DIALOG_ID_DATEPICKER = 1;
	final int DIALOG_ID_TIMEPICKER = 2;
	TextView tv;
	private TextView tv2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.button1);
		tv = (TextView) findViewById(R.id.textView1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ID_DATEPICKER);
			}
		});
		tv2 = (TextView) findViewById(R.id.textView2);
		Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ID_TIMEPICKER);
			}
		});
	} // end of onCreate
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ID_DATEPICKER:
			DatePickerDialog dialog = new DatePickerDialog(this, 
					new OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, 
								int year, int monthOfYear, int dayOfMonth) {
							tv.setText("생일: "+year+"년 "+
								(monthOfYear+1)+"월 "+dayOfMonth+"일");
						}
					}, 2017, 5-1, 5);
			return dialog; // create() 안한다.
		case DIALOG_ID_TIMEPICKER:
			TimePickerDialog dialog2 = new TimePickerDialog(this, // this써야함
					new OnTimeSetListener() {
						@Override
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							tv2.setText("점심시간: "+hourOfDay+":"+minute);
						}
					}, 12, 50, true);
			return dialog2;
		}
		return null;
	}
} // end of class
