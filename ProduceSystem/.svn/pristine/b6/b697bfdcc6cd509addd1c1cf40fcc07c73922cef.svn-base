package edu.cuit.module.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.sys.dao.impl.TbcuitmoonDepartmentDaoImpl;
import edu.cuit.module.sys.entity.TbcuitmoonDepartment;
import edu.cuit.module.sys.service.TbcuitmoonDepartmentService;

@Service
public class TbcuitmoonDepartmentServiceImpl extends
		BaseServiceImpl<TbcuitmoonDepartment, String> implements TbcuitmoonDepartmentService{
	private TbcuitmoonDepartmentDaoImpl tbcuitmoonDepartmentDao;
	
	@Autowired
	private void setTbcuitmoonDepartmentDao(TbcuitmoonDepartmentDaoImpl tbcuitmoonDepartmentDao){
		this.tbcuitmoonDepartmentDao = tbcuitmoonDepartmentDao;
		setBaseDao(tbcuitmoonDepartmentDao);
		
	}
}
