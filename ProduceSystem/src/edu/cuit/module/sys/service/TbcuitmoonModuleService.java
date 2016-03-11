package edu.cuit.module.sys.service;

import java.util.List;
import java.util.Map;

import edu.cuit.common.service.BaseService;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.entity.TbcuitmoonModule;

public interface TbcuitmoonModuleService extends
		BaseService<TbcuitmoonModule, String> {

	/**
	 * 根据父级id,查询条件获取模块分页
	 * @param requsetPageNo
	 * @param pageCounSize
	 * @return
	 */
	Page listChildModuleByOrderNum(String parentId, String search, Integer requsetPageNo, int pageCounSize);
	
	/**
	 * 获取顶级模块
	 * @return
	 */
	List<?> getTopLevelModule();
	
	/**
	 * 通过父级模块id获取
	 * @return
	 */
	List<?> getByParentId(String parentId);
	
	/**
	 * 连同 子模块一同获取
	 * @return
	 */
	List<?> findWithChild();
	
	List<?> findWithChildList();
	
	/**
	 * 连同 子模块一同获取 不收状态影响
	 * @return
	 */
	List<?> findWithChildListWithoutState();
	
	/**
	 * 交换顺序
	 * @param module
	 * @param type 值 desc as
	 * @return
	 */
	boolean sort(TbcuitmoonModule module, String type);
	
	/**
	 * 成功返回 父Id 为cuitMoonParentModuleId 的 最大ordernum
	 * 失败返回-1
	 * @param cuitMoonParentModuleId
	 * @return
	 */
	int getMaxOrderNum(String cuitMoonParentModuleId);
	
}
