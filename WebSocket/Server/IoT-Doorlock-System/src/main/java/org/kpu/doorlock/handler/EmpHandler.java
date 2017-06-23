package org.kpu.doorlock.handler;

import java.util.List;

import org.kpu.doorlock.domain.EmployeeVO;
import org.kpu.doorlock.persistence.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpHandler {
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	// (우선은) 이름으로 사원 검색
	public EmployeeVO FindEmp(EmployeeVO target) {
		return employeeDAO.getInfoByName(target.getName());
	}
	
	// 사원 리스트 출력
	public List<EmployeeVO> EmpList() {
		return employeeDAO.getAllEmployee();
	}
}
