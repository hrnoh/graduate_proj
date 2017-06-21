package org.kpu.ng.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.kpu.ng.domain.DepartmentVO;
import org.kpu.ng.domain.EmployeeVO;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
	@Inject
	private SqlSession session;
	
	private static final String namespace = "org.kpu.ng.mapper.EmployeeMapper";
	
	@Override
	public void create(EmployeeVO vo) throws Exception {
		session.insert(namespace + ".create", vo);
	}
	
	@Override
	public EmployeeVO readByEno(Integer eno) throws Exception {
		return session.selectOne(namespace + ".readByEno", eno);
	}
	
	@Override
	public EmployeeVO readByName(String name) throws Exception {
		return session.selectOne(namespace + ".readByName", name);
	}
	
	@Override
	public void update(EmployeeVO vo) throws Exception {
		session.update(namespace + ".update", vo);
	}
	
	@Override
	public void delete(Integer eno) throws Exception {
		session.delete(namespace + ".delete", eno);
	}
	
	@Override
	public List<EmployeeVO> listAll() throws Exception {
		return session.selectList(namespace + ".listAll");
	}
	
	@Override
	public List<DepartmentVO> deptList() throws Exception {
		return session.selectList(namespace + ".deptList");
	}
}
