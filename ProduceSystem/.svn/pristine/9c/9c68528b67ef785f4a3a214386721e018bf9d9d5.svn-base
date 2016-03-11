package edu.cuit.module.sys.service;


import java.util.List;

import edu.cuit.common.service.BaseService;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.entity.TbcuitmoonArea;

public interface TbcuitmoonAreaService extends BaseService<TbcuitmoonArea, String> {
	
	/**
	 * 根据父级code获取地区
	 * @return
	 */
	List<?> getByParentCode(long parentCode);
	
	/**
	 * 根据父级code获取地区
	 * @param condition 
	 * @param parentId
	 * @return
	 */
	Page getByParentId(String parentCode, String condition, int pageNo, int pageCountSize);
	
	
	/**
	 * 判断该code 是否已经存在
	 * @param cuitMoonAreaCode
	 * @return
	 */
	boolean isExistByCode(long cuitMoonAreaCode);
	
	String getNameByCode(long code);
	String getNameByCode(String code);
	long getCodeByName(String name);

	/**
	 * 判断 除了自己该code 是否已经存在
	 * @param cuitMoonAreaCode
	 * @return
	 */
	boolean isExistByCodeNotMySelf(String areaId, long cuitMoonAreaCode);
	/**
	 * 通过code 获取地区
	 * @param cuitMoonAreaId
	 * @return
	 */
	TbcuitmoonArea getByCode(long cuitMoonAreaId);
}
