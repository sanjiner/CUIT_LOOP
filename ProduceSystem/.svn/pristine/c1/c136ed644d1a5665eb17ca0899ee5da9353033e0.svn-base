package edu.cuit.module.label.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.label.dao.impl.ProvidecodecaseDaoImpl;
import edu.cuit.module.label.entity.Providecodecase;

@Service
public class ProvidecodecaseService extends
		BaseServiceImpl<Providecodecase, String> {
	private ProvidecodecaseDaoImpl providecodecaseDao;
	@Autowired
	private void setProvidecodecaseDao(ProvidecodecaseDaoImpl providecodecaseDao){
		this.providecodecaseDao = providecodecaseDao;
		setBaseDao(providecodecaseDao);
	}
}
