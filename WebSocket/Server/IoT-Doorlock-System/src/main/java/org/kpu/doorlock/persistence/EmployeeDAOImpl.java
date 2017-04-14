package org.kpu.doorlock.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.kpu.doorlock.domain.EmployeeVO;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace = "org.kpu.doorlock.mapper.EmployeeMapper";
	
	@Override
	public EmployeeVO getInfoByNum(int eno) {
		return sqlSession.selectOne(namespace+".getInfoByNum", eno);
	}
	
	@Override
	public List<EmployeeVO> getAllEmployee() {
		return sqlSession.selectList(namespace+".getAllEmployee");
	}
	
	@Override
	public EmployeeVO getInfoByName(String name) {
		return sqlSession.selectOne(namespace+".getInfoByName", name);
	}
}
