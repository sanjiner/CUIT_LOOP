package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.Reportinfomation;
import edu.cuit.module.authc.dao.ReportinfomationDao;
import edu.cuit.module.authc.service.ReportinfomationService;

@Service
public class ReportinfomationServiceImpl extends
		BaseServiceImpl<Reportinfomation, String> implements ReportinfomationService{
	private ReportinfomationDao reportinfomationDao;
	
	@Autowired
	private void setReportinfomationDao(ReportinfomationDao reportinfomationDao){
		this.reportinfomationDao = reportinfomationDao;
		setBaseDao(reportinfomationDao);
	}
}
