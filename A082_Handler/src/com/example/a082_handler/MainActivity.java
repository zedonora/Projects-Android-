package com.example.a082_handler;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
//	Handler handler = new Handler(); // 화면 변경시 사용
	Handler handler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (msg.what==3) {
				Date d = new Date();
				tv.setText(d.toString());
			} else if (msg.what == 6) {
				String str = (String) msg.obj;
				tv.setText(str);
			}
			return true;
		}
	}); 
	private TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.textView1);
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < 10; i++) {
							final Date d = new Date(); // import java.util
							handler.postDelayed(new Runnable() {
								@Override
								public void run() {
									tv.setText(d.toString());
								}
							},1000);
//							try {
//								Thread.sleep(1000);
//							} catch (InterruptedException e) {
//							}
						}
					} // end of run()
				});
				t.start(); // 쓰레드 시작
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < 10; i++) {
//							Date d = new Date();
//							tv.setText(d.toString());
							handler.sendEmptyMessage(3);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
							}
						}
					}
				});
				t.start();
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < 10; i++) {
							Date d = new Date();
							
//							tv.setText(d.toString());
//							Message msg = new Message();
							Message msg = Message.obtain(); // new Message();
							msg.what = 6;
							msg.obj = d.toString(); // 전달할 내용
							handler.sendMessage(msg); // Message
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
							}
						}
					}
				});
				t.start();
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < 10; i++) {
							final Date d = new Date();
							
							// Activity 의 메서드로 쓰레드에서 화면 변경하는 작업
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									tv.setText(d.toString());
								}
							});
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
							}
						}
					}
				});
				t.start(); // 쓰레드 시작
			}
		});
	} // end of onCreate
} // end of class
