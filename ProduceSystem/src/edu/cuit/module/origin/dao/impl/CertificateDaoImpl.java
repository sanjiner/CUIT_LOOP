package edu.cuit.module.origin.dao.impl;

import org.springframework.stereotype.Repository;

import edu.cuit.common.dao.impl.BaseDaoImpl;
import edu.cuit.module.origin.dao.CertificateDao;
import edu.cuit.module.origin.entity.Certificate;

@Repository
public class CertificateDaoImpl extends BaseDaoImpl<Certificate, String> implements CertificateDao {

}
