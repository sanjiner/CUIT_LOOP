package edu.cuit.module.infom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.infom.dao.BusinessmanagementDao;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.service.BusinessmanagementService;

@Service
public class BusinessmanagementServiceImpl extends
		BaseServiceImpl<Businessmanagement, String> implements BusinessmanagementService {
	private BusinessmanagementDao businessmanagementDao;
	
	@Autowired
	private void setBusinessmanagementDao(BusinessmanagementDao businessmanagementDao){
		this.businessmanagementDao = businessmanagementDao;
		setBaseDao(businessmanagementDao);
	}
}
