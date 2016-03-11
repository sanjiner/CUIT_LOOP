package edu.cuit.module.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.sys.dao.impl.TbcuitmoonInformationDaoImpl;
import edu.cuit.module.sys.entity.TbcuitmoonInformation;
import edu.cuit.module.sys.service.TbcuitmoonInformationService;

@Service
public class TbcuitmoonInformationServiceImpl extends
		BaseServiceImpl<TbcuitmoonInformation, String> implements TbcuitmoonInformationService {
	private TbcuitmoonInformationDaoImpl tbcuitmoonInformationDao;
	
	@Autowired
	private void setTbcuitmoonInformationDao(TbcuitmoonInformationDaoImpl tbcuitmoonInformationDao){
		this.tbcuitmoonInformationDao = tbcuitmoonInformationDao;
		setBaseDao(tbcuitmoonInformationDao);
	}
}
