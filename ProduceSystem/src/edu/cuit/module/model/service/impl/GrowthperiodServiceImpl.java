package edu.cuit.module.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.model.dao.GrowthperiodDao;
import edu.cuit.module.model.entity.Growthperiod;
import edu.cuit.module.model.service.GrowthperiodService;

@Service
public class GrowthperiodServiceImpl extends
		BaseServiceImpl<Growthperiod, String> implements GrowthperiodService {
	private GrowthperiodDao growthperiodDao;
	
	@Autowired
	public void setGrowthperiodDao(GrowthperiodDao growthperiodDao){
		this.growthperiodDao = growthperiodDao;
		setBaseDao(growthperiodDao);
	}
}
