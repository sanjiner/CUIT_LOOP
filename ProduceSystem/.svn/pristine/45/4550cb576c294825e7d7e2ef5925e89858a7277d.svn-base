package edu.cuit.module.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.model.dao.impl.ElementinfomationDaoImpl;
import edu.cuit.module.model.entity.Elementinfomation;
import edu.cuit.module.model.service.ElementinfomationService;

@Service
public class ElementinfomationServiceImpl extends
		BaseServiceImpl<Elementinfomation, String> implements ElementinfomationService {
	private ElementinfomationDaoImpl elementinfomationDao;
	@Autowired
	private void setElementinfomationDao(ElementinfomationDaoImpl elementinfomationDao){
		this.elementinfomationDao = elementinfomationDao;
		setBaseDao(elementinfomationDao);
	}
}
