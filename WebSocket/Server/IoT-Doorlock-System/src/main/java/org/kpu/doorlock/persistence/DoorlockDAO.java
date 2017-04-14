package org.kpu.doorlock.persistence;

import java.util.List;

import org.kpu.doorlock.domain.DoorlockVO;

public interface DoorlockDAO {
	public DoorlockVO getInfoByMac(String mac);
	public List<DoorlockVO> getAllInfo();
	public void insertDoorlock(DoorlockVO vo);
}
