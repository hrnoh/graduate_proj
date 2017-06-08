package org.kpu.ng.service;

import java.util.List;

import javax.inject.Inject;

import org.kpu.ng.domain.LogVO;
import org.kpu.ng.persistence.LogDAO;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
	@Inject
	LogDAO dao;
	
	@Override
	public void regist(LogVO vo) throws Exception {
		dao.create(vo);
	}
	
	@Override
	public List<LogVO> listByEmployee(Integer eno) throws Exception {
		return dao.listByEmployee(eno);
	}
	
	@Override
	public List<LogVO> listByDoorlock(String mac) throws Exception {
		return dao.listByDoorlock(mac);
	}
	
	@Override
	public List<LogVO> listAll() throws Exception {
		return dao.listAll();
	}
}
