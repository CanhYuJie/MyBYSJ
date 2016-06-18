package cn.lyj.mybysj.bean;

import java.io.Serializable;

public class User implements Serializable {
	private String userName;
	private String passWord;
	private String remark;
	
	
	public User() {
		super();
	}
	
	 
	public User(String userName, String passWord, String remark) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.remark = remark;
	}
	

	@Override
	public String toString() {
		return "User [userName=" + userName + ", passWord=" + passWord
				+ ", remark=" + remark + "]";
	}


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
