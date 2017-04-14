package org.kpu.doorlock;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kpu.doorlock.domain.DoorlockVO;
import org.kpu.doorlock.persistence.DoorlockDAO;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (
		locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DoorlockDAOTest {
	@Inject
	private DoorlockDAO dao;
	
	//@Test
	public void testGetInfoByMac() {
		try {
			System.out.println("결과 : " + dao.getInfoByMac("B4-74-9F-AF-72-ED"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllInfo() {
		try {
			List<DoorlockVO> doorlockList = dao.getAllInfo();
			
			for(DoorlockVO vo : doorlockList) {
				System.out.println("결과 : " + vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertDoorlock() {
		try {
			DoorlockVO vo = new DoorlockVO();
			vo.setLocation("test home2");
			vo.setMac("AB-FF-FF-FF-FF-FF");
			vo.setLevel(1);
			dao.insertDoorlock(vo);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
