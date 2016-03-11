package edu.cuit.module.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.model.dao.impl.ProductinfomationDaoImpl;
import edu.cuit.module.model.entity.Productinfomation;
import edu.cuit.module.model.service.ProductinfomationService;

@Service
public class ProductinfomationServiceImpl extends
		BaseServiceImpl<Productinfomation, String> implements ProductinfomationService {
	private ProductinfomationDaoImpl productinfomationDao;

	@Autowired
	private void setProductinfomationDao(
			ProductinfomationDaoImpl productinfomationDao) {
		this.productinfomationDao = productinfomationDao;
		setBaseDao(productinfomationDao);
	}
	

	@Override
	public List<?> getListAll(String strWhere) {
		String hql = "from Productinfomation where 1=1 ";
		hql += strWhere;
		List<?> list = productinfomationDao.find(hql, null);
		return list;
	}


	@Override
	public List<?> getList(String productType) {
		String hql = "from Productinfomation where productType=?";
		List<?> list = productinfomationDao.find(hql, productType);
		return list;

	}

	@Override
	public Page listByOrderNum(String strWhere, Integer requsetPageNo,
			int pageCounSize) {
		String hql = "from Productinfomation where 1=1 ";
		hql += strWhere;
		hql += " order by addTime desc";
		return getPage(hql, requsetPageNo - 1, pageCounSize);
	}

	@Override
	public List<?> getDetail(String productNumber) {
		String hql = "from Productinfomation where productNumber=?";
		List<?> list = productinfomationDao.find(hql, productNumber);
		return list;
	}
}
