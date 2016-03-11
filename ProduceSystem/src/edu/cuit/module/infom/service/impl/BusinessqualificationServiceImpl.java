package edu.cuit.module.infom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.infom.dao.BusinessqualificationDao;
import edu.cuit.module.infom.entity.Businessqualification;
import edu.cuit.module.infom.service.BusinessqualificationService;

@Service
public class BusinessqualificationServiceImpl extends
		BaseServiceImpl<Businessqualification, String> implements
		BusinessqualificationService {
	BusinessqualificationDao businessqualificationDao;
	
	@Autowired
	public void setBusinessqualificationDao(BusinessqualificationDao businessqualificationDao){
		this.businessqualificationDao = businessqualificationDao;
		setBaseDao(businessqualificationDao);
	}
}
