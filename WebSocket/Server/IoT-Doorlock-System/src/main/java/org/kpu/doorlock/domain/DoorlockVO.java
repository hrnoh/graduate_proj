package org.kpu.doorlock.domain;

import org.springframework.web.socket.WebSocketSession;

import com.google.gson.annotations.Expose;


public class DoorlockVO {
	WebSocketSession session;
	@Expose
	private int dno;
	@Expose
	private String location;
	@Expose
	private String mac;
	@Expose
	private int level;
	@Expose
	private int isOnline;
	
	public DoorlockVO() { isOnline = 0; }
	
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
	public WebSocketSession getSession() {
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}
	@Override
	public String toString() {
		return "DoorlockVO [session=" + session + ", dno=" + dno + ", location=" + location + ", mac=" + mac
				+ ", level=" + level + ", isOnline=" + isOnline + "]";
	}
}
