package org.kpu.ng.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.kpu.ng.domain.DoorlockVO;
import org.springframework.stereotype.Repository;

@Repository
public class DoorlockDAOImpl implements DoorlockDAO {
	@Inject
	private SqlSession session;
	
	private static final String namespace = "org.kpu.ng.mapper.DoorlockMapper";
	
	@Override
	public void create(DoorlockVO vo) throws Exception {
		session.insert(namespace + ".create", vo);
	}
	
	@Override
	public DoorlockVO readByDno(Integer dno) throws Exception {
		return session.selectOne(namespace + ".readByDno", dno);
	}
	
	@Override
	public DoorlockVO readByMac(String mac) throws Exception {
		return session.selectOne(namespace + ".readByMac", mac);
	}
	
	@Override
	public void update(DoorlockVO vo) throws Exception {
		session.update(namespace + ".update", vo);
	}
	
	@Override
	public void delete(String mac) throws Exception {
		session.delete(namespace + ".delete", mac);
	}
	
	@Override
	public List<DoorlockVO> listAll() throws Exception {
		return session.selectList(namespace + ".listAll");
	}
}
