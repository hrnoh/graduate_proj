package org.kpu.ng.service;

import java.util.List;

import org.kpu.ng.domain.EmployeeVO;

public interface EmployeeService {
	public void regist(EmployeeVO vo) throws Exception;
	
	public EmployeeVO readByEno(Integer eno) throws Exception;
	
	public EmployeeVO readByName(String name) throws Exception;
	
	public void modify(EmployeeVO vo) throws Exception;
	
	public void remove(Integer eno) throws Exception;
	
	public List<EmployeeVO> listAll() throws Exception;
}
