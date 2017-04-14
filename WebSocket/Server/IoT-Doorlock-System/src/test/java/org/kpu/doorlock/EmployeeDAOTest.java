package org.kpu.doorlock;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpu.doorlock.domain.EmployeeVO;
import org.kpu.doorlock.persistence.EmployeeDAO;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (
		locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class EmployeeDAOTest {
	@Inject
	private EmployeeDAO dao;
	
	//@Test
	public void testInfobyNum() throws Exception {
		try {
			System.out.println("결과 : " + dao.getInfoByNum(5));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testAllEmployee() throws Exception {
		try {
			List<EmployeeVO> list = dao.getAllEmployee();
			
			for(EmployeeVO vo : list) {
				System.out.println("결과: " + vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInfoByName() {
		try {
			System.out.println("결과 : " + dao.getInfoByName("노형래"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
