package org.kpu.ng;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpu.ng.domain.DoorlockVO;
import org.kpu.ng.persistence.DoorlockDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DoorlockDAOTest {
	@Inject
	DoorlockDAO dao;
	
	private static Logger logger = LoggerFactory.getLogger(DoorlockDAOTest.class);
	
	@Test
	public void createTest() throws Exception {
		DoorlockVO vo = new DoorlockVO();
		vo.setLocation("Test용 장소");
		vo.setMac("testMac");
		vo.setLevel(2);
		dao.create(vo);
	}
	
	//@Test
	public void readByDnoTest() throws Exception {
		logger.info(dao.readByDno(1).toString());
	}
	
	//@Test
	public void readByMacTest() throws Exception {
		logger.info(dao.readByMac("testMac").toString());
	}
	
	//@Test
	public void updateTest() throws Exception {
		DoorlockVO vo = dao.readByMac("testMac");
		vo.setLocation("수정된 test용 장소");
		dao.update(vo);
	}
	
	//@Test
	public void deleteTest() throws Exception {
		dao.delete("testMac");
	}
	
	//@Test
	public void listAllTest() throws Exception {
		List<DoorlockVO> list = dao.listAll();
		for(DoorlockVO vo : list) {
			logger.info(vo.toString());
		}
	}
}
