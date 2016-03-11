package edu.cuit.module.infom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.infom.dao.impl.WeatherstationinfoDaoImpl;
import edu.cuit.module.infom.entity.Weatherstationinfo;
import edu.cuit.module.infom.service.WeatherstationinfoService;

@Service
public class WeatherstationinfoServiceImpl extends
		BaseServiceImpl<Weatherstationinfo, String> implements WeatherstationinfoService {
	private WeatherstationinfoDaoImpl weatherstationinfoDao;
	@Autowired
	private void setWeatherstationinfo(WeatherstationinfoDaoImpl weatherstationinfoDao){
		this.weatherstationinfoDao = weatherstationinfoDao;
		setBaseDao(weatherstationinfoDao);
	}
	
	@SuppressWarnings("unchecked")
	public String getCodeByName(String name){
		String code = "";
		List<Weatherstationinfo> list = (List<Weatherstationinfo>)find("from Weatherstationinfo where name = ?",name);
		if (list.size() > 0) {
			code = list.get(0).getBelongTo();
		}
		return code;
	}
}
