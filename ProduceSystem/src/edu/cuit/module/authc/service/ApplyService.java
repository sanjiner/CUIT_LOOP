package edu.cuit.module.authc.service;

import java.util.List;
import java.util.Map;

import edu.cuit.common.service.BaseService;
import edu.cuit.common.util.Page;
import edu.cuit.module.auchc.entity.Apply;

public interface ApplyService extends BaseService<Apply, String> {

	/**
	 * 以时间顺序获取所有的申请
	 * 
	 * @return
	 */
	List<?> findAllByTime();

	/** 
	 * 返回分页
	 * 
	 * @param pageNo
	 *            页号
	 * @param pageCountSize
	 *            每页条数
	 * @return
	 */
	Page findPageByTime(int pageNo, int pageCountSize);
	
	public List<Map<String, String>> select(final String hql);
}
