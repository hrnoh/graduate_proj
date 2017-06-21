package org.kpu.ng.domain;

public class DepartmentVO {
	private int dno;
	private String deptName;
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
	@Override
	public String toString() {
		return "DepartmentVO [dno=" + dno + ", deptName=" + deptName + "]";
	}
	
	
}
