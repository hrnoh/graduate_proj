package org.kpu.ng.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/web/*")
public class WebController {
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);
	
	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public void management() {
		logger.info("management ............");
	}
	
	/*
	 * 도어락 추가 (URL : http://서버ip:8080/web/dAdd) =======================
	 * */
	@RequestMapping(value = "/dAdd", method = RequestMethod.GET)
	public void dAddGET() {
		
	}
	
	@RequestMapping(value = "/dAdd", method = RequestMethod.POST)
	public void dAddPOST() {
		
	}
	//===========================================================================
	
	/*
	 * 도어락 삭제 (URL : http://서버ip:8080/web/dDel) =======================
	 * Param : mac주소
	 * */
	@RequestMapping(value = "/dDel", method = RequestMethod.POST)
	public void dDelPOST(String mac) {
		
	}
	//===========================================================================
	
	/*
	 * 도어락 로그 (URL : http://서버ip:8080/web/dDel) =======================
	 * Param : mac주소
	 * */
	@RequestMapping(value = "/dLog", method = RequestMethod.POST)
	public void dLogPOST(String mac) {
		
	}
	//===========================================================================
}
