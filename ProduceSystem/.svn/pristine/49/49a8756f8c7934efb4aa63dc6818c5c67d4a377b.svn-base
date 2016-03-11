package edu.cuit.module.sys.service;

import java.util.List;
import java.util.Set;

import edu.cuit.common.service.BaseService;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.entity.TbcuitmoonUser;

public interface TbcuitmoonUserService extends BaseService<TbcuitmoonUser, String>{
	
	/**
	 * 根据名称返回用户
	 * @param username
	 * @return 若没有找到返回null
	 */
	public TbcuitmoonUser findByName(String username);
	
	/**
	 * 通过用户名获取所用的角色名称
	 * @param username
	 * @return
	 */
	public Set<String> findRolesIds(String username);
	
	public Set<String> findJurisdictionIds(String username);
	
	public Set<String> findJurisdictionCodes(String username);
	
	/**
	 * 修改用户密码
	 * @param cuitMoonUserId
	 * @param oldPassword
	 * @param newPassword
	 */
	public boolean changePassword(String cuitMoonUserId, String oldPassword,
			String newPassword);
	
	/**
	 * 根据地区code获取用户
	 * @param areaCode
	 * @return
	 */
	public Page findByAreaCode(String areaCode, int pageNo, int pageCountSize);

	/**
	 * 搜索条件获取用户
	 * @param areaCode
	 * @return
	 */
	public Page findByConditions(String areaCode, String condition,
			int requsetPageNo, int pageCounSize);
	
	/**
	 * 检查是否存在该用户名
	 * @param cuitMoonUserName
	 * @return
	 */
	public boolean hasUsername(String cuitMoonUserName);
	
}
