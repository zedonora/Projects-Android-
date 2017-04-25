package com.kosta.daehee.a76_fishfarming;

public class Feed {
	private int type; // 먹이 종류에 따라 다른이미지를 보여주기위한 변수
	private int x;
	private int y;
	public Feed(int type, int x, int y) {
		super();
		this.type = type;
		this.x = x;
		this.y = y;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
}
