package cn.lyj.mybysj.bean;

import java.io.Serializable;

public class Student implements Serializable {
	private String name;
	private String uid;
	private String sex;
	private String b_class;
	private String b_department;
	private String b_bedroom;
	private String remark;
	private String blackList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getB_class() {
		return b_class;
	}
	public void setB_class(String b_class) {
		this.b_class = b_class;
	}
	public String getB_department() {
		return b_department;
	}
	public void setB_department(String b_department) {
		this.b_department = b_department;
	}
	public String getB_bedroom() {
		return b_bedroom;
	}
	public void setB_bedroom(String b_bedroom) {
		this.b_bedroom = b_bedroom;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBlackList() {
		return blackList;
	}
	public void setBlackList(String blackList) {
		this.blackList = blackList;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", uid=" + uid + ", sex=" + sex
				+ ", b_class=" + b_class + ", b_department=" + b_department
				+ ", b_bedroom=" + b_bedroom + ", remark=" + remark
				+ ", blackList=" + blackList + "]";
	}
	public Student(String name, String uid, String sex, String b_class,
			String b_department, String b_bedroom, String remark,
			String blackList) {
		super();
		this.name = name;
		this.uid = uid;
		this.sex = sex;
		this.b_class = b_class;
		this.b_department = b_department;
		this.b_bedroom = b_bedroom;
		this.remark = remark;
		this.blackList = blackList;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
