package org.kpu.ng.persistence;

import java.util.List;

import org.kpu.ng.domain.LogVO;

public interface LogDAO {
	public void create(LogVO vo) throws Exception;
	
	public List<LogVO> listByEmployee(Integer eno) throws Exception;
	
	public List<LogVO> listByDoorlock(String mac) throws Exception;
	
	public List<LogVO> listAll() throws Exception;
}
