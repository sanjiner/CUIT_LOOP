package edu.cuit.module.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.model.dao.impl.ElementmodelDaoImpl;
import edu.cuit.module.model.entity.Elementmodel;
import edu.cuit.module.model.service.ElementmodelService;

@Service
public class ElementmodelServiceImpl extends
		BaseServiceImpl<Elementmodel, String> implements ElementmodelService {
	private ElementmodelDaoImpl elementmodelDao;

	@Autowired
	private void setElementmodelDao(ElementmodelDaoImpl elementmodelDao) {
		this.elementmodelDao = elementmodelDao;
		setBaseDao(elementmodelDao);
	}
	
	@Override
	public List<?> getListAll(String strWhere) {
		String hql = "from Elementmodel where 1=1 ";
		hql += strWhere;
		List<?> list = elementmodelDao.find(hql, null);
		return list;
	}
	
	@Override
	public List<?> getList(String elementType) {
		String hql = "from Elementmodel as e where e.elementType=?";
		List<?> list = elementmodelDao.find(hql, elementType);
		return list;

	}

	@Override
	public Page listByOrderNum(String strWhere, Integer requsetPageNo, int pageCounSize) {
		String hql = "from Elementmodel as e where 1=1 ";
		hql += strWhere;
		hql += " order by e.addTime desc";
		return getPage(hql, requsetPageNo - 1, pageCounSize);
	}

	@Override
	public List<?> getDetail(String elementNumber) {
		String hql = "from Elementmodel as e where e.elementNumber=?";
		List<?> list = elementmodelDao.find(hql, elementNumber);
		return list;
	}
}
