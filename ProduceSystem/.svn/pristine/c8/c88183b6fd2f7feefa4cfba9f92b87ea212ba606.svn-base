package edu.cuit.module.authc.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import edu.cuit.common.dao.impl.BaseDaoImpl;
import edu.cuit.common.util.DateFormat;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.authc.dao.ApplyDao;

@Repository
public class ApplyDaoImpl extends BaseDaoImpl<Apply, String> implements ApplyDao {
	
	public List<Map<String, String>> select(final String hql) {

		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> doInHibernate(Session session)
					throws HibernateException {
				List<?> list = session.createSQLQuery(hql).list();
				List<Map<String, String>> result = new ArrayList<Map<String,String>>();
				for (Object object : list) {
					// applyBh,productName,applyTime,isWithDraw,HandleResult,HandleDescription,declareStatus
					//applyBh,productName,applyTime,isWithDraw,HandleResult,HandleDescription
					Object[] objs = (Object[])object;
					Map<String, String> map = new HashMap<String, String>();
					map.put("applyBh", objs[0]+"");
					map.put("productName", objs[1]+"");
					map.put("applyTime",DateFormat.getStrTime((Date)objs[2],1));
					map.put("isWithDraw",objs[3]+"");
					map.put("handleResult", objs[4]+"");
					map.put("handleDescription", objs[5]+"");
					map.put("declareStatus", objs[6]+"");
					result.add(map);
				}
				return result;
			}
		});
	}
}
