package edu.cuit.module.label.service;

import edu.cuit.common.service.BaseService;
import edu.cuit.common.util.Page;
import edu.cuit.module.label.entity.Labermanagement;

public interface LabermanagementService extends
		BaseService<Labermanagement, String> {
	/**
	 * 分页
	 * @param requsetPageNo
	 * @param pageCounSize
	 * @return
	 */
	Page listByOrderNum(Integer requsetPageNo, int pageCounSize);
}
