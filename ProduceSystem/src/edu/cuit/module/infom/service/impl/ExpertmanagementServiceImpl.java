package edu.cuit.module.infom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.infom.dao.impl.ExpertmanagementDaoImpl;
import edu.cuit.module.infom.entity.Expertmanagement;
import edu.cuit.module.infom.service.ExpertmanagementService;

@Service
public class ExpertmanagementServiceImpl extends BaseServiceImpl<Expertmanagement, String> implements ExpertmanagementService{
	private ExpertmanagementDaoImpl expertmanagementDao;
	@Autowired
	private void setExpertmanagementDao(ExpertmanagementDaoImpl expertmanagementDao){
		this.expertmanagementDao = expertmanagementDao;
		setBaseDao(expertmanagementDao);
	}
}
