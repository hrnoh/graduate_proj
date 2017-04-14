package org.kpu.doorlock.persistence;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.kpu.doorlock.domain.LogVO;
import org.springframework.stereotype.Repository;

@Repository
public class LogDAOImpl implements LogDAO {
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace = "org.kpu.doorlock.mapper.LogMapper";
	
	public List<LogVO> getLogByEno(int eno) {
		return sqlSession.selectList(namespace + ".getLogByEno", eno);
	}
	
	public List<LogVO> getAllLog() {
		return sqlSession.selectList(namespace + ".getAllLog");
	}
	
	public void insertLog(LogVO vo) {
		sqlSession.insert(namespace + ".insertLog", vo);
	}
}
