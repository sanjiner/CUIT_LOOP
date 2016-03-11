package edu.cuit.module.origin.service;

import java.util.List;

import edu.cuit.common.service.BaseService;
import edu.cuit.common.util.Page;
import edu.cuit.module.origin.entity.Productapply;

public interface ProductapplyService extends BaseService<Productapply, String> {
	
	/**
	 * 根据申请人查找溯源申请
	 * @param user
	 * @return 若没有找到返回null
	 */
	public List<?> modellist(String user);
	
	/**
	 * 返回分页
	 * @param pageNo 页号
	 * @param pageCountSize 每页条数
	 * @return
	 */
	Page findPageByTime(int pageNo, int pageCountSize);
	
	/**
	 * 插入一条溯源申请记录
	 * @param 实体参数
	 * @return
	 */
	public String Insertmodel(String applyPerson, String applyCompany, String phone, String email,String address, 
			String productName,String productBrand, String productNum, String productValue, String labelNum, String originDescription);
	
	/**
	 * 根据主键获取溯源申请
	 * @param 溯源编号
	 * @return
	 */
	public Productapply getPAmodel(String originId);
	
	/**
	 * 更新溯源申请记录
	 * @param 实体参数
	 * @return
	 */
	public String Updatemodel(String originId,String applyPerson, String applyCompany, String phone, String email,String address, 
			String productName,String productBrand, String productNum, String productValue, String labelNum, String originDescription);
	
}
