package edu.cuit.module.origin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.origin.dao.impl.ProductapplyDaoImpl;
import edu.cuit.module.origin.entity.Productapply;
import edu.cuit.module.origin.service.ProductapplyService;

@Service
public class ProductapplyServiceImpl extends BaseServiceImpl<Productapply, String> implements ProductapplyService {
	private ProductapplyDaoImpl productapplyDao;
	@Autowired
	private void setProductapplyDao(ProductapplyDaoImpl productapplyDao){
		this.productapplyDao = productapplyDao;
		setBaseDao(productapplyDao);
	}
	
	@Override
	public List<?> modellist(String user) {		
		String hql = "from Productapply as pmodel where pmodel.applyPerson=?";
		List<?> list = productapplyDao.find(hql, user);
		return list;
	}

	@Override
	public Page findPageByTime(int pageNo, int pageCountSize) {
		String hql = "from Productapply as pmodel order by pmodel.applyTime desc";
		return getPage(hql, pageNo, pageCountSize);
	}

	@Override
	public String Insertmodel(String applyPerson, String applyCompany, String phone, String email,String address, 
			String productName,String productBrand, String productNum, String productValue, String labelNum, String originDescription) {
		Productapply model = new Productapply();
		String originId =  UUID.randomUUID().toString().replace("-", "");
		Date applyTime = new Date();
		Integer labelnums = Integer.parseInt(labelNum);
		
		model.setOriginId(originId);
		model.setApplyPerson(applyPerson);
		model.setApplyTime(applyTime);
		model.setApplyCompany(applyCompany);
		model.setConstract(phone);
		model.setPersonAdress(address);
		model.setRemark(email);
		model.setOriginName(productName);
		model.setProductBrand(productBrand);
		model.setProductNum(productNum);
		model.setProductValue(productValue);
		model.setLabelNum(labelnums);
		model.setOriginDescription(originDescription);
		model.setOrignStatus("待审核");
		
		productapplyDao.save(model); 
		return "T";
	}

	@Override
	public Productapply getPAmodel(String originId) {
		Productapply model = new Productapply();
		model = productapplyDao.get(originId);
		return model;
	}

	@Override
	public String Updatemodel(String originId, String applyPerson,
			String applyCompany, String phone, String email, String address,
			String productName, String productBrand, String productNum,
			String productValue, String labelNum, String originDescription) {
		
		Productapply model = new Productapply();
		Date applyTime = new Date();
		Integer labelnums = Integer.parseInt(labelNum);
		
		model.setOriginId(originId);
		model.setApplyPerson(applyPerson);
		model.setApplyTime(applyTime);
		model.setApplyCompany(applyCompany);
		model.setConstract(phone);
		model.setPersonAdress(address);
		model.setRemark(email);
		model.setOriginName(productName);
		model.setProductBrand(productBrand);
		model.setProductNum(productNum);
		model.setProductValue(productValue);
		model.setLabelNum(labelnums);
		model.setOriginDescription(originDescription);
		model.setOrignStatus("待审核");
		
		productapplyDao.update(model);
		return null;
	}
	
}
