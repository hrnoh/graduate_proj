package org.kpu.ng.dto;

import com.google.gson.Gson;

public class MessageDTO {
	private int type;
	private String data;		// 여기에 실제 JSON Data가 들어있음
	
	
	public MessageDTO() {}
	public MessageDTO(int type, String data) {
		super();
		this.type = type;
		this.data = data;
	}

	public static MessageDTO convMessage(String json) {
		MessageDTO message = new MessageDTO();
		Gson gson = new Gson();
		message = gson.fromJson(json, MessageDTO.class);
		
		return message;
	}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
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
		return "MessageDTO [type=" + type + ", data=" + data + "]";
	}
}
