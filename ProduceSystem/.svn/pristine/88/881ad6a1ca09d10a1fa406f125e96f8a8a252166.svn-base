package edu.cuit.module.label.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.label.dao.impl.LabelscanrecordDaoImpl;
import edu.cuit.module.label.entity.Labelscanrecord;
import edu.cuit.module.label.service.LabelscanrecordService;

@Service
public class LabelscanrecordServiceImpl extends BaseServiceImpl<Labelscanrecord, String> implements LabelscanrecordService{
	private LabelscanrecordDaoImpl labelscanrecordDao;
	@Autowired
	private void setLabelscanrecordDao(LabelscanrecordDaoImpl labelscanrecordDao){
		this.labelscanrecordDao = labelscanrecordDao;
		setBaseDao(labelscanrecordDao);
	}
}
