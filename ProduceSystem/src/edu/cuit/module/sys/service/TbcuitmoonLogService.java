package edu.cuit.module.sys.service;

import java.util.List;

import edu.cuit.common.service.BaseService;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.entity.TbcuitmoonLog;

public interface TbcuitmoonLogService extends BaseService<TbcuitmoonLog, String> {
	
	/**
	 * 以时间先后顺序获取分页
	 * @return
	 */
	Page findByTimeDesc(int pageNo, int pageCountSize);
}
