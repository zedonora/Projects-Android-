package com.kosta.daehee.a76_fishfarming;

import java.util.ArrayList;
import java.util.Random;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

public class GameView extends View {
	Toast t = null;
	Context context;
	Paint paint;
	ArrayList<Fish> fishList = new ArrayList<Fish>();
	ArrayList<Feed> feedList = new ArrayList<Feed>();
	ArrayList<Feed> sinkedFeedList = new ArrayList<Feed>();
	ArrayList<Point> bubbleList = new ArrayList<Point>();
	Canvas myCan;
	Bitmap myBit;
	Random rand = new Random();
	MediaPlayer mp;
	int point = 0;
	int maxPoint = 0;
	
	RectF fishRect;
	RectF feedRect;

	Handler handler = new Handler();
	
	Runnable runBubble = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			handler.postDelayed(runBubble, 500);
			final Point point = new Point(rand.nextInt(myCan.getWidth()-100), myCan.getHeight()-100);
			bubbleList.add(point);
			ValueAnimator va = ValueAnimator.ofInt(1000, 0);
			va.setDuration(2000);// 애니메이션 하는 시간
			va.setInterpolator(new LinearInterpolator());
			va.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					// TODO Auto-generated method stub
					point.y = (Integer) animation.getAnimatedValue();// 애니메이터
					// 변경된 값
					// 얻어오기
					if (point.y == 0) {
						bubbleList.remove(point);
					}
					invalidate();
				}
			});
			// va.setRepeatCount(0);// 반복횟수
			va.setRepeatMode(ValueAnimator.REVERSE);
			va.start();// 애니메이션 시작
			invalidate();
		}
	};
	
	Runnable runLife = new Runnable() {
		@Override
		public void run() {
			for (int i = fishList.size()-1; i >= 0; i--) {
				Fish f = fishList.get(i);
				f.setLife(f.getLife()-1);
				if (f.getLife() <= 0) {
					fishList.remove(i);
//					if (t!=null) t.cancel();
//					t = Toast.makeText(getContext(), "물고기가 죽었어요.. ㅠㅠ", Toast.LENGTH_SHORT);
//					t.show();
					point -= 100;
					showFishToast();
				}
			}
			handler.postDelayed(runLife, 1000);
		}
	};
	
	Runnable runFeed = new Runnable() {
		@Override
		public void run() {
			if (feedList.size() >= 1) {
				// feedList.remove(0);
			}
			invalidate();
		}
	};
	
	Runnable runPoint = new Runnable() {
		@Override
		public void run() {
			if (point < 0) {
				point = 0;
				fishList.clear();
				if (t!=null) t.cancel();
				t = Toast.makeText(context, "점수 0, 게임오버", Toast.LENGTH_SHORT);
				t.show();
			}
			((MainActivity)context).tv.setText("물고기 : " + fishList.size() + "마리    " + "점수 : " + point + "    최고점수 : " + maxPoint);
			if (fishList.size() >= 1) { 
				point++;
				if (point > maxPoint) {
					maxPoint = point;
				}
			}
			handler.postDelayed(runPoint, 100);
		}
	};

	// 사료 이미지
	Bitmap feed1 = BitmapFactory.decodeResource(getResources(), R.drawable.feed1);
	Bitmap feed2 = BitmapFactory.decodeResource(getResources(), R.drawable.feed2);
	/*Bitmap refeed1 = Bitmap.createScaledBitmap(feed1, 30, 30, true);
	Bitmap refeed2 = Bitmap.createScaledBitmap(feed2, 30, 30, true);*/
	Bitmap[] refeed = {Bitmap.createScaledBitmap(feed1, 30, 30, true), 
			Bitmap.createScaledBitmap(feed2, 30, 30, true)};
	int feedSelNum = 0; // 0 or 1
	
	// 버블 이미지
	Bitmap bubble = BitmapFactory.decodeResource(getResources(), R.drawable.bubble);
	Bitmap bubbleBitmap = Bitmap.createScaledBitmap(bubble, 80, 80, true);

	// 물고기 이미지
	Bitmap[][] fishBitmap = {
			{BitmapFactory.decodeResource(getResources(), R.drawable.fish_0_left), BitmapFactory.decodeResource(getResources(), R.drawable.fish_0_right) },
			{BitmapFactory.decodeResource(getResources(), R.drawable.fish_1_left), BitmapFactory.decodeResource(getResources(), R.drawable.fish_1_right) },
			{BitmapFactory.decodeResource(getResources(), R.drawable.fish_2_left), BitmapFactory.decodeResource(getResources(), R.drawable.fish_2_right) },
	};
	
	int fishWidth = fishBitmap[0][0].getWidth();
	int fishHeight = fishBitmap[0][0].getHeight();
	int feedWidth = refeed[0].getWidth();
	int feedHeight = refeed[0].getHeight();

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		paint = new Paint();
	}
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		paint = new Paint();
		mp = MediaPlayer.create(getContext(), R.raw.bgm);
		mp.setLooping(true);
	}
	public GameView(Context context) {
		super(context);
		this.context = context;
		paint = new Paint();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		myBit = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		myCan = new Canvas(myBit);
		handler.post(runBubble);
		handler.post(runLife);
		handler.post(runPoint);
		if (mp!=null) mp.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	      final Feed feed = new Feed(feedSelNum, (int) event.getX(), (int) event.getY());
	      switch (event.getAction()) {
	      case MotionEvent.ACTION_DOWN:
	         feedList.add(feed);
	         point--;
	         ValueAnimator va = ValueAnimator.ofInt(100, 1500);

	         va.setDuration(5000); 
	         va.setInterpolator(new LinearInterpolator());
	         va.addUpdateListener(new AnimatorUpdateListener() {
	            @Override
	            public void onAnimationUpdate(ValueAnimator animation) {
	               for (int i = 0; i < feedList.size(); i++) {
	                  feed.setY((Integer) animation.getAnimatedValue());
	                  invalidate();
	               }
	            }
	         });
	         handler.postDelayed(runFeed, 10000);
	         va.start();
	         
	      case MotionEvent.ACTION_MOVE:
	         feedList.add(feed);
	         point--;
	         ValueAnimator va2 = ValueAnimator.ofInt(100, 1500);

	         va2.setDuration(5000); 
	         va2.setInterpolator(new LinearInterpolator());
	         va2.addUpdateListener(new AnimatorUpdateListener() {
	            @Override
	            public void onAnimationUpdate(ValueAnimator animation) {
	               for (int i = 0; i < feedList.size(); i++) {
	                  feed.setY((Integer) animation.getAnimatedValue());
	                  invalidate();
	               }
	            }
	         });
	         handler.postDelayed(runFeed, 10000);
	         va2.start();
	      }
	      return true;

	}

	@Override

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		clear();

		// 물고기-먹이 충돌검사
		for (int i = fishList.size()-1; i >= 0; i--) {
			fishRect = new RectF(fishList.get(i).getX(), 
					fishList.get(i).getY()+fishHeight/4, 
					fishList.get(i).getX()+fishWidth, 
					fishList.get(i).getY()+fishHeight/4*3);
			
			// 각 물고기들이 먹이를 contains() 하는지
			for (int j = feedList.size()-1; j >= 0; j--) {
				feedRect = new RectF(feedList.get(j).getX(), 
						feedList.get(j).getY(), 
						feedList.get(j).getX()+feedWidth/4, 
						feedList.get(j).getY()+feedHeight);
				
				if (fishRect.contains(feedRect)) {
					Log.e("먹었다 !!!", "물고기 : " + fishList.get(i).getX() + ", " + fishList.get(i).getY() + " / " + "먹이 : " + feedList.get(j).getX() + ", " + feedList.get(j).getY());
					if (fishList.get(i).getLife() < 15) fishList.get(i).setLife(fishList.get(i).getLife()+1);
					feedList.remove(j);
					point++;
					// continue;
				}
			}
		}
		fishRect = null;
		feedRect = null;

		// 물고기 이동 / 그리기
		for (int i = 0; i < fishList.size(); i++) {
			int x = fishList.get(i).getX();
			int y = fishList.get(i).getY();
			int endX = fishList.get(i).getEndX();
			int endY = fishList.get(i).getEndY();
			int speed = fishList.get(i).getSpeed();

			boolean finishX = false; // 상하,
			boolean finishY = false; // 좌우 이동의 완료 여부값을 만들고 이두가지가 모두 true 라면 물고기의 목적지를 reset 하는 방식

			// X 좌표 이동 (진행방향의 좌우 판단)
			if (fishList.get(i).getDirLeftRight() == Dir.RIGHT) { // 진행방향이 오른쪽이라면
				if (x < endX) {
					// x+=2;
					x+=speed;
				} else {
					finishX = true; // 도달시 
				}
			} else if (fishList.get(i).getDirLeftRight() == Dir.LEFT) { // 진행방향이 왼쪽이라면
				if (x > endX) {
					// x-=2;
					x-=speed;
				} else {
					finishX = true;
				}
			}

			// Y 좌표 이동 (진행방향의 상하판단)
			if (fishList.get(i).getDirUpDown() == Dir.DOWN) {
				if (y < endY) {
					// y+=2;
					y+=speed;
				} else {
					finishY = true;
				}
			} else if (fishList.get(i).getDirUpDown() == Dir.UP) {
				if (y > endY) {
					// y-=2;
					y-=speed;
				} else {
					finishY = true;
				}
			}

			if (finishX && finishY) { // 목적지 도달 시 !
				int randX = rand.nextInt(myCan.getWidth()-200);
				int randY = rand.nextInt(myCan.getHeight()-400);
				fishList.get(i).changeDest(randX, randY);
//				if (t!=null) t.cancel();
//				t = Toast.makeText(context, "목적지 도착 -> 변경", Toast.LENGTH_SHORT);
//				t.show();
			}

			fishList.get(i).setX(x);
			fishList.get(i).setY(y);

			int isLeft = 0;
			if (fishList.get(i).getDirLeftRight() == Dir.RIGHT) {
				isLeft = 1;
			}

			int bitmapIndex = fishList.get(i).getType();
			myCan.drawBitmap(fishBitmap[bitmapIndex][isLeft], x, y, paint);
			
			paint.setStyle(Style.FILL);
			paint.setColor(Color.RED);
			myCan.drawRect(fishList.get(i).getX()-20, 
					fishList.get(i).getY(), 
					fishList.get(i).getX(), 
					fishList.get(i).getY() + fishList.get(i).getLife()*10, 
					paint);
			paint.setStrokeWidth(4);
			paint.setStyle(Style.STROKE);
			paint.setColor(Color.BLACK);
			myCan.drawRect(fishList.get(i).getX()-20, 
							fishList.get(i).getY(), 
							fishList.get(i).getX(), 
							fishList.get(i).getY() + fishList.get(i).getLife()*10, 
							paint);
		}
		
		// 버블 그리기
		for (int i = 0; i < bubbleList.size(); i++) {
			canvas.drawBitmap(bubbleBitmap, bubbleList.get(i).x, bubbleList.get(i).y, paint);
		}

		// 먹이 투하 / 그리기
		for (int i = 0; i < feedList.size(); i++) {
	         canvas.drawBitmap(refeed[feedList.get(i).getType()], feedList.get(i).getX(), feedList.get(i).getY(), paint);
		}

		canvas.drawBitmap(myBit, 0, 0, null);
		invalidate(); // 그리기 작업 마친 후 다시 호출
	}

	public void clear() {
		myBit.eraseColor(Color.argb(0, 0, 0, 0));
		invalidate();
	}

	public void addFish(int resId) { // 물고기 추가 메서드
		Random rand = new Random();
		int x = rand.nextInt(myCan.getWidth()-200);
		int y = rand.nextInt(myCan.getHeight()-400);
		int endX = rand.nextInt(myCan.getWidth()-200);
		int endY = rand.nextInt(myCan.getHeight()-400);
		int speed = rand.nextInt(3)+2;
		fishList.add(new Fish(x, y, endX, endY, resId, 15, speed));
		Log.e("fishList.add()", x + ", " + y + ", " + endX + ", " + endY);
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (t != null) t.cancel();
		if (mp != null) mp.stop();
	}
	
	public void showFishToast() {
		View toastView = ((MainActivity)context).getLayoutInflater()
				.inflate(R.layout.toast_view, (ViewGroup)findViewById(R.id.relativedeath));
        
        if (t != null) t.cancel();
        t = new Toast(context);
        t.setDuration(Toast.LENGTH_SHORT);

        ImageView ivDeath = (ImageView) toastView.findViewById(R.id.imageDeath);
        ivDeath.setImageResource(R.anim.frame_animation_death);

        t.setView(toastView);
        t.setGravity(Gravity.CENTER|Gravity.CENTER,0, 0);
        t.show();
        AnimationDrawable a= (AnimationDrawable) ivDeath.getDrawable();
        a.start();
	}
	

} // end of class























