package edu.cuit.module.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.dao.impl.TbcuitmoonJurisdictionDaoImpl;
import edu.cuit.module.sys.entity.TbcuitmoonJurisdiction;
import edu.cuit.module.sys.service.TbcuitmoonJurisdictionService;

@Service
public class TbcuitmoonJurisdictionServiceImpl extends
		BaseServiceImpl<TbcuitmoonJurisdiction, String> implements TbcuitmoonJurisdictionService {
	private TbcuitmoonJurisdictionDaoImpl tbcuitmoonJurisdictionDao;
	@Autowired
	private void setTbcuitmoonJurisdictionDao(TbcuitmoonJurisdictionDaoImpl tbcuitmoonJurisdictionDao){
		this.tbcuitmoonJurisdictionDao = tbcuitmoonJurisdictionDao;
		setBaseDao(tbcuitmoonJurisdictionDao);
	}
	
	@Override
	public Page listByModule(String moduleId, String condition,
			Integer requsetPageNo, int pageCounSize) {
		String hql;
		if(StringUtils.hasLength(condition)){
			hql = "from TbcuitmoonJurisdiction as jur where jur.cuitMoonModuleId='"+moduleId+"' and jur.cuitMoonJurisdictionName='"+condition+"' order by jur.cuitMoonJurisdictionOrderNum";
		}else{
			hql = "from TbcuitmoonJurisdiction as jur where jur.cuitMoonModuleId='"+moduleId+"' order by jur.cuitMoonJurisdictionOrderNum";
		}
		
		return getPage(hql, requsetPageNo-1, pageCounSize);
		
	}
}
