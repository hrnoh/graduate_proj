package org.kpu.doorlock.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.kpu.doorlock.domain.DoorlockVO;
import org.springframework.stereotype.Repository;

@Repository
public class DoorlockDAOImpl implements DoorlockDAO {
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace = "org.kpu.doorlock.mapper.DoorlockMapper";
	
	@Override
	public DoorlockVO getInfoByMac(String mac) {
		return sqlSession.selectOne(namespace+".getInfoByMac", mac);
	}
	
	@Override
	public List<DoorlockVO> getAllInfo() {
		return sqlSession.selectList(namespace+".getAllInfo");
	}
	
	@Override
	public void insertDoorlock(DoorlockVO vo) {
		sqlSession.insert(namespace+".insertDoorlock", vo);
	}
}
