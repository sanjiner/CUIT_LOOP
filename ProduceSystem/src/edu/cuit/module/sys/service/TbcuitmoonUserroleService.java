package edu.cuit.module.sys.service;

import edu.cuit.common.service.BaseService;
import edu.cuit.module.sys.entity.TbcuitmoonUserrole;

public interface TbcuitmoonUserroleService extends
		BaseService<TbcuitmoonUserrole, String> {
	
	/**
	 * 通过userid删除 记录
	 * @return 返回影响行数
	 */
	int deleteByUserId(String userId);
	
}
