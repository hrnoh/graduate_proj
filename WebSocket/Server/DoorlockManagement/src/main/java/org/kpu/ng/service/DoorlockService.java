package org.kpu.ng.service;

import java.util.List;

import org.kpu.ng.domain.DoorlockVO;

public interface DoorlockService {
	public void regist(DoorlockVO vo) throws Exception;
	
	public DoorlockVO readByDno(Integer dno) throws Exception;
	
	public DoorlockVO readByMac(String mac) throws Exception;
	
	public void modify(DoorlockVO vo) throws Exception;
	
	public void remove(String mac) throws Exception;
	
	public List<DoorlockVO> listAll() throws Exception;
}
