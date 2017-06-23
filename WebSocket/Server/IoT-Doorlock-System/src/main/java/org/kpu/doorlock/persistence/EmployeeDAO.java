package org.kpu.doorlock.persistence;

import java.util.List;

import org.kpu.doorlock.domain.EmployeeVO;

public interface EmployeeDAO {
	public EmployeeVO getInfoByNum(int eno);
	public EmployeeVO getInfoByName(String name);
	public List<EmployeeVO> getAllEmployee();
}
