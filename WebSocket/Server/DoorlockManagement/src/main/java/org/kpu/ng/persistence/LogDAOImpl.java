package org.kpu.ng.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.kpu.ng.domain.LogVO;
import org.springframework.stereotype.Repository;

@Repository
public class LogDAOImpl implements LogDAO {
	@Inject
	private SqlSession session;
	
	private static final String namespace = "org.kpu.ng.mapper.LogMapper";
	
	@Override
	public void create(LogVO vo) throws Exception {
		session.insert(namespace + ".create", vo);
	}
	
	@Override
	public List<LogVO> listByEmployee(Integer eno) throws Exception {
		return session.selectList(namespace + ".listByEmployee", eno);
	}
	
	@Override
	public List<LogVO> listByDoorlock(String mac) throws Exception {
		return session.selectList(namespace + ".listByDoorlock", mac);
	}
	
	@Override
	public List<LogVO> listAll() throws Exception {
		return session.selectList(namespace + ".listAll");
	}
}
