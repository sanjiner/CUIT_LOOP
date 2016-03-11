package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.Bearinginfo;
import edu.cuit.module.authc.dao.BearinginfoDao;
import edu.cuit.module.authc.service.BearinginfoService;

@Service
public class BearinginfoServiceImpl extends
		BaseServiceImpl<Bearinginfo, String> implements BearinginfoService{
	private BearinginfoDao bearinginfoDao;
	
	@Autowired
	public void setBearinginfoDao(BearinginfoDao bearinginfoDao){
		this.bearinginfoDao = bearinginfoDao;
		setBaseDao(bearinginfoDao);
	}
}
