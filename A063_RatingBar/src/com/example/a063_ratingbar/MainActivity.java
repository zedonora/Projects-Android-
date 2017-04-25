package com.example.a063_ratingbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final TextView tv = (TextView) findViewById(R.id.ratingBar1);
		final SeekBar sb = (SeekBar) findViewById(R.id.seekBar1);
		RatingBar rb = (RatingBar) findViewById(R.id.ratingBar1);
		sb.setMax(rb.getMax());
		rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, 
					boolean fromUser) {
				tv.setText("별점: "+rating);
				sb.setProgress((int)rating);
			}
		});
	} // end of onCreate
} // end of class
