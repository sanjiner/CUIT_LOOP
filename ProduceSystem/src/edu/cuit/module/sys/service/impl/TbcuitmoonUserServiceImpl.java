package edu.cuit.module.sys.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.dao.impl.TbcuitmoonUserDaoImpl;
import edu.cuit.module.sys.entity.TbcuitmoonJurisdiction;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonUserService;

@Service
public class TbcuitmoonUserServiceImpl extends BaseServiceImpl<TbcuitmoonUser, String> implements TbcuitmoonUserService{
	private TbcuitmoonUserDaoImpl tbcuitmoonUserDao;
	
	@Autowired
	private void setTbcuitmoonUserDao(TbcuitmoonUserDaoImpl tbcuitmoonUserDao){
		this.tbcuitmoonUserDao = tbcuitmoonUserDao;
		setBaseDao(tbcuitmoonUserDao);
	}
	
	/**
	 * 根据名称返回用户
	 * @param username
	 * @return 若没有找到返回null
	 */
	public TbcuitmoonUser findByName(String username){
		String hql = "from TbcuitmoonUser as user where user.cuitMoonUserName=?";
		List<?> users = tbcuitmoonUserDao.find(hql, username);
		if(users.size() != 0){
			return (TbcuitmoonUser) users.get(0);
		}
		return null;
	}
	
	/**
	 * 通过用户名获取所用的角色名称
	 * @param username
	 * @return
	 */
	public Set<String> findRolesIds(String username) {
		Set<String> rolesIds = new HashSet<String>();
		TbcuitmoonUser user = findByName(username);
		if(user != null){
			for(TbcuitmoonRole role : user.getTbcuitmoonRoles()){
				if(role.getCuitMoonRoleStatus() == 1){
					rolesIds.add(role.getCuitMoonRoleId());
				}
			}
		}
		return rolesIds;
	}

	public Set<String> findJurisdictionIds(String username) {
		Set<String> jurisdictionIds = new HashSet<String>();
		TbcuitmoonUser user = findByName(username);
		if(user != null){
			for(TbcuitmoonRole role : user.getTbcuitmoonRoles()){
				for(TbcuitmoonJurisdiction Jurisdiction : role.getTbcuitmoonJurisdictions() ){
					jurisdictionIds.add(Jurisdiction.getCuitMoonJurisdictionId());
				}
			}
		}
		return jurisdictionIds;
	}
	
	@Override
	public Set<String> findJurisdictionCodes(String username) {
		Set<String> jurisdictionCodes = new HashSet<String>();
		TbcuitmoonUser user = findByName(username);
		if(user != null){
			for(TbcuitmoonRole role : user.getTbcuitmoonRoles()){
				if(role.getCuitMoonRoleStatus() == 0){
					continue;
				}
				for(TbcuitmoonJurisdiction Jurisdiction : role.getTbcuitmoonJurisdictions() ){
					if(Jurisdiction.getCuitMoonJurisdictionStatus()==0){
						continue;
					}
					jurisdictionCodes.add(Jurisdiction.getCuitMoonJurisdictionCode());
				}
			}
		}
		return jurisdictionCodes;
	}

	/**
	 * 修改用户密码
	 */
	@Override
	public boolean changePassword(String cuitMoonUserId, String oldPassword,
			String newPassword) {
		String hql = "update TbcuitmoonUser user set user.cuitMoonUserPassWord=? where user.cuitMoonUserId=? and user.cuitMoonUserPassWord=?";
		int row = bulkUpdate(hql, newPassword, cuitMoonUserId, oldPassword);
		if(row != 0){
			return true;
		}
		return false;
	}

	@Override
	public Page findByAreaCode(String areaCode, int pageNo, int pageCountSize) {
		
		String hql = "from TbcuitmoonUser as user where user.cuitMoonAreaId like '%" + areaCode +"%'";
		
		return getPage(hql, pageNo-1, pageCountSize);
	}

	@Override
	public Page findByConditions(String username, String realname,
			int requsetPageNo, int pageCountSize) {
		String hql = "from TbcuitmoonUser as user where user.cuitMoonUserRealName like '%"+realname+"%' or user.cuitMoonUserName like '%"+username+"%'";
		return getPage(hql, requsetPageNo-1, pageCountSize);
	}

	@Override
	public boolean hasUsername(String cuitMoonUserName) {
		String hql = "select count(*) from TbcuitmoonUser as user where user.cuitMoonUserName=?";
		List<?> result = find(hql, cuitMoonUserName);
		String num = result.get(0).toString();
		if( num.equals("0")){
			return false;
		}
		return true;
	}

}
