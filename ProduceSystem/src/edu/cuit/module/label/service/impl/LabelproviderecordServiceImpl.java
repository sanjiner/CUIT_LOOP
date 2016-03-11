package edu.cuit.module.label.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.label.dao.impl.LabelproviderecordDaoImpl;
import edu.cuit.module.label.entity.Labelproviderecord;
import edu.cuit.module.label.service.LabelproviderecordService;

@Service
public class LabelproviderecordServiceImpl extends
		BaseServiceImpl<Labelproviderecord, String> implements
		LabelproviderecordService {
	private LabelproviderecordDaoImpl labelproviderecordDao;

	@Autowired
	private void setLabelproviderecordDao(
			LabelproviderecordDaoImpl labelproviderecordDao) {
		this.labelproviderecordDao = labelproviderecordDao;
		setBaseDao(labelproviderecordDao);
	}
}
