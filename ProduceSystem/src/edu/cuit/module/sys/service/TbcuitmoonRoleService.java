package edu.cuit.module.sys.service;


import java.util.List;

import edu.cuit.common.service.BaseService;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.entity.TbcuitmoonRole;

public interface TbcuitmoonRoleService extends BaseService<TbcuitmoonRole, String> {
	
	/**
	 * 通过 cuitMoonRoleOrderNum 序号 分页获取
	 * @param pageNo
	 * @param pageCounSize
	 * @return
	 */
	Page listByOrderNum(String parentRoleId, String condition, int pageNo, int pageCounSize);
	
	/**
	 * 通过角色状态获取
	 * @param value
	 * @return
	 */
	List<?> findByState(int value);
	
	/**
	 * 通过角色排序获得
	 * @return
	 */
	List<?> findByOrderNum(String parentId);

}
