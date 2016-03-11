package edu.cuit.module.label.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.label.dao.impl.LabelapplicationDaoImpl;
import edu.cuit.module.label.entity.Labelapplication;
import edu.cuit.module.label.service.LabelapplicationService;
import edu.cuit.module.model.service.ModelformulateService;

@Service
public class LabelapplicationServiceImpl extends
		BaseServiceImpl<Labelapplication, String> implements LabelapplicationService {
	private LabelapplicationDaoImpl labelapplicationDao;

	@Autowired
	private void setLabelapplicationDao(
			LabelapplicationDaoImpl labelapplicationDao) {
		this.labelapplicationDao = labelapplicationDao;
		setBaseDao(labelapplicationDao);
	}

}
