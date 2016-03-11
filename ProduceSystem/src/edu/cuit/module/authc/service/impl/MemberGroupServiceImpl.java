package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.MemberGroup;
import edu.cuit.module.authc.dao.MemberGroupDao;
import edu.cuit.module.authc.dao.impl.MemberGroupDaoImpl;
import edu.cuit.module.authc.service.MemberGroupService;

@Service
public class MemberGroupServiceImpl extends BaseServiceImpl<MemberGroup, String> implements MemberGroupService {
	private MemberGroupDao memberGroupDao;

	@Autowired
	private void setMemberGroupDao(MemberGroupDao memberGroupDao) {
		this.memberGroupDao = memberGroupDao;
		setBaseDao(memberGroupDao);
	}
	 
	public void delete(String hql){
	}
}
