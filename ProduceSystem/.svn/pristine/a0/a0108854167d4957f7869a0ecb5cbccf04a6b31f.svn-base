package edu.cuit.module.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.model.dao.GrowthelementDao;
import edu.cuit.module.model.entity.Growthelement;
import edu.cuit.module.model.service.GrowthelementService;

@Service
public class GrowthelementServiceImpl extends
		BaseServiceImpl<Growthelement, String> implements GrowthelementService {
	private GrowthelementDao growthelementDao;
	
	@Autowired
	public void setGrowthelementDao(GrowthelementDao growthelementDao){
		this.growthelementDao = growthelementDao;
		setBaseDao(growthelementDao);
	}
}
