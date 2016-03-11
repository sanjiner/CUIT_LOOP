package edu.cuit.module.label.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.label.dao.impl.PayrecordDaoImpl;
import edu.cuit.module.label.entity.Payrecord;

@Service
public class PayrecordService extends BaseServiceImpl<Payrecord, String> {
	private PayrecordDaoImpl payrecordDao;
	@Autowired
	private void setPayrecordDao(PayrecordDaoImpl payrecordDao){
		this.payrecordDao = payrecordDao;
		setBaseDao(payrecordDao);
	}
}
