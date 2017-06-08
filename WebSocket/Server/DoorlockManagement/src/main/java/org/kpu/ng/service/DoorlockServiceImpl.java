package org.kpu.ng.service;

import java.util.List;

import javax.inject.Inject;

import org.kpu.ng.domain.DoorlockVO;
import org.kpu.ng.persistence.DoorlockDAO;
import org.springframework.stereotype.Service;

@Service
public class DoorlockServiceImpl implements DoorlockService {
	@Inject
	private DoorlockDAO dao;
	
	@Override
	public void regist(DoorlockVO vo) throws Exception {
		dao.create(vo);
	}
	
	@Override
	public DoorlockVO readByDno(Integer dno) throws Exception {
		return dao.readByDno(dno);
	}
	
	@Override
	public DoorlockVO readByMac(String mac) throws Exception {
		return dao.readByMac(mac);
	}
	
	@Override
	public void modify(DoorlockVO vo) throws Exception {
		dao.update(vo);
	}
	
	@Override
	public void remove(String mac) throws Exception {
		dao.delete(mac);
	}
	
	@Override
	public List<DoorlockVO> listAll() throws Exception {
		return dao.listAll();
	}
}
