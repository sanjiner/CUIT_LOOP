package edu.cuit.module.authc.service.impl;

import java.util.List;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.authc.dao.ApplyDao;
import edu.cuit.module.authc.service.ApplyService;

@Service
public class ApplyServiceImpl extends BaseServiceImpl<Apply, String> implements
		ApplyService {
 
	private ApplyDao applyDao;

	@Autowired
	private void setApplyDao(ApplyDao applyDao) {
		this.applyDao = applyDao;
		setBaseDao(applyDao);
	} 

	@Override
	public List<?> findAllByTime() {
		String hql = "from Apply as a order by a.applyTime desc";

		return find(hql);
	}

	@Override
	public Page findPageByTime(int pageNo, int pageCountSize) {
		String hql = "from Apply as a order by a.applyTime desc";
		return getPage(hql, pageNo-1, pageCountSize);
	}

	@Override
	public List<Map<String, String>> select(String hql) {
		return applyDao.select(hql);
	}
}
