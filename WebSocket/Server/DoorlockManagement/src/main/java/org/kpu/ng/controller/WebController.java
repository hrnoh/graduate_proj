package org.kpu.ng.controller;

import java.util.List;

import javax.inject.Inject;

import org.kpu.ng.domain.DepartmentVO;
import org.kpu.ng.domain.DoorlockVO;
import org.kpu.ng.domain.EmployeeVO;
import org.kpu.ng.domain.LogVO;
import org.kpu.ng.service.DoorlockService;
import org.kpu.ng.service.EmployeeService;
import org.kpu.ng.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/*")
public class WebController {
	@Inject
	private LogService logService;
	@Inject
	private DoorlockService doorlockService;
	@Inject
	private EmployeeService employeeService;
	
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String mainGET() {
		logger.info("main ............");
		
		return "main";
	}
	
	/*
	 * 도어락 추가 (URL : http://서버ip:8080/web/dAdd) =======================
	 * */
	@RequestMapping(value = "/dAdd", method = RequestMethod.GET)
	public void dAddGET() {
		
	}
	
	@RequestMapping(value = "/dAdd", method = RequestMethod.POST)
	public String dAddPOST(DoorlockVO doorlockVO, RedirectAttributes rttr) throws Exception {
		
		doorlockService.regist(doorlockVO);
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/web/";
	}
	//===========================================================================
	
	/*
	 * 사원 추가 (URL : http://서버ip:8080/web/eAdd) =======================
	 * */
	@RequestMapping(value = "/deptList", method = RequestMethod.GET)
	public @ResponseBody List<DepartmentVO> deptListGET() throws Exception {
		List<DepartmentVO> deptList = employeeService.deptList();
		
		return deptList;
	}
	
	@RequestMapping(value = "/eAdd", method = RequestMethod.POST)
	public String eAddPOST(EmployeeVO employeeVO, RedirectAttributes rttr) throws Exception {
		
		employeeService.regist(employeeVO);
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/web/";
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
	 * 도어락 리스트 (URL : http://서버ip:8080/web/dList) =======================
	 * */
	@RequestMapping(value = "/dList", method = RequestMethod.GET)
	public @ResponseBody List<DoorlockVO> dListGET() throws Exception {
		List<DoorlockVO> list = doorlockService.listAll();
		
		return list;
	}
	//===========================================================================
	
	/*
	 * 사원 리스트 (URL : http://서버ip:8080/web/eList) =======================
	 * */
	@RequestMapping(value = "/eList", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeVO> eListGET() throws Exception {
		List<EmployeeVO> list = employeeService.listAll();
		
		return list;
	}
	//===========================================================================
	
	/*
	 * 도어락 로그 (URL : http://서버ip:8080/web/dLog) =======================
	 * Param : mac
	 * */
	@RequestMapping(value = "/dLog", method = RequestMethod.GET)
	public String dLogGET(@RequestParam("mac")String mac, Model model) throws Exception {
		List<LogVO> dLogList = logService.listByDoorlock(mac);
		DoorlockVO doorlockVO = doorlockService.readByMac(mac);
		
		model.addAttribute("list", dLogList);
		model.addAttribute("doorlockVO", doorlockVO);
		
		return "doorlock_log";
	}
	//===========================================================================
	
	/*
	 * 사원 로그 (URL : http://서버ip:8080/web/eLog) =======================
	 * Param : eno
	 * */
	@RequestMapping(value = "/eLog", method = RequestMethod.GET)
	public String eLogGET(@RequestParam("eno")int eno, Model model) throws Exception {
		List<LogVO> eLogList = logService.listByEmployee(eno);
		EmployeeVO employeeVO = employeeService.readByEno(eno);
		
		model.addAttribute("list", eLogList);
		model.addAttribute("employeeVO", employeeVO);
		
		return "emp_log";
	}
	//===========================================================================
}
