package edu.cuit.module.authc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.auchc.entity.Elementsprocessvalue;
import edu.cuit.module.authc.dao.ElementsprocessvalueDao;
import edu.cuit.module.authc.service.ElementsprocessvalueService;

@Service
public class ElementsprocessvalueServiceImpl extends BaseServiceImpl<Elementsprocessvalue, String> implements ElementsprocessvalueService {
	private ElementsprocessvalueDao elementsprocessvalueDao;
	
	@Autowired
	private void setElementsprocessvalueDao(ElementsprocessvalueDao elementsprocessvalueDao){
		this.elementsprocessvalueDao = elementsprocessvalueDao;
		setBaseDao(elementsprocessvalueDao);
	}
}
