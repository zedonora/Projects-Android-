package com.example.a76_game;

import java.io.Serializable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	MediaPlayer mp; // 음악 재생을 위한 객체
	int musicposition; // 음악 재생 위치
	private int spPosition; // 리스트뷰 클릭 인덱스
	
	// 생명주기 onCreate() 오버라이드 메서드
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 커스텀 리스트뷰에서 사용될 뮤직 객체 생성
		final Music music[] = { new Music("송아지", R.drawable.cow1,R.raw.cowmusic, "동요",0),
				new Music("코끼리", R.drawable.ele1,R.raw.elephantmusic, "동요",1), 
				new Music("사자", R.drawable.lion1,R.raw.lionmusic , "동요",2),
				new Music("원숭이", R.drawable.mon1,R.raw.monkeymusic , "동요",3),
				new Music("양", R.drawable.sheep1,R.raw.sheepmusic , "동요",4),
				new Music("호랑이", R.drawable.tiger1,R.raw.tigermusic , "동요",5)};
		
		ListView lv = (ListView)findViewById(R.id.listView1); // AdapterView 선언
		// 커스텀어댑터 생성
		MyAdapter ma = new MyAdapter(getApplicationContext(), R.layout.myrow, music); 
		// 커스텀어댑터 연결
		lv.setAdapter(ma);
		
		final ImageView mainImage = (ImageView) findViewById(R.id.imageView1);
		final TextView tv = (TextView) findViewById(R.id.textView1);
		Button startB = (Button) findViewById(R.id.button1);
		mainImage.setImageResource(music[0].img);
		tv.setText(music[0].name);
		
		// 리스트뷰 클릭시
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				spPosition = position; // 클릭한 인덱스 값 저장
				tv.setText(music[position].name);
				mainImage.setImageResource(music[position].img);
				if(mp != null){ // 기존에 음악이 재생중일 경우
					mp.stop(); // 재생중지
					mp = MediaPlayer.create(getApplicationContext(), music[position].bgm);
					mp.setLooping(true); // 반복재생 안함
					mp.start();
				}else{
					mp = MediaPlayer.create(getApplicationContext(), music[position].bgm);
					mp.setLooping(true); // 반복재생 안함
					mp.start();
				}
			}
		});
		
		// 게임 시작 버튼 클릭시
		startB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// GameActivity 화면이동을 위한 Intent
				Intent intent = new Intent(getApplicationContext(), GameActivity.class);
				intent.putExtra("music", spPosition); // intent에 리스트뷰 클릭 인덱스 담기
				intent.putExtra("name", music[spPosition].name); // intent에 리스트뷰 클릭 인덱스의 노래제목 담기 
				startActivity(intent); // intent 시작
				  
				finish(); // 현재 activity 닫기
			}
		});
	
	}//end of onCreate
	
	// 뒤로가기 키를 누를시 호출되는 오버라이드 메서드
	@Override
    public void onBackPressed() { 
		showDialog(1);// 종료 여부를 묻는 다이얼로그 띄우기
		onPause(); // 생명주기중 onPause() 메서드 호출
    }

	// 커스텀 다이얼로그 생성 오버라이드 메서드
	@Override
    protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		LayoutInflater lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = lif.inflate(R.layout.exit_dialog, null);
	    dialog.setView(view);

	    // 다이얼로그의 네 버튼을 클릭시 호출되는 메서드
	    dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
	    	
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	        	finish(); // 현재 activity 닫기 후 앱 종료
	        }
	    });
	    
	    // 다이얼로그의 아니요 버튼을 클릭시 호출되는 메서드
	    dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				onResume(); // 생명주기중 onResume() 메서드 호출
			}
	    });
	    
	    // 커스텀 다이얼로그 생성
	    return dialog.create();
	}
   
	// 생명주기 onPause() 오버라이드 메서드
	@Override
    protected void onPause() {
		super.onPause();
        musicposition = mp.getCurrentPosition(); // 재생중인 음악의 위치 저장
        mp.pause(); // 재생중인 음악 일시정지
	}
   
	// 생명주기 onResume() 오버라이드 메서드
	@Override
	protected void onResume() {
		super.onRestart();
		if(mp != null){ // 기존 음악이 일시정지 중일 경우
			mp.seekTo(musicposition); // 저장된 위치로 이동
			mp.start(); // 음악 재생
		} else { // 처음 로딩시 음악 자동 재생
			mp = MediaPlayer.create(getApplicationContext(), R.raw.cowmusic);
			mp.setLooping(true);
			mp.start();
		}
	}
   
	// 생명주기 onDestroy() 오버라이드 메서드
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mp.stop(); // 음악 정지
	}
	
	// 커스텀 리스트뷰 동작부분
	class MyAdapter extends BaseAdapter{
		int row;
		Music[] music;
		LayoutInflater lif; 
		
		// 생성자
		public MyAdapter(Context context, int row, Music[] music) {
			this.row = row;
			this.music = music;
			// LayoutInflater 객체 얻어오기
			this.lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		// (필수) ListView 에 몇 항목을 보여줄지
		public int getCount() { 
			return music.length;
		}
		// (선택) position번째의 내용를 리턴
		public Object getItem(int position) { 
			return music[position];
		}
		// (선택) position번째의 유니크한 id를 리턴
		public long getItemId(int position) { 
			return position;
		}
		// (필수) 해당 position번째의 레이아웃에 어떤 데이터를 보여줄지를 설정
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) { // 처음 한번만 
				convertView = lif.inflate(row, null); // xml 레이아웃을 객체로 변환
			}
			// 사용자가 클릭시 Music 클래스 객체 내의 정보를 활용해 커스텀 리스트뷰 리턴
			ImageView im = (ImageView) convertView.findViewById(R.id.imageView1);
			im.setImageResource(music[position].img);
			TextView tv1 = (TextView)convertView.findViewById(R.id.textView1);
			tv1.setText(music[position].name);
			TextView tv2 = (TextView)convertView.findViewById(R.id.textView2);
			tv2.setText(music[position].info);
			
			// 만들어진 커스텀 리스트뷰 리턴
			return convertView;
		}
	}
}//end of class

// 커스텀 리스트뷰에서 사용될 클래스
class Music implements Serializable{
	String name = "";
	int img;
	int bgm;
	String info="";
	int musicPosi;
	public Music(String name, int img, int bgm, String info,int musicPosi) {
		this.name = name;
		this.img = img;
		this.info = info;
		this.bgm = bgm;
		this.musicPosi= musicPosi;
	}
} // end of class Music
