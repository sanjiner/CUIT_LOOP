package edu.cuit.module.model.service;

import java.util.List;

import edu.cuit.common.service.BaseService;
import edu.cuit.common.util.Page;
import edu.cuit.module.model.entity.Elementmodel;

public interface ElementmodelService extends BaseService<Elementmodel, String> {
	
	public List<?> getListAll(String strWhere);
	/*
	 * 根据“要素类别”来查询气象要素。
	 */
	public List<?> getList(String elementType);
	
	/*
	 * 分页
	 * @param requsetPageNo
	 * @param pageCounSize
	 * @return
	 */
	Page listByOrderNum(String strWhere,Integer requsetPageNo, int pageCounSize);
	
	/*
	 * 详情
	 */
	public List<?> getDetail(String elementNumber);
}
