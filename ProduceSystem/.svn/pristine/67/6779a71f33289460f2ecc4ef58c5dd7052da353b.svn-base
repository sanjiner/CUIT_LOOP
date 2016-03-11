package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.Prictureinfomation;
import edu.cuit.module.authc.dao.PrictureinfomationDao;
import edu.cuit.module.authc.dao.impl.PrictureinfomationDaoImpl;
import edu.cuit.module.authc.service.MemberGroupService;
import edu.cuit.module.authc.service.PrictureinfomationService;

@Service
public class PrictureinfomationServiceImpl extends
		BaseServiceImpl<Prictureinfomation, String> implements PrictureinfomationService{
	private PrictureinfomationDao prictureinfomationDao;
	
	@Autowired
	private void setPrictureinfomationDao(PrictureinfomationDao prictureinfomationDao){
		this.prictureinfomationDao = prictureinfomationDao;
		setBaseDao(prictureinfomationDao);
	}
}
