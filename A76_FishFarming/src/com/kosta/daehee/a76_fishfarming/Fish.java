package com.kosta.daehee.a76_fishfarming;

import java.util.Random;

public class Fish {
	private int x;
	private int y;
	private int endX, endY;
	Dir dirUpDown;
	Dir dirLeftRight;
	private int type; // 이미지 리소스 Id 저장변수
	private int life; // 추후개발 - 먹이를 먹지않고있으면 life 값이 서서히 감소
					  // 0 이 된다면 fishList.remove(index) 를 하면 될것이다.
	private int speed; // 2 ~ 4
	public Fish(int x, int y, int type, int life) {
		super();
		this.x = x;
		this.y = y;
		this.type = type;
		this.life = life;
	}
	public Fish(int x, int y, int endX, int endY, int type, int life, int speed) {
		super();
		this.x = x;
		this.y = y;
		this.type = type;
		this.life = life;
		this.endX = endX;
		this.endY = endY;
		this.speed = speed;
		
		if (x <= endX) setDirLeftRight(Dir.RIGHT);
		else setDirLeftRight(Dir.LEFT);
		
		if (y <= endY) setDirUpDown(Dir.DOWN);
		else setDirUpDown(Dir.UP);
	}
	
	public void changeDest(int endX, int endY) {
		this.endX = endX;
		this.endY = endY;
		this.speed = (new Random()).nextInt(3)+2;
		
		if (x <= endX) setDirLeftRight(Dir.RIGHT);
		else setDirLeftRight(Dir.LEFT);
		
		if (y <= endY) setDirUpDown(Dir.DOWN);
		else setDirUpDown(Dir.UP);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getEndX() {
		return endX;
	}
	public void setEndX(int endX) {
		this.endX = endX;
	}
	public int getEndY() {
		return endY;
	}
	public void setEndY(int endY) {
		this.endY = endY;
	}
	public Dir getDirUpDown() {
		return dirUpDown;
	}
	public void setDirUpDown(Dir dirUpDown) {
		this.dirUpDown = dirUpDown;
	}
	public Dir getDirLeftRight() {
		return dirLeftRight;
	}
	public void setDirLeftRight(Dir dirLeftRight) {
		this.dirLeftRight = dirLeftRight;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
