package edu.cuit.module.infom.service;

import edu.cuit.common.service.BaseService;
import edu.cuit.module.infom.entity.Expertsgroup;

public interface ExpertsgroupService extends BaseService<Expertsgroup, String> {
	
	public String getGroupLevel(String region);
}
