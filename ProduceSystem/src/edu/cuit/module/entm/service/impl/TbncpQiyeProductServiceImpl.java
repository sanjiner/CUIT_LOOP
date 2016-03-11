package edu.cuit.module.entm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.entm.dao.impl.TbncpQiyeProductDaoImpl;
import edu.cuit.module.entm.entity.TbncpQiyeProduct;
import edu.cuit.module.entm.service.TbncpQiyeProductService;

@Service
public class TbncpQiyeProductServiceImpl extends
		BaseServiceImpl<TbncpQiyeProduct, String> implements TbncpQiyeProductService {
	private TbncpQiyeProductDaoImpl tbncpQiyeProductDao;
	
	@Autowired
	private void setTbncpQiyeProductDao(TbncpQiyeProductDaoImpl tbncpQiyeProductDao){
		this.tbncpQiyeProductDao = tbncpQiyeProductDao;
		setBaseDao(tbncpQiyeProductDao);
	}
}
