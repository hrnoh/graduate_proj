package org.kpu.ng.service;

import java.util.List;

import javax.inject.Inject;

import org.kpu.ng.domain.DepartmentVO;
import org.kpu.ng.domain.EmployeeVO;
import org.kpu.ng.persistence.EmployeeDAO;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Inject
	private EmployeeDAO dao;
	
	@Override
	public void regist(EmployeeVO vo) throws Exception {
		dao.create(vo);
	}
	
	@Override
	public EmployeeVO readByEno(Integer eno) throws Exception {
		return dao.readByEno(eno);
	}
	
	@Override
	public EmployeeVO readByName(String name) throws Exception {
		return dao.readByName(name);
	}
	
	@Override
	public void modify(EmployeeVO vo) throws Exception {
		dao.update(vo);
	}
	
	@Override
	public void remove(Integer eno) throws Exception {
		dao.delete(eno);
	}
	
	@Override
	public List<EmployeeVO> listAll() throws Exception {
		return dao.listAll();
	}
	
	@Override
	public List<DepartmentVO> deptList() throws Exception {
		return dao.deptList();
	}
}
