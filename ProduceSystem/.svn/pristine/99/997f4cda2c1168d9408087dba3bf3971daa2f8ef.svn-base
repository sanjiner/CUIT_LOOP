package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.IdentificationRecord;
import edu.cuit.module.authc.dao.IdentificationRecordDao;
import edu.cuit.module.authc.service.IdentificationRecordService;

@Service
public class IdentificationRecordServiceImpl extends
		BaseServiceImpl<IdentificationRecord, String> implements IdentificationRecordService {
	private IdentificationRecordDao identificationRecordDao;
	
	@Autowired
	private void setIdentificationRecordDao(IdentificationRecordDao identificationRecordDao){
		this.identificationRecordDao = identificationRecordDao;
		setBaseDao(identificationRecordDao);
	}
	
}
