package edu.cuit.module.sys.service;

import edu.cuit.common.service.BaseService;
import edu.cuit.module.sys.entity.TbcuitmoonRolejurisdiction;

public interface TbcuitmoonRolejurisdictionService extends
		BaseService<TbcuitmoonRolejurisdiction, String> {
	
	/**
	 *通过 jurisdiction id删除
	 */
	int deleteByJurId(String jurisdictionId);
	
	/**
	 * 通过role id 删除
	 * @param cuitMoonRoleId
	 * @return
	 */
	int deleteByRoleId(String cuitMoonRoleId);
}
 