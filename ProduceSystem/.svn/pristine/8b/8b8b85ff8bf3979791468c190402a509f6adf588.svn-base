package edu.cuit.module.entm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.entm.dao.TbncpQiyeNewsDao;
import edu.cuit.module.entm.entity.TbncpQiyeNews;
import edu.cuit.module.entm.service.TbncpQiyeNewsService;

@Service
public class TbncpQiyeNewsServiceImpl extends BaseServiceImpl<TbncpQiyeNews, String> implements TbncpQiyeNewsService {
	private TbncpQiyeNewsDao tbncpQiyeNewsDao;
	
	@Autowired
	private void setTbncpQiyeNewsDao(TbncpQiyeNewsDao tbncpQiyeNewsDao){
		this.tbncpQiyeNewsDao = tbncpQiyeNewsDao;
		setBaseDao(tbncpQiyeNewsDao);
	}
}
