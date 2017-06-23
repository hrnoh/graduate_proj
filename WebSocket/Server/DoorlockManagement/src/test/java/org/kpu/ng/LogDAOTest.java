package org.kpu.ng;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpu.ng.domain.LogVO;
import org.kpu.ng.persistence.LogDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class LogDAOTest {

	@Inject
	private LogDAO dao;
	
	private static Logger logger = LoggerFactory.getLogger(LogDAOTest.class);
	
	//@Test
	public void createTest() throws Exception {
		LogVO vo = new LogVO();
		vo.setEno(1);
		vo.setDno(1);
		vo.setResult("test_fail2");
		dao.create(vo);
	}
	
	//@Test
	public void listByEmployeeTest() throws Exception {
		List<LogVO> list = dao.listByEmployee(1);
		
		for(LogVO vo : list) {
			logger.info(vo.toString());
		}
	}
	
	//@Test
	public void listByDoorlockTest() throws Exception {
		List<LogVO> list = dao.listByDoorlock("B4-74-9F-AF-72-ED");
		
		for(LogVO vo : list) {
			logger.info(vo.toString());
		}
	}
	
	@Test
	public void listAllTest() throws Exception {
		List<LogVO> list = dao.listAll();
		
		for(LogVO vo : list) {
			logger.info(vo.toString());
		}
	}
}
