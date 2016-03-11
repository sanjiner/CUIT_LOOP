package edu.cuit.module.entm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.entm.dao.ProducepicDao;
import edu.cuit.module.entm.entity.Producepic;
import edu.cuit.module.entm.service.ProducepicService;

@Service
public class ProducepicServiceImpl extends BaseServiceImpl<Producepic, String> implements ProducepicService{
	private ProducepicDao producepicDao;

	@Autowired
	public void setProducepicDao(ProducepicDao producepicDao) {
		this.producepicDao = producepicDao;
		setBaseDao(producepicDao);
	}
	
}
