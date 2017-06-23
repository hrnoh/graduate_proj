package org.kpu.doorlock.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kpu.doorlock.domain.DoorlockVO;
import org.kpu.doorlock.domain.EmployeeVO;
import org.kpu.doorlock.domain.LogVO;
import org.kpu.doorlock.domain.MessageVO;
import org.kpu.doorlock.persistence.DoorlockDAO;
import org.kpu.doorlock.persistence.EmployeeDAO;
import org.kpu.doorlock.persistence.LogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

@Component
public class DoorlockHandler extends TextWebSocketHandler {
	private int initFlag = 0;
	private final Logger logger = LogManager.getLogger(getClass());
	private List<WebSocketSession> userList = new ArrayList<WebSocketSession>();
	private List<DoorlockVO> doorlockList = new ArrayList<DoorlockVO>();
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Autowired
	private DoorlockDAO doorlockDAO;
	
	@Autowired
	private LogDAO logDAO;
	
	public DoorlockHandler() {
		super();
		this.logger.info("create DoorlockHandler instance!");
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
		
		// 오프라인으로 설정
		DoorlockVO delDoorlock = findDoorlockBySession(session);
		if(delDoorlock != null) {
			delDoorlock.setIsOnline(0);
			doorlockList();
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
		
		if(initFlag == 0) {
			doorlockList = doorlockDAO.getAllInfo();
			initFlag = 1;
		}
		
		this.logger.info("새로운 연결 IP : " + session.getRemoteAddress().getHostName());
		this.logger.info(doorlockList);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		MessageVO msg = MessageVO.convMessage(message.getPayload());
		this.logger.info("메세지 타입 : " + msg.getType() + ", 메세지 내용 : " + msg.getData());
		
		// 도어락 초기화
		if(msg.getType().equals("dinit")) {
			String mac = msg.getData();
			dInit(session, mac);
		}
		// 유저 초기화
		else if(msg.getType().equals("uinit")) {
			userList.add(session);
			doorlockList();
		}
		// 인증 요청
		else if(msg.getType().equals("info_request")) {
			infoResponse(session, Integer.parseInt(msg.getData()));
		}
		// 출입 이력 검색
		else if(msg.getType().equals("log_search")) {
			logSearch(session, msg.getData());
		} 
		// 도어락 추가
		else if(msg.getType().equals("doorlock_add")) {
			doorlockAdd(msg.getData());
		}
		// 원격 오픈
		else if(msg.getType().equals("remoteOpen")) {
			remoteOpen(Integer.parseInt(msg.getData()));
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		this.logger.info("Partial Message!");
		
		return super.supportsPartialMessages();
	}
	
	public DoorlockVO findDoorlockBySession(WebSocketSession session) {
		if(doorlockList.isEmpty()) {
			return null;
		}
		
		for(DoorlockVO doorlockVO : doorlockList) {
			if(doorlockVO.getSession() == session) {
				return doorlockVO;
			}
		}
		
		return null;
	}
	public DoorlockVO findDoorlockByMac(String mac) {
		if(doorlockList.isEmpty()) {
			return null;
		}
		
		for(DoorlockVO doorlockVO : doorlockList) {
			if(doorlockVO.getMac().equals(mac)) {
				return doorlockVO;
			}
		}
		
		return null;
	}
	public DoorlockVO findDoorlockByDno(int dno) {
		if(doorlockList.isEmpty()) {
			return null;
		}
		
		for(DoorlockVO doorlockVO : doorlockList) {
			if(doorlockVO.getDno() == dno) {
				return doorlockVO;
			}
		}
		
		return null;
	}
	/*
	 *  Iot Doorlock System Method
	 * 1. dInit : 도어락 초기화 메서드
	 * 2. infoResponse : 사원 정보 반환
	 * */
	public void dInit(WebSocketSession session, String mac) {
		this.logger.info("새로운 기기 연결 : " + mac);
		DoorlockVO doorlockVO = findDoorlockByMac(mac);
		MessageVO msg = new MessageVO();
		
		// Error 처리
		if(doorlockVO == null) {
			msg.setType("error");
			msg.setData("doorlock is not registered. Please register Doorlock (" + mac + ")");
			String msgJson = new Gson().toJson(msg);
			try {
				session.sendMessage(new TextMessage(msgJson));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.logger.info(mac + "은 등록되지 않은 도어락입니다.");
			return;
		}
		
		// 등록 된 도어락인 경우
		String doorlockJson = new Gson().toJson(doorlockVO);
		
		msg.setType("dinit");
		msg.setData(doorlockJson);
		String msgJson = new Gson().toJson(msg);
		// 응답 전송
		try {
			session.sendMessage(new TextMessage(msgJson));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.logger.info(doorlockVO.getLocation() + " " + session.getRemoteAddress().getHostName() + " 연결 완료.");
		sendLog("INFO: " + doorlockVO.getLocation() + " " + session.getRemoteAddress().getHostName() + " 연결 완료.");
		
		// 온라인 설정
		doorlockVO.setSession(session);
		doorlockVO.setIsOnline(1);
		doorlockList();
	}
	
	public void infoResponse(WebSocketSession session, int eno) {
		DoorlockVO doorlockVO = findDoorlockBySession(session);
		EmployeeVO employeeVO = employeeDAO.getInfoByNum(eno);
		String employeeJson = new Gson().toJson(employeeVO);
		MessageVO msg = new MessageVO();
		
		if(employeeVO == null) {
			msg.setType("error");
			msg.setData("This employee not exist.");
			String msgJson = new Gson().toJson(msg);
			
			try {
				session.sendMessage(new TextMessage(msgJson));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.logger.warn(doorlockVO.getLocation() + "에 불법 카드 접근!");
			sendLog("WARNING: " + doorlockVO.getLocation() + "에 불법 카드 접근!");
			return;
		}
		
		msg.setType("info_response");
		msg.setData(employeeJson);
		String msgJson = new Gson().toJson(msg);
		
		try {
			session.sendMessage(new TextMessage(msgJson));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.logger.info(employeeVO.getName() + "이(가) " + doorlockVO.getLocation() + "에 접근하였습니다.");
		sendLog("INFO: " + employeeVO.getName() + "이(가) " + doorlockVO.getLocation() + "에 접근하였습니다.");
		
		// 출입 이력 추가
		LogVO log = new LogVO();
		log.setEno(employeeVO.getEno());
		log.setMessage(employeeVO.getName() + "이(가)" + doorlockVO.getLocation() + "에 접근하였습니다.");
		//log.setTime(new Date());
		logDAO.insertLog(log);
	}

	/*
	 * 
	 * Web User Method
	 * 1. sendLog : 웹 유저에게 로그 전송
	 * 2. logSearch : 개인 출입 이력 전송
	 * 3. doorlockList : 도어락 리스트 전송
	 * 4. doorlockAdd : 도어락 추가
	 * */
	public void sendLog(String message) {
		MessageVO msg = new MessageVO();
		msg.setType("log");
		msg.setData(message);
		
		String msgJson = new Gson().toJson(msg);
		
		for(WebSocketSession session : userList) {
			try {
				session.sendMessage(new TextMessage(msgJson));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void logSearch(WebSocketSession session, String name) {
		MessageVO msg = new MessageVO();
		EmployeeVO empVO = employeeDAO.getInfoByName(name);
		if(empVO == null) {
			msg.setType("log_search_error");
			msg.setData("존재하지 않는 사원입니다!");
			String errMsg = new Gson().toJson(msg);
			
			try {
				session.sendMessage(new TextMessage(errMsg));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return;
		}
		List<LogVO> logList = logDAO.getLogByEno(empVO.getEno());
		
		String logListJson = new Gson().toJson(logList);
		
		msg.setType("log_search");
		msg.setData(logListJson);
		String msgJson = new Gson().toJson(msg);
		try {
			session.sendMessage(new TextMessage(msgJson));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void doorlockList() {
		MessageVO message = new MessageVO();
		String jsonMsg;
		Gson gson;		
		message.setType("doorlockList");
		
		if(doorlockList.isEmpty()) {
			gson = new Gson();
			
			message.setData(null);
			jsonMsg = gson.toJson(message);
			
			try {
				for(WebSocketSession session : userList)
					session.sendMessage(new TextMessage(jsonMsg));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return;
		}
		// excludeFieldsWithoutExposeAnnotation().create() : @Expose붙은 필드만 직렬화
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String doorlockListJson = gson.toJson(doorlockList);
		message.setData(doorlockListJson);
		jsonMsg = new Gson().toJson(message);

		try {
			for(WebSocketSession session : userList)
				session.sendMessage(new TextMessage(jsonMsg));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void doorlockAdd(String data) {
		DoorlockVO vo = new Gson().fromJson(data, DoorlockVO.class);
		doorlockDAO.insertDoorlock(vo);
		vo = doorlockDAO.getInfoByMac(vo.getMac());
		
		if(!doorlockList.contains(vo)) {
			doorlockList.add(vo);
		}

		String logMessage = "새로운 도어락 추가 : location(" + vo.getLocation();
		logMessage += "), mac(" + vo.getMac() + ")" + ", level(" + vo.getLevel() + ")";
		this.logger.info(logMessage);
		doorlockList();
		sendLog("INFO: " + logMessage);
	}

	public void remoteOpen(int dno) {
		WebSocketSession session;
		MessageVO message = new MessageVO();
		DoorlockVO vo = findDoorlockByDno(dno);
		if(vo == null || vo.getIsOnline() == 0) {
			this.logger.warn("도어락 원격 개방 실패!");
			sendLog("WARNING: 도어락 원격 개방 실패!");
			return;
		}
		
		session = vo.getSession();
		message.setType("remoteOpen");
		message.setData(null);
		String msgJson = new Gson().toJson(message);
		
		try {
			session.sendMessage(new TextMessage(msgJson));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		this.logger.info(vo.getLocation() + "(이)가 개방되었습니다.");
		sendLog("INFO: " + vo.getLocation() + "(이)가 개방되었습니다.");
	}
}
