package org.kpu.ng.domain;

public class EmployeeVO {
	private int eno;
	private String name;
	private int age;
	private String phoneNum;
	private String position;
	private String status;
	private Integer level;
	private Integer dno;
	private String deptName;
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getEno() {
		return eno;
	}
	public void setEno(int eno) {
		this.eno = eno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getDno() {
		return dno;
	}
	public void setDno(Integer dno) {
		this.dno = dno;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return "EmployeeVO [eno=" + eno + ", name=" + name + ", age=" + age + ", PhoneNum=" + phoneNum + ", position="
				+ position + ", status=" + status + ", dno=" + dno + ", deptName=" + deptName + ", level=" + level
				+ "]";
	}
}
