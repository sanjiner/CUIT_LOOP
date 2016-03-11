package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.QualityIdentification;
import edu.cuit.module.authc.dao.QualityIdentificationDao;
import edu.cuit.module.authc.service.QualityIdentificationService;

@Service
public class QualityIdentificationServiceImpl extends
		BaseServiceImpl<QualityIdentification, String> implements QualityIdentificationService {
	private QualityIdentificationDao qualityIdentificationDao;
	
	@Autowired
	private void setQualityIdentificationDao(QualityIdentificationDao qualityIdentificationDao){
		this.qualityIdentificationDao = qualityIdentificationDao;
		setBaseDao(qualityIdentificationDao);
	}
}
