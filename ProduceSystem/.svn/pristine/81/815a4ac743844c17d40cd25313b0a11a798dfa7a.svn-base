package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.Expertsgrade;
import edu.cuit.module.authc.dao.ExpertsgradeDao;
import edu.cuit.module.authc.service.ExpertsgradeService;

@Service
public class ExpertsgradeServiceImpl extends
		BaseServiceImpl<Expertsgrade, String> implements ExpertsgradeService {
	private ExpertsgradeDao expertsgradeDao;
	
	@Autowired
	public void setExpertsgradeDao(ExpertsgradeDao expertsgradeDao){
		this.expertsgradeDao = expertsgradeDao;
		setBaseDao(expertsgradeDao);
	}
}
