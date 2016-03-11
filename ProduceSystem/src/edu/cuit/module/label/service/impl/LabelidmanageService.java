package edu.cuit.module.label.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.label.dao.impl.LabelidmanageDaoImpl;
import edu.cuit.module.label.entity.Labelidmanage;

@Service
public class LabelidmanageService extends BaseServiceImpl<Labelidmanage, String> {
	private LabelidmanageDaoImpl labelidmanageDao;
	
	@Autowired
	private void setLabelidmanageDao(LabelidmanageDaoImpl labelidmanageDao){
		this.labelidmanageDao = labelidmanageDao;
		setBaseDao(labelidmanageDao);
	}
}
