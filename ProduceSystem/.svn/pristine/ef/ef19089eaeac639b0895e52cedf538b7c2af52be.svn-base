package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.Cimatequalityapproveprocess;
import edu.cuit.module.authc.dao.CimatequalityapproveprocessDao;
import edu.cuit.module.authc.service.CimatequalityapproveprocessService;

@Service
public class CimatequalityapproveprocessServiceImpl extends
		BaseServiceImpl<Cimatequalityapproveprocess, String> implements CimatequalityapproveprocessService {
	private CimatequalityapproveprocessDao cimatequalityapproveprocessDao;
	
	@Autowired
	private void setCimatequalityapproveprocessDao(CimatequalityapproveprocessDao cimatequalityapproveprocessDao){
		this.cimatequalityapproveprocessDao = cimatequalityapproveprocessDao;
		setBaseDao(cimatequalityapproveprocessDao);
	}
}
