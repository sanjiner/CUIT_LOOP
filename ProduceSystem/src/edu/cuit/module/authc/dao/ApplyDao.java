package edu.cuit.module.authc.dao;

import java.util.List;
import java.util.Map;

import edu.cuit.common.dao.BaseDao;
import edu.cuit.module.auchc.entity.Apply;

public interface ApplyDao extends BaseDao<Apply, String> {
	public List<Map<String, String>> select(final String hql);
}
