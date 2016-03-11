package edu.cuit.module.origin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.module.origin.dao.impl.CertificateDaoImpl;
import edu.cuit.module.origin.entity.Certificate;
import edu.cuit.module.origin.service.CertificateService;

@Service
public class CertificateServiceImpl extends BaseServiceImpl<Certificate, String> implements CertificateService{
	private CertificateDaoImpl certificateDao;
	@Autowired
	private void setCertificateDao (CertificateDaoImpl certificateDao){
		this.certificateDao = certificateDao;
		setBaseDao(certificateDao);
	}
}
