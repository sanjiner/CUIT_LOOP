package edu.cuit.module.label.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.label.dao.impl.LabermanagementDaoImpl;
import edu.cuit.module.label.entity.Labermanagement;
import edu.cuit.module.label.service.LabermanagementService;

@Service
public class LabermanagementServiceImpl extends
		BaseServiceImpl<Labermanagement, String> implements LabermanagementService{
	
	private LabermanagementDaoImpl labermanagementDao;

	@Autowired
	private void setLabermanagementDao(LabermanagementDaoImpl labermanagementDao) {
		this.labermanagementDao = labermanagementDao;
		setBaseDao(labermanagementDao);
	}

	@Override
	public Page listByOrderNum(Integer requsetPageNo, int pageCounSize) {
		String hql = "from LaberManagement as m order by m.cuitMoonModuleOrderNum";
		return getPage(hql, requsetPageNo - 1, pageCounSize);
	}
}
