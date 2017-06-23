package org.kpu.ng.controller;

import java.util.List;

import javax.inject.Inject;

import org.kpu.ng.domain.DoorlockVO;
import org.kpu.ng.domain.EmployeeVO;
import org.kpu.ng.domain.LogVO;
import org.kpu.ng.service.DoorlockService;
import org.kpu.ng.service.EmployeeService;
import org.kpu.ng.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping("/app/*")
public class AndroidController {
	
	@Inject
	private LogService logService;
	@Inject
	private DoorlockService doorlockService;
	@Inject
	private EmployeeService employeeService;
	
	private static final Logger logger = LoggerFactory.getLogger(AndroidController.class);
	private Gson gson;
	
	@RequestMapping(value = "/dLog/{mac}", method = RequestMethod.GET)
	public ResponseEntity<List<LogVO>> dLog(@PathVariable("mac") String mac) throws Exception {
		ResponseEntity<List<LogVO>> entity = null;
		
		try {
			List<LogVO> list = logService.listByDoorlock(mac);
			entity = new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/eLog/{eno}", method = RequestMethod.GET)
	public ResponseEntity<List<LogVO>> eLog(@PathVariable("eno") Integer eno) {
		ResponseEntity<List<LogVO>> entity = null;
		
		try {
			List<LogVO> list = logService.listByEmployee(eno);
			entity = new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/dAdd", method = RequestMethod.POST)
	public ResponseEntity<String> dAdd(@RequestBody DoorlockVO vo) {
		ResponseEntity<String> entity = null;
		
		try {
			doorlockService.regist(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/eAdd", method = RequestMethod.POST)
	public ResponseEntity<String> eAdd(@RequestBody EmployeeVO vo) {
		ResponseEntity<String> entity = null;
		
		try {
			employeeService.regist(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/dDel/{mac}", method = RequestMethod.DELETE)
	public ResponseEntity<String> dDel(@PathVariable("mac") String mac) {
		ResponseEntity<String> entity = null;
		
		try {
			doorlockService.remove(mac);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/eDel/{eno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> eDel(@PathVariable("eno") Integer eno) {
		ResponseEntity<String> entity = null;
		
		try {
			employeeService.remove(eno);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/eList", method = RequestMethod.GET)
	public ResponseEntity<List<EmployeeVO>> eList() {
		ResponseEntity<List<EmployeeVO>> entity = null;
		
		try {
			List<EmployeeVO> list = employeeService.listAll();
			entity = new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "/dList", method = RequestMethod.GET)
	public ResponseEntity<List<DoorlockVO>> dList() {
		ResponseEntity<List<DoorlockVO>> entity = null;
		
		try {
			List<DoorlockVO> list = doorlockService.listAll();
			entity = new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}