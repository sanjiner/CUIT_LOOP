package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.ClimateQualitycertificationExperts;
import edu.cuit.module.authc.dao.ClimateQualitycertificationExpertsDao;
import edu.cuit.module.authc.dao.impl.ClimateQualitycertificationExpertsDaoImpl;
import edu.cuit.module.authc.service.ClimateQualitycertificationExpertsService;

@Service
public class ClimateQualitycertificationExpertsServiceImpl extends
		BaseServiceImpl<ClimateQualitycertificationExperts, String> implements ClimateQualitycertificationExpertsService{
	private ClimateQualitycertificationExpertsDao climateQualitycertificationExpertsDao;
	
	@Autowired
	private void setClimateQualitycertificationExpertsDao(ClimateQualitycertificationExpertsDao climateQualitycertificationExpertsDao){
		this.climateQualitycertificationExpertsDao  = climateQualitycertificationExpertsDao;
		setBaseDao(climateQualitycertificationExpertsDao);
	}
}
