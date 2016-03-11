package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.Approvedatamanager;
import edu.cuit.module.authc.dao.ApprovedatamanagerDao;
import edu.cuit.module.authc.dao.impl.ApprovedatamanagerDaoImpl;
import edu.cuit.module.authc.service.ApprovedatamanagerService;

@Service
public class ApprovedatamanagerServiceImpl extends
		BaseServiceImpl<Approvedatamanager, String> implements ApprovedatamanagerService {
	private ApprovedatamanagerDao approvedatamanagerDao;
	@Autowired
	private void setApprovedatamanagerDao(ApprovedatamanagerDao approvedatamanagerDao){
			this.approvedatamanagerDao = approvedatamanagerDao;
			setBaseDao(approvedatamanagerDao);
	}
}
