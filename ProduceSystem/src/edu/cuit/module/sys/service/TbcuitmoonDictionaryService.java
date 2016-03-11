package edu.cuit.module.sys.service;

import java.util.List;

import edu.cuit.common.service.BaseService;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;

public interface TbcuitmoonDictionaryService extends
		BaseService<TbcuitmoonDictionary, String> {
	/*
	 * 详情
	 */
	public List<?> dictionaryList(String cuitMoon_ParentDictionaryCode);

	public String getDicNameByCode(long code);
	
	public String getDicNameByCode(String code);

	public String getCodeByName(String name);
	
	public String getRemarkByCode(long code);

	public List<?> findByParentCode(long code);
	
	public Page getByParentId(Long parentCode, String condition,
			Integer requsetPageNo, int pageCounSize);

	public boolean hasThisCode(Long cuitMoonDictionaryCode);
	
	/**
	 * 除了自己有没有重复的
	 * @param cuitMoonDictionaryCode
	 */
	public boolean hasThisCodeNotMe(String dicId,Long dicCode);

}
