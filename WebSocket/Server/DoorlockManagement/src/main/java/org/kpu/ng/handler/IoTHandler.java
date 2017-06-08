package org.kpu.ng.handler;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kpu.ng.domain.DoorlockVO;
import org.kpu.ng.domain.EmployeeVO;
import org.kpu.ng.domain.LogVO;
import org.kpu.ng.dto.MessageDTO;
import org.kpu.ng.service.DoorlockService;
import org.kpu.ng.service.EmployeeService;
import org.kpu.ng.service.LogService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

@Component
public class IoTHandler extends TextWebSocketHandler {
	private final Logger logger = LogManager.getLogger(getClass());
	private List<WebSocketSession> userList = new ArrayList<WebSocketSession>();
	private List<DoorlockListItem> doorlockList = new ArrayList<DoorlockListItem>();
	
	@Inject
	private DoorlockService doorlockService;
	@Inject
	private EmployeeService employeeService;
	@Inject
	private LogService logService;
	
	public IoTHandler() {
		super();
		this.logger.info("create IoTHandler instance!");
	}
	
	/*
	 *	WebSocket 연결 제어
	 *	afterConnectionEstiablished : 연결 후 호출
	 *	afterConnecitonClosed : 연결 해제 후 호출
	 *	handleTextMessage : 클라이언트 메세지 도착 시 호출
	 * */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		
		// 도어락인 경우 오프라인으로 설정
		DoorlockListItem item = findItemBySession(session);
		if(item != null) {
			doorlockList.remove(item);
		}
		
		// 일반 유저인 경우 유저 삭제
		if(userList.contains(session)) {
			userList.remove(session);
		}
		
