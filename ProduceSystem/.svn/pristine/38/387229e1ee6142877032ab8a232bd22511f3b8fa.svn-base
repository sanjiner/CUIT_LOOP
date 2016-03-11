package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.Disastoursdata;
import edu.cuit.module.authc.dao.DisastoursdataDao;
import edu.cuit.module.authc.service.DisastoursdataService;

@Service
public class DisastoursdataServiceImpl extends BaseServiceImpl<Disastoursdata, String> implements DisastoursdataService {
	private DisastoursdataDao disastoursdataDao;
	
	@Autowired
	private void setDisastoursdataDao(DisastoursdataDao disastoursdataDao){
		this.disastoursdataDao = disastoursdataDao;
		setBaseDao(disastoursdataDao);
	}
}
