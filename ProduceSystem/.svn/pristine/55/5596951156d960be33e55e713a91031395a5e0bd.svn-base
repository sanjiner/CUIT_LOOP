package edu.cuit.module.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.model.dao.impl.ApprovemodelmanageDaoImpl;
import edu.cuit.module.model.dao.impl.ModelformulateDaoImpl;
import edu.cuit.module.model.entity.Approvemodelmanage;
import edu.cuit.module.model.service.ApprovemodelmanageService;

@Service
public class ApprovemodelmanageServiceImpl extends
		BaseServiceImpl<Approvemodelmanage, String> implements ApprovemodelmanageService {
	private ApprovemodelmanageDaoImpl approvemodelmanageDao;
	private ModelformulateDaoImpl modelformulateDao;
	@Autowired
	private void setApprovemodelmanageDao(ApprovemodelmanageDaoImpl approvemodelmanageDao){
		this.approvemodelmanageDao = approvemodelmanageDao;
		setBaseDao(approvemodelmanageDao);
	}
	@Override
	public List<?> mGradeList() {
		List<?> mglist = approvemodelmanageDao.find("from Approvemodelmanage");
//		List<?> mlist 
//		= modelformulateDao.find("from Modelformulate as mf where mf.productApproModelId", mglist.get(1));
		
		return mglist;
	}
}
