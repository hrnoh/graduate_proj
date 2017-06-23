package org.kpu.doorlock.web;

import java.util.List;

import org.kpu.doorlock.domain.EmployeeVO;
import org.kpu.doorlock.handler.EmpHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/android")
public class AndroidController {
	
	@Autowired
	private EmpHandler empHandler;

	@RequestMapping(value = "/connect", method = RequestMethod.POST)
	public void androidConnect(@RequestParam("test") String param) {
		
		System.out.println("어플리케이션 연결");
		System.out.println("test: " + param);
	}
	
	@ResponseBody
	@RequestMapping(value="/emp", method=RequestMethod.POST)
	public List<EmployeeVO> EmpList(EmployeeVO target) {
		System.out.println("/emp");
		return empHandler.EmpList();
	}
	
	@RequestMapping(value="/emp/find", method=RequestMethod.POST)
	public EmployeeVO SearchEmp(EmployeeVO target) {
		
		return empHandler.FindEmp(target);
	}
}
