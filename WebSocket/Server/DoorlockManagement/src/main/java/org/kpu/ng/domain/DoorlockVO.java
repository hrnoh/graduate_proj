package org.kpu.ng.domain;


public class DoorlockVO {
	private int dno;
	private String location;
	private String mac;
	private int level;
	
	public int getDno() {
		return dno;
	}
	public void setDno(int dno) {
		this.dno = dno;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return "DoorlockVO [" + "dno=" + dno + ", location=" + location + ", mac=" + mac
				+ ", level=" + level + "]";
	}
}
