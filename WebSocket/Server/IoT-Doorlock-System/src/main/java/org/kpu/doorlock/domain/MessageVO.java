package org.kpu.doorlock.domain;

import com.google.gson.Gson;

public class MessageVO {
	private String type;
	private String data;		// 여기에 실제 JSON Data가 들어있음
	
	public static MessageVO convMessage(String json) {
		MessageVO message = new MessageVO();
		Gson gson = new Gson();
		message = gson.fromJson(json, MessageVO.class);
		
		return message;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "MessageVO [type=" + type + ", data=" + data + "]";
	}
}
