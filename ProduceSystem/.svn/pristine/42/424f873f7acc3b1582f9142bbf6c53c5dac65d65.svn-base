package edu.cuit.module.infom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.infom.dao.CheckcenternewsreportDao;
import edu.cuit.module.infom.entity.Checkcenternewsreport;
import edu.cuit.module.infom.service.CheckcenternewsreportService;

@Service
public class CheckcenternewsreportServiceImpl extends
		BaseServiceImpl<Checkcenternewsreport, String> implements CheckcenternewsreportService {
	private CheckcenternewsreportDao checkcenternewsreportDao;
	@Autowired
	private void setCheckcenternewsreportDao(CheckcenternewsreportDao checkcenternewsreportDao){
		this.checkcenternewsreportDao = checkcenternewsreportDao;
		setBaseDao(checkcenternewsreportDao);
	}
}
