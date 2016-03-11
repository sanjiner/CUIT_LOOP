package edu.cuit.module.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.dao.impl.TbcuitmoonLogDaoImpl;
import edu.cuit.module.sys.entity.TbcuitmoonLog;
import edu.cuit.module.sys.service.TbcuitmoonLogService;

@Service
public class TbcuitmoonLogServiceImpl extends BaseServiceImpl<TbcuitmoonLog, String> implements TbcuitmoonLogService {
	private TbcuitmoonLogDaoImpl tbcuitmoonLogDao;
	@Autowired
	private void setTbcuitmoonLogDao(TbcuitmoonLogDaoImpl tbcuitmoonLogDao){
		this.tbcuitmoonLogDao = tbcuitmoonLogDao;
		setBaseDao(tbcuitmoonLogDao);
	}
	@Override
	public Page findByTimeDesc(int pageNo, int pageCountSize) {
		
		String hql = "from TbcuitmoonLog as log order by log.cuitMoonLogOperationTime desc";
		return getPage(hql, pageNo-1, pageCountSize);
	}
}
