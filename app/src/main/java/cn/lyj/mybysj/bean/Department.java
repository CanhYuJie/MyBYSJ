package cn.lyj.mybysj.bean;

import java.io.Serializable;

public class Department implements Serializable {
	private String department;

	@Override
	public String toString() {
		return "Department [department=" + department + "]";
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Department(String department) {
		super();
		this.department = department;
	}

	public Department() {
		super();
	}
}
