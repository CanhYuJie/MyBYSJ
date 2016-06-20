package cn.lyj.mybysj.bean;

import java.io.Serializable;

public class BedRoom implements Serializable {
	private String b_floor;
	private String b_area;
	private String intro;
	public String getB_floor() {
		return b_floor;
	}
	public void setB_floor(String b_floor) {
		this.b_floor = b_floor;
	}
	public String getB_area() {
		return b_area;
	}
	public void setB_area(String b_area) {
		this.b_area = b_area;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	@Override
	public String toString() {
		return "BedRoom [b_floor=" + b_floor + ", b_area=" + b_area
				+ ", intro=" + intro + "]";
	}
	public BedRoom(String b_floor, String b_area, String intro) {
		super();
		this.b_floor = b_floor;
		this.b_area = b_area;
		this.intro = intro;
	}
	public BedRoom() {
		super();
	}
}
