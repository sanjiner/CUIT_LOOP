package edu.cuit.module.authc.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.auchc.entity.Climatopeinfo;
import edu.cuit.module.authc.dao.ClimatopeinfoDao;
import edu.cuit.module.authc.service.ClimatopeinfoService;

@Service
public class ClimatopeinfoServiceImpl extends
		BaseServiceImpl<Climatopeinfo, String> implements ClimatopeinfoService {
	private ClimatopeinfoDao climatopeinfoDao;
	@Autowired
	public void setClimatopeinfoDao (ClimatopeinfoDao climatopeinfoDao){
		this.climatopeinfoDao = climatopeinfoDao;
		setBaseDao(climatopeinfoDao);
	}

}
