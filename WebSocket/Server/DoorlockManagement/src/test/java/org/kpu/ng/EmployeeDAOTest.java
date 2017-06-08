package org.kpu.ng;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpu.ng.domain.EmployeeVO;
import org.kpu.ng.persistence.EmployeeDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class EmployeeDAOTest {
	@Inject
	private EmployeeDAO dao;
	
	private static Logger logger = LoggerFactory.getLogger(EmployeeDAOTest.class);
	
	//@Test
	public void createTest() throws Exception {
		EmployeeVO vo = new EmployeeVO();
		vo.setName("Test");
		vo.setAge(200);
		vo.setPhoneNum("010-0000-0000");
		vo.setPosition("대리");
		vo.setLevel(1);
		vo.setDno(1);
		dao.create(vo);
	}
	
	//@Test
	public void readByEnoTest() throws Exception {
		logger.info(dao.readByEno(1).toString());
	}
	
	//@Test
	public void readByNameTest() throws Exception {
		logger.info(dao.readByName("Test").toString());
	}
	
	//@Test
	public void updateTest() throws Exception {
		EmployeeVO vo = dao.readByName("Test");
		vo.setPosition("수정된 대리");
		dao.update(vo);
	}
	
	//@Test
	public void deleteTest() throws Exception {
		dao.delete(3);
	}
	
	//@Test
	public void listAllTest() throws Exception {
		List<EmployeeVO> list = dao.listAll();
		
		for(EmployeeVO vo : list) {
			logger.info(vo.toString());
		}
	}
}
