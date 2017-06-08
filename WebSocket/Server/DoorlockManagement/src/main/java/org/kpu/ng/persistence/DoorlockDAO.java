package org.kpu.ng.persistence;

import java.util.List;

import org.kpu.ng.domain.DoorlockVO;

public interface DoorlockDAO {
	public void create(DoorlockVO vo) throws Exception;
	
	public DoorlockVO readByDno(Integer dno) throws Exception;
	
	public DoorlockVO readByMac(String mac) throws Exception;
	
	public void update(DoorlockVO vo) throws Exception;
	
	public void delete(String mac) throws Exception;
	
	public List<DoorlockVO> listAll() throws Exception;
}
