package org.kpu.doorlock;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpu.doorlock.domain.LogVO;
import org.kpu.doorlock.persistence.LogDAO;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (
		locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class LogDAOTest {
	@Inject
	private LogDAO dao;
	
	//@Test
	public void testInsertLog() {
		try {
			LogVO vo = new LogVO();
			vo.setEno(1);
			vo.setMessage("insertTest");
			dao.insertLog(vo);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Test
	public void testLogByEno() {
		try {
			List<LogVO> list = dao.getLogByEno(1);
			for(LogVO vo : list) {
				System.out.println("결과 : " + vo.getTime());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
