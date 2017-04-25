package com.example.a060_datepicker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DatePicker dp = (DatePicker) findViewById(R.id.datePicker1);
		CalendarView cv = dp.getCalendarView();
		cv.setOnDateChangeListener(new OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView view, 
					int year, int month, int dayOfMonth) {
				Toast.makeText(getApplicationContext(), 
						year+"년 "+(month+1)+"월 "+dayOfMonth+"일", Toast.LENGTH_SHORT).show();
			}
		});
		
		TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
		
		tp.setIs24HourView(true);
		tp.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				setTitle(hourOfDay+"시 "+minute+"분");
			}
		});
		
	} // end of onCreate
} // end of class
