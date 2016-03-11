package edu.cuit.module.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.dao.impl.TbcuitmoonRoleDaoImpl;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.service.TbcuitmoonRoleService;

@Service
public class TbcuitmoonRoleServiceImpl extends BaseServiceImpl<TbcuitmoonRole, String> implements TbcuitmoonRoleService{
	private TbcuitmoonRoleDaoImpl tbcuitmoonRoleDao;
	
	@Autowired
	private void setTbcuitmoonRoleDao(TbcuitmoonRoleDaoImpl tbcuitmoonRoleDao) {
		this.tbcuitmoonRoleDao = tbcuitmoonRoleDao;
		setBaseDao(tbcuitmoonRoleDao);
	}

	@Override
	public Page listByOrderNum(String parentRoleId, String condition, int pageNo, int pageCounSize) {
		String hql;
		if(StringUtils.hasLength(condition)){
			hql = "from TbcuitmoonRole as r where r.cuitMoonParentRoleId='"+parentRoleId+"' and r.cuitMoonRoleName='"+condition+"' order by r.cuitMoonRoleOrderNum";
		}else{
			hql = "from TbcuitmoonRole as r where r.cuitMoonParentRoleId='"+parentRoleId+"' order by r.cuitMoonRoleOrderNum";
		}
		
		return getPage(hql, pageNo-1, pageCounSize);
	}

	@Override
	public List<?> findByState(int value) {
		String hql = "from TbcuitmoonRole as r where r.cuitMoonRoleStatus=? order by r.cuitMoonRoleOrderNum";
		return find(hql,new Long(value));
	}

	@Override
	public List<?> findByOrderNum(String parentId) {
		String hql = "from TbcuitmoonRole as r where r.cuitMoonParentRoleId=? order by r.cuitMoonRoleOrderNum";
		return find(hql,parentId);
	}
	
}
