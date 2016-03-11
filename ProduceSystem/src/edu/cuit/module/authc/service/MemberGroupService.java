package edu.cuit.module.authc.service;


import edu.cuit.common.service.BaseService;
import edu.cuit.module.auchc.entity.MemberGroup;

public interface MemberGroupService extends BaseService<MemberGroup, String> {
	public void delete(String hql); 
}
