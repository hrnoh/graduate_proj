package org.kpu.doorlock.persistence;

import java.util.List;

import org.kpu.doorlock.domain.LogVO;

public interface LogDAO {
	public List<LogVO> getLogByEno(int eno);
	public List<LogVO> getAllLog();
	public void insertLog(LogVO vo);
}
