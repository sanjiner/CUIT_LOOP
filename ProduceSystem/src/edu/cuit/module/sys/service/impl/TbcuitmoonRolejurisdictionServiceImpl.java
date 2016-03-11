package edu.cuit.module.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.sys.dao.impl.TbcuitmoonRolejurisdictionDaoImpl;
import edu.cuit.module.sys.entity.TbcuitmoonRolejurisdiction;
import edu.cuit.module.sys.service.TbcuitmoonRolejurisdictionService;

@Service
public class TbcuitmoonRolejurisdictionServiceImpl extends
		BaseServiceImpl<TbcuitmoonRolejurisdiction, String> implements TbcuitmoonRolejurisdictionService{
	private TbcuitmoonRolejurisdictionDaoImpl tbcuitmoonRolejurisdictionDao;
	
	@Autowired
	private void setTbcuitmoonRolejurisdictionDao(TbcuitmoonRolejurisdictionDaoImpl tbcuitmoonRolejurisdictionDao){
		this.tbcuitmoonRolejurisdictionDao = tbcuitmoonRolejurisdictionDao;
		setBaseDao(tbcuitmoonRolejurisdictionDao);
	}

	@Override
	public int deleteByJurId(String jurisdictionId) {
		String hql = "delete from TbcuitmoonRolejurisdiction as rolejur where rolejur.cuitMoonJurisdictionId=?";
		return bulkUpdate(hql, jurisdictionId);
	}

	@Override
	public int deleteByRoleId(String cuitMoonRoleId) {
		String hql = "delete from TbcuitmoonRolejurisdiction as rolejur where rolejur.cuitMoonRoleId=?";
		return bulkUpdate(hql, cuitMoonRoleId);
	}
}
