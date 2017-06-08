package org.kpu.ng.domain;

import java.util.Date;

public class LogVO {
	private int lno;
	private int eno;
	private int dno;
	private String deptName;
	private String position;
	private String name;
	private String location;
	private String result;
	private Date time;
	public int getLno() {
		return lno;
	}
	public void setLno(int lno) {
		this.lno = lno;
	}
	public int getEno() {
		return eno;
	}
	public void setEno(int eno) {
		this.eno = eno;
	}
	public int getDno() {
		return dno;
	}
	public void setDno(int dno) {
		this.dno = dno;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "LogVO [lno=" + lno + ", eno=" + eno + ", dno=" + dno + ", deptName=" + deptName + ", position="
				+ position + ", name=" + name + ", location=" + location + ", result=" + result + ", time=" + time
				+ "]";
	}
	
	
}
