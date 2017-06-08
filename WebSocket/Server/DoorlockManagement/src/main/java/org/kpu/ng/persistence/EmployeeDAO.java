package org.kpu.ng.persistence;

import java.util.List;

import org.kpu.ng.domain.EmployeeVO;

public interface EmployeeDAO {
	public void create(EmployeeVO vo) throws Exception;
	
	public EmployeeVO readByEno(Integer eno) throws Exception;
	
	public EmployeeVO readByName(String name) throws Exception;
	
	public void update(EmployeeVO vo) throws Exception;
	
	public void delete(Integer eno) throws Exception;
	
	public List<EmployeeVO> listAll() throws Exception;
}