		this.logger.info("remove session - " + session.getId());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		
		this.logger.info("새로운 연결 IP : " + session.getRemoteAddress().getHostName());
	}

	/*
	 * Protocol
	 * 1. dInit : 100 (param : mac)
	 * 2. uInit : 200
	 * 3. infoRequest : 300 (param : eno)
	 * 4. remoteOpen : 400 (param : mac)
	 * 5. dRefresh : 500
	 * 6. rLog : 600
	 * 7. error : 700
	 * */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		MessageDTO msg = MessageDTO.convMessage(message.getPayload());
		this.logger.info("메세지 타입 : " + msg.getType() + ", 메세지 내용 : " + msg.getData());
		
		// 도어락 초기화
		if(msg.getType() == 100) {
			String mac = msg.getData();
			dInit(session, mac);
		}
		// 유저 초기화
		else if(msg.getType() == 200) {
			userList.add(session);
			doorlockList();
		}
		// 인증 요청
		else if(msg.getType() == 300) {
			infoResponse(session, Integer.parseInt(msg.getData()));
		}
		// 원격 오픈
		else if(msg.getType() == 400) {
			remoteOpen(msg.getData());
		}
	}

	/*
	 *  Iot Doorlock System Method
	 * 1. dInit : 도어락 초기화 메서드
	 * 2. infoResponse : 사원 정보 반환
	 * */
	public void dInit(WebSocketSession session, String mac) throws Exception {
		DoorlockVO doorlockVO = doorlockService.readByMac(mac);
		MessageDTO msg;
		
		// Error 처리
		if(doorlockVO == null) {
			String errorMsg = "doorlock is not registered. Please register Doorlock (" + mac + ")";
			msg = new MessageDTO(700, errorMsg);
			String msgJson = msg.toJson();
		
			session.sendMessage(new TextMessage(msgJson));
			
			this.logger.info(mac + "은 등록되지 않은 도어락입니다.");
			return;
		}
		
		// 등록 된 도어락인 경우
		Gson gson = new Gson();
		String doorlockJson = gson.toJson(doorlockVO);
		
		msg = new MessageDTO(100, doorlockJson);
		String msgJson = msg.toJson();
		// 응답 전송
		session.sendMessage(new TextMessage(msgJson));
		
		/* 로그 남기
		this.logger.info(msgJson);
		this.logger.info(doorlockVO.getLocation() + " " + session.getRemoteAddress().getHostName() + " 연결 완료.");
		sendLog("INFO: " + doorlockVO.getLocation() + " " + session.getRemoteAddress().getHostName() + " 연결 완료.");
		*/
		
		// 중복 검사
		//doorlockList();
		for(DoorlockListItem item : doorlockList) {
			if(item.getMac().equals(mac))
				return;
		}
		doorlockList.add(new DoorlockListItem(session, mac));
	}
	
	public LogVO infoResponse(WebSocketSession session, int eno) throws Exception {
		DoorlockListItem item = findItemBySession(session);
		DoorlockVO doorlockVO = doorlockService.readByMac(item.getMac());
		EmployeeVO employeeVO = employeeService.readByEno(eno);
		String employeeJson = new Gson().toJson(employeeVO);
		MessageDTO msg;
		
		if(employeeVO == null) {
			String errorMsg = "This employee not exist.";
			msg = new MessageDTO(700, errorMsg);
			String msgJson = msg.toJson();
			
			session.sendMessage(new TextMessage(msgJson));
			
			this.logger.warn(doorlockVO.getLocation() + "에 불법 카드 접근!");
			sendLog("WARNING: " + doorlockVO.getLocation() + "에 불법 카드 접근!");
			return null;
		}
		
		msg = new MessageDTO(300, employeeJson);
		String msgJson = msg.toJson();
		
		session.sendMessage(new TextMessage(msgJson));
		
		this.logger.info(employeeVO.getName() + "이(가) " + doorlockVO.getLocation() + "에 접근하였습니다.");
		sendLog("INFO: " + employeeVO.getName() + "이(가) " + doorlockVO.getLocation() + "에 접근하였습니다.");
		
		// 출입 이력 추가(AOP로 처리)
		LogVO log = new LogVO();
		log.setEno(employeeVO.getEno());
		log.setDno(employeeVO.getDno());
		log.setResult("success");
		return log;
	}

	/*
	 * 
	 * Web User Method
	 * 1. sendLog : 웹 유저에게 로그 전송
	 * 2. doorlockList : 도어락 리스트 전송
	 * 3. remoteOpen : 실시간 도어락 open
	 * */
	public void sendLog(String message) throws Exception {
		MessageDTO msg = new MessageDTO(600, message);
		String msgJson = msg.toJson();
		
		for(WebSocketSession session : userList) {
			session.sendMessage(new TextMessage(msgJson));
		}
	}
	
	public void doorlockList() throws Exception {
		MessageDTO msg = new MessageDTO(500, null);
		String jsonMsg;		
		
		if(doorlockList.isEmpty()) {
			jsonMsg = msg.toJson();
			
			for(WebSocketSession session : userList)
				session.sendMessage(new TextMessage(jsonMsg));
			
			return;
		}
		
		String doorlockListJson = new Gson().toJson(doorlockList);
		msg.setData(doorlockListJson);
		jsonMsg = msg.toJson();

		for(WebSocketSession session : userList)
			session.sendMessage(new TextMessage(jsonMsg));
	}

	public DoorlockVO remoteOpen(String mac) throws Exception {
		DoorlockListItem item = findItemByMac(mac);
		DoorlockVO vo = doorlockService.readByMac(mac);
		WebSocketSession session;
		MessageDTO msg;
		
		if(item == null) {
			this.logger.warn("도어락 원격 개방 실패!");
			sendLog("WARNING: 도어락 원격 개방 실패!");
			return null;
		}
		
		// 원격개방 시그널 전송
		session = item.getSession();
		msg = new MessageDTO(400, null);
		String msgJson = msg.toJson();
		session.sendMessage(new TextMessage(msgJson));
		
		// 로그
		this.logger.info(vo.getLocation() + "(이)가 개방되었습니다.");
		sendLog("INFO: " + vo.getLocation() + "(이)가 개방되었습니다.");
		
		return vo;
	}
	
	public DoorlockListItem findItemBySession(WebSocketSession session) {
		if(doorlockList.isEmpty()) {
			return null;
		}
		
		for(DoorlockListItem item : doorlockList) {
			if(item.getSession().getId().equals(session.getId())) {
				return item;
			}
		}
		
		return null;
	}
	
	public DoorlockListItem findItemByMac(String mac) {
		if(doorlockList.isEmpty()) {
			return null;
		}
		
		for(DoorlockListItem item : doorlockList) {
			if(mac.equals(item.getMac())) {
				return item;
			}
		}
		
		return null;
	}
}

// 도어락 리스트 아이템
class DoorlockListItem {
	WebSocketSession session;
	private String mac;
	
	public DoorlockListItem() {}
	public DoorlockListItem(WebSocketSession session, String mac) {
		this.session = session;
		this.mac = mac;
	}
	public WebSocketSession getSession() {
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	@Override
	public String toString() {
		return "DoorlockListItem [session=" + session + ", mac=" + mac + "]";
	}
}