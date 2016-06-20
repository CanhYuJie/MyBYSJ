package cn.lyj.mybysj.bean;

import java.io.Serializable;

public class Major implements Serializable {
	private String major;
	private String bDepartment;
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getbDepartment() {
		return bDepartment;
	}
	public void setbDepartment(String bDepartment) {
		this.bDepartment = bDepartment;
	}
	@Override
	public String toString() {
		return "Major [major=" + major + ", bDepartment=" + bDepartment + "]";
	}
	public Major(String major, String bDepartment) {
		super();
		this.major = major;
		this.bDepartment = bDepartment;
	}
	public Major() {
		super();
	}
}
