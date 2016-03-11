package edu.cuit.module.sys.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.sys.dao.impl.TbcuitmoonRolemoduleDaoImpl;
import edu.cuit.module.sys.entity.TbcuitmoonRolemodule;
import edu.cuit.module.sys.service.TbcuitmoonRolejurisdictionService;
import edu.cuit.module.sys.service.TbcuitmoonRolemoduleService;

@Service
public class TbcuitmoonRolemoduleServiceImpl extends
		BaseServiceImpl<TbcuitmoonRolemodule, String> implements TbcuitmoonRolemoduleService{
	private TbcuitmoonRolemoduleDaoImpl tbcuitmoonRolemoduleDao;
	
	@Autowired
	private void setTbcuitmoonRolemoduleDao(TbcuitmoonRolemoduleDaoImpl tbcuitmoonRolemoduleDao){
		this.tbcuitmoonRolemoduleDao = tbcuitmoonRolemoduleDao;
		setBaseDao(tbcuitmoonRolemoduleDao);
	}

	@Override
	public boolean hasThisModule(String cuitMoonModuleId,List<String> roleIdList) {
		StringBuilder sb = new StringBuilder();
		for(String roleId : roleIdList){
			sb.append("'");
			sb.append(roleId);
			sb.append("'");
			sb.append(",");
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
			String hql = "select count(*) from TbcuitmoonRolemodule as roleModule where roleModule.cuitMoonModuleId='"+cuitMoonModuleId+"' and roleModule.cuitMoonRoleId in ("+sb.toString()+")";
			List<?> list = find(hql);
			if(list.get(0).toString().equals("0")){
				return false;
			}
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public List<?> roleHasModuleIdList(String cuitMoonRoleId) {
		String hql = "select rm.cuitMoonModuleId from TbcuitmoonRolemodule as rm where rm.cuitMoonRoleId=?";
		return find(hql,cuitMoonRoleId);
	}

}
