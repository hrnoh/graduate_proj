package org.kpu.doorlock.web;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {
	private final Logger logger = LogManager.getLogger(getClass());
	
	@RequestMapping("/management")
	public String wsTest() {
		return "management";
	}
	
}
