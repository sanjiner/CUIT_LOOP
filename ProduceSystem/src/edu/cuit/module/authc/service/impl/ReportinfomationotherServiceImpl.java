package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.Reportinfomationother;
import edu.cuit.module.authc.dao.ReportinfomationotherDao;
import edu.cuit.module.authc.service.ReportinfomationotherService;

@Service
public class ReportinfomationotherServiceImpl extends
		BaseServiceImpl<Reportinfomationother, String> implements
		ReportinfomationotherService {
	private ReportinfomationotherDao reportinfomationotherDao;
	@Autowired
	public void setReportinfomationotherDao(ReportinfomationotherDao reportinfomationotherDao){
		this.reportinfomationotherDao = reportinfomationotherDao;
		setBaseDao(reportinfomationotherDao);
	}
}
