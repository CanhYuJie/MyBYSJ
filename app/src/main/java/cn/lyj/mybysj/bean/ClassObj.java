package cn.lyj.mybysj.bean;

import java.io.Serializable;

public class ClassObj implements Serializable {
	private String className;
	private String bMajor;
	private String bDepartment;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getbMajor() {
		return bMajor;
	}
	public void setbMajor(String bMajor) {
		this.bMajor = bMajor;
	}
	public String getbDepartment() {
		return bDepartment;
	}
	public void setbDepartment(String bDepartment) {
		this.bDepartment = bDepartment;
	}
	@Override
	public String toString() {
		return "ClassObj [className=" + className + ", bMajor=" + bMajor
				+ ", bDepartment=" + bDepartment + "]";
	}
	public ClassObj(String className, String bMajor, String bDepartment) {
		super();
		this.className = className;
		this.bMajor = bMajor;
		this.bDepartment = bDepartment;
	}
	public ClassObj() {
		super();
	}
}
