package edu.cuit.module.infom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.infom.dao.impl.ExpertsgroupDaoImpl;
import edu.cuit.module.infom.entity.Expertsgroup;
import edu.cuit.module.infom.service.ExpertsgroupService;

@Service
public class ExpertsgroupServiceImpl extends BaseServiceImpl<Expertsgroup, String> implements ExpertsgroupService {
	private ExpertsgroupDaoImpl expertsgroupDao;
	@Autowired
	private void setExpertsgroupDao(ExpertsgroupDaoImpl expertsgroupDao){
		this.expertsgroupDao = expertsgroupDao;
		setBaseDao(expertsgroupDao);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getGroupLevel(String region) {
		String level = "";
		String hql = "from Expertsgroup where region = ?";
		List<Expertsgroup> list =(List<Expertsgroup>) expertsgroupDao.find(hql, region);
		if (list != null && list.size() > 0) {
			level = list.get(0).getExpertsLevel();
		}
		return level;
	}
}
