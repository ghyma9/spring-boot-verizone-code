package com.ma.springboot.verizone.code.data;

import org.springframework.stereotype.Component;

@Component
public class NumberPairDTO {
	private int x;
	private int y;

	@Override
	public String toString() {
		return String.format("x: %d  y: %d", x, y);
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
