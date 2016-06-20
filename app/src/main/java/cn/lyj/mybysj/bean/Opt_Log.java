package cn.lyj.mybysj.bean;

import java.io.Serializable;

public class Opt_Log implements Serializable {
	private String optUser;
	private String optType;
	private String optTime;
	
	public Opt_Log(String optUser, String optType, String optTime) {
		super();
		this.optUser = optUser;
		this.optType = optType;
		this.optTime = optTime;
	}
	public Opt_Log() {
		super();
	}
	@Override
	public String toString() {
		return "Opt_Log [optUser=" + optUser + ", optType=" + optType
				+ ", optTime=" + optTime + "]";
	}
	public String getOptUser() {
		return optUser;
	}
	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	public String getOptTime() {
		return optTime;
	}
	public void setOptTime(String optTime) {
		this.optTime = optTime;
	}
	
}
