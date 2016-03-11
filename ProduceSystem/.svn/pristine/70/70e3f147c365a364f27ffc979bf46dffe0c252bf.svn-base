package edu.cuit.module.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.sys.dao.impl.TbcuitmoonUserroleDaoImpl;
import edu.cuit.module.sys.entity.TbcuitmoonUserrole;
import edu.cuit.module.sys.service.TbcuitmoonUserroleService;

@Service
public class TbcuitmoonUserroleServiceImpl extends
		BaseServiceImpl<TbcuitmoonUserrole, String> implements TbcuitmoonUserroleService{
	private TbcuitmoonUserroleDaoImpl tbcuitmoonUserroleDao;
	
	@Autowired
	private void setTbcuitmoonUserroleDao(TbcuitmoonUserroleDaoImpl tbcuitmoonUserroleDao){
		this.tbcuitmoonUserroleDao = tbcuitmoonUserroleDao;
		setBaseDao(tbcuitmoonUserroleDao);
		
	}

	@Override
	public int deleteByUserId(String userId) {
		String hql = "delete from TbcuitmoonUserrole as ur where ur.cuitMoonUserId=?";
		return bulkUpdate(hql, userId);
	}
	
}
