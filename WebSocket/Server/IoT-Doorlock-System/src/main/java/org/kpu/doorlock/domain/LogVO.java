package org.kpu.doorlock.domain;

import java.sql.Timestamp;
import java.util.Date;

public class LogVO {
	private int lno;
	private int eno;
	private String message;
	private Timestamp time;
	
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "LogVO [lno=" + lno + ", eno=" + eno + ", message=" + message + ", time=" + time + "]";
	}
	
}
