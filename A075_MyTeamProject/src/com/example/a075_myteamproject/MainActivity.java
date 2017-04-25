package com.example.a075_myteamproject;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	int DIALOG_OPTION=1;
	
	TextView tvClick;
	TextView tvName;
	TextView tvButton;
	ImageButton ibOption;
	TextView tvEnergy;
	TextView tvClean;
	TextView tvSense;
	TextView tvIntel;
	ProgressBar pb;
	ImageView ivMain;
	ImageButton ibCode;
	ImageButton ibClean;
	ImageButton ibOut;
	ImageButton ibSleep;
	ImageButton ibGame;
	ImageButton ibEat;

	MediaPlayer mp;
	int energy, clean, sense, intel;
	final int MAX = 100;
	final int MIN = 0;
	final int HALF = 50;
	int environ = -1;
	boolean isWin;
	Random ran = new Random();
	private Switch swSound;
	int position = 0;
	int clickCountDown = 30;
	Handler handler = new Handler();
	boolean checkTest;
	int ending = -1;

	Runnable r = new Runnable() {
		@Override
		public void run() {
			setState();
			setEnable(true);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myFindViewById();
		init();
		mp = MediaPlayer.create(getApplicationContext(), R.raw.main_bgm);
		mp.setLooping(true);
		mp.start();
		setState();

		ibCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setEnable(false);
				energy -= 1;
				clean -= 1;
				sense -= 2;
				intel += 2;
				handler.postDelayed(r, 3000);

				clickCountDown--;
				checkState();
			}
		});
		ibOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setEnable(false);
				environ = ran.nextInt(2);
				switch (environ) {
				case 0: // 벚꽃
					energy -= 1;
					clean -= 1;
					sense += 2;
					break;
				case 1: // 비
					energy -= 2;
					clean -= 2;
					sense += 2;
					break;
				}
				handler.postDelayed(r, 3000);
				clickCountDown--;
				checkState();
				environ = -1;
			}
		});
		ibClean.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setEnable(false);
				energy -= 1;
				clean = MAX;

				clickCountDown--;
				handler.postDelayed(r, 3000);
				checkState();
			}
		});
		ibSleep.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setEnable(false);
				energy = MAX;
				clean -= 1;

				clickCountDown--;
				setState();
				handler.postDelayed(r, 3000);
				checkState();
			}
		});
		ibGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// startActivity(intent); // activity 값을 리턴
				setEnable(false);
				switch (environ) {
				case -1: // 이김
					energy -= 1;
					clean -= 1;
					sense -= 2;
					intel += 4;
					break;
				case 1: // 짐
					energy -= 1;
					clean -= 2;
					sense += 2;
					intel -= 2;
					break;
				}

				handler.postDelayed(r, 3000);
				clickCountDown--;
				setState();
				checkState();
			}
		});
		ibEat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setEnable(false);
				energy += energy / 2;
				clean -= 1;

				clickCountDown--;
				handler.postDelayed(r, 3000);
				checkState();
			}
		});
	} // end of onCreate

	public void setState() {
		tvEnergy.setText("\t: " + energy);
		tvClean.setText("\t: " + clean);
		tvSense.setText("\t: " + sense);
		tvIntel.setText("\t: " + intel);
	}

	public void init() {
		isWin = false;
		energy = MAX;
		clean = MAX;
		sense = HALF;
		intel = HALF;
		pb.setVisibility(ProgressBar.INVISIBLE);
	}

	public void checkState() {
		if (energy >= MAX) {
			energy = MAX;
		} else if (clean >= MAX) {
			clean = MAX;
		} else if (sense <= 0) {
			sense = 0;
		} else if (intel <= 0) {
			intel = 0;
		}
		tvClick.setText("" + clickCountDown);
		if (clickCountDown > 0) {
			if (energy <= 0) {
				Toast.makeText(getApplicationContext(), "세포가 죽었어 ㅜㅜ", Toast.LENGTH_SHORT).show();
				setEnable(false);
				gameOver();
			} else if (clean <= 0) {
				Toast.makeText(getApplicationContext(), "으 냄새~! 세포야 나 너랑 못살거 같아", Toast.LENGTH_SHORT).show();
				setEnable(false);
				gameOver();
			} else if (sense >= MAX) {
				Toast.makeText(getApplicationContext(), "헐~! 세포가 나갔어 ㅜㅜ", Toast.LENGTH_SHORT).show();
				setEnable(false);
				gameOver();
			} else if (intel >= MAX) {
				Toast.makeText(getApplicationContext(), "세포가 사이코가 됐다.", Toast.LENGTH_SHORT).show();
				setEnable(false);
				gameOver();
			}
		} else {
			Toast.makeText(getApplicationContext(), "세포가 분열했어. 우와", Toast.LENGTH_SHORT).show();
			isWin = true;
			setEnable(false);
			gameOver();
		}
	}

	public void gameOver() {
		// 새로운Dialog 실행
		if (isWin) {
			// 이겼을 때 Dialog
			setEnable(false);
		} else {
			// 졌을 때 Dialog
			setEnable(false);
		}
	}

	public void myFindViewById() {
		tvClick = (TextView) findViewById(R.id.TextViewClick);
		tvName = (TextView) findViewById(R.id.textView1);
		tvButton = (TextView) findViewById(R.id.textView2);
		ibOption = (ImageButton) findViewById(R.id.ImageButton1);

		tvEnergy = (TextView) findViewById(R.id.textView3);
		tvClean = (TextView) findViewById(R.id.textView4);
		tvSense = (TextView) findViewById(R.id.textView5);
		tvIntel = (TextView) findViewById(R.id.textView6);

		pb = (ProgressBar) findViewById(R.id.ProgressBar1);

		ivMain = (ImageView) findViewById(R.id.imageView1);

		ibCode = (ImageButton) findViewById(R.id.imageButton2);
		ibOut = (ImageButton) findViewById(R.id.imageButton3);
		ibClean = (ImageButton) findViewById(R.id.imageButton4);
		ibSleep = (ImageButton) findViewById(R.id.imageButton5);
		ibGame = (ImageButton) findViewById(R.id.imageButton6);
		ibEat = (ImageButton) findViewById(R.id.imageButton7);
	}

	public void setEnable(boolean isEnable) {
		pb.setVisibility(isEnable ? ProgressBar.INVISIBLE : ProgressBar.VISIBLE);
		ibCode.setEnabled(isEnable);
		ibOut.setEnabled(isEnable);
		ibClean.setEnabled(isEnable);
		ibSleep.setEnabled(isEnable);
		ibGame.setEnabled(isEnable);
		ibEat.setEnabled(isEnable);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		LayoutInflater lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = lif.inflate(R.layout.activity_option, null);
		swSound = (Switch) view.findViewById(R.id.switch1);
		swSound.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// 소리남
					mp.seekTo(position);
					mp.start();
				} else {
					// 무음
					position = mp.getCurrentPosition();
					mp.pause();
				}
			}
		});

		Log.d("dialog", "실행됬슴다 setView전");
		dialog.setView(view);
		return dialog.create();
	}

	protected void onPause() {
		super.onPause();
		if (mp != null) {
			mp.stop();
		}
	}
} // end of class
