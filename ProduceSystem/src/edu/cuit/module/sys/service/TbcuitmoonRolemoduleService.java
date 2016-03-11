package edu.cuit.module.sys.service;

import java.util.List;

import edu.cuit.common.service.BaseService;
import edu.cuit.module.sys.entity.TbcuitmoonRolemodule;

public interface TbcuitmoonRolemoduleService extends
		BaseService<TbcuitmoonRolemodule, String> {
	
	/**
	 * 判断一个角色集合中是否有该模块
	 * @param cuitMoonModuleId
	 * @param roleIdArray
	 * @return
	 */
	boolean hasThisModule(String cuitMoonModuleId, List<String> roleIdList);
	
	/**
	 * 根据角色Id获取 相关联的模块id
	 * @param cuitMoonRoleId
	 * @return
	 */
	List<?> roleHasModuleIdList(String cuitMoonRoleId);
}
 