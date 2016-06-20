package cn.lyj.mybysj.bean;

import java.io.Serializable;

public class Floor implements Serializable {
	private String floor;

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	@Override
	public String toString() {
		return "Floor [floor=" + floor + "]";
	}

	public Floor(String floor) {
		super();
		this.floor = floor;
	}

	public Floor() {
		super();
	}
	
	
}
