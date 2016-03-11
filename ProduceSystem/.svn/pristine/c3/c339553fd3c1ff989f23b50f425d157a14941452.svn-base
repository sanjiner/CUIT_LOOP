package edu.cuit.module.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.model.dao.impl.ModelformulateDaoImpl;
import edu.cuit.module.model.entity.Modelformulate;
import edu.cuit.module.model.service.ModelformulateService;

@Service
public class ModelformulateServiceImpl extends
		BaseServiceImpl<Modelformulate, String> implements ModelformulateService {
	private ModelformulateDaoImpl modelformulateDao;

	@Autowired
	private void setModelformulateDao(ModelformulateDaoImpl modelformulateDao) {
		this.modelformulateDao = modelformulateDao;
		setBaseDao(modelformulateDao);
	}
	
	@Override
	public List<?> getList(String modelType) {
		String hql = "from Modelformulate where modelType=?";
		List<?> list = modelformulateDao.find(hql, modelType);
		return list;

	}

	@Override
	public Page listByOrderNum(String strWhere, Integer requsetPageNo, int pageCounSize) {
		String hql = "from Modelformulate where 1=1 ";
		hql += strWhere;
		return getPage(hql, requsetPageNo - 1, pageCounSize);
	}

	@Override
	public List<?> getDetail(String productApproModelId) {
		String hql = "from Modelformulate where productApproModelId=?";
		List<?> list = modelformulateDao.find(hql, productApproModelId);
		return list;
	}
}
