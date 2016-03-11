package edu.cuit.common.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import edu.cuit.common.dao.BaseDao;
import edu.cuit.common.util.Page;

public class BaseDaoImpl<T extends Serializable, PK extends Serializable>
		extends HibernateDaoSupport implements BaseDao<T, PK> {
	/**
	 * 通过注解的方式 添加sessionFactory
	 * 
	 * @param sessionFactory
	 */
	@Resource(name = "sessionFactory")
	public void setHibernateSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private Class<T> entityClass;

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * 通过反射 获取 泛型的class
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 插入记录 返回 主键 hiberante 4.x 以后会根据对象主键选择更新还是插入
	 * 
	 * @param entity
	 */
	@Override
	public Serializable save(T entity) {
		return getHibernateTemplate().save(entity);
	}

	/**
	 * 更新相应是实体记录
	 * 
	 * @param entity
	 */
	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);

	}

	/**
	 * 删除相应实体记录
	 * 
	 * @param entity
	 */
	@Override
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);

	}

	/**
	 * 根据主键获取实体。如果没有相应的实体，返回 null。
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public T get(PK id) {
		return getHibernateTemplate().get(getEntityClass(), id);
	}

	/**
	 * 根据主键获取实体。如果没有相应的实体，抛出异常。
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public T load(PK id) {
		return getHibernateTemplate().load(getEntityClass(), id);
	}

	/**
	 * 获取全部实体。
	 * 
	 * @return
	 */
	@Override
	public List<T> loadAll() {

		return getHibernateTemplate().loadAll(getEntityClass());
	}

	/**
	 * 通过判断主键是否存在 保存或更新
	 * 
	 * @param entity
	 */
	@Override
	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);

	}

	/**
	 * 获取分页
	 * 
	 * @param hql
	 * @param pageNo
	 * @param pageCountSize
	 * @return
	 */
	@Override
	public Page getPage(final String hql, final int pageNo,
			final int pageCountSize) {
		return getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback<Page>() {

					public Page doInHibernate(Session session)
							throws HibernateException {
						Query queryCount = session
								.createQuery("SELECT COUNT(*) " + hql);
						Integer totalSize = (new Integer(queryCount
								.uniqueResult().toString())).intValue();

						Query query = session.createQuery(hql);
						int pageNum ;
						if (pageCountSize * pageNo > totalSize) {
							pageNum = totalSize/pageCountSize;
							query.setFirstResult(pageNum * pageCountSize);
						}else{
							pageNum = pageNo;
							query.setFirstResult(pageNo * pageCountSize);
						}
						query.setMaxResults(pageCountSize);
						List<?> list = query.list();
						return new Page(pageNum, pageCountSize, totalSize, list);
					}

				});
	}

	/**
	 * 获取分页list集合
	 * 
	 * @param hql
	 * @param pageNo
	 * @param pageCountSize
	 * @return
	 */
	@Override
	public List<?> getListForPage(final String hql, final int pageNo,
			final int pageCountSize) {

		return getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback<List<?>>() {
					@Override
					public List<?> doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createQuery(hql);
						query.setFirstResult(pageNo * pageCountSize);
						query.setMaxResults(pageCountSize);
						return query.list();
					}
				});
	}

	/**
	 * 通过实体主键来删除记录
	 * 
	 * @param id
	 */
	@Override
	public void deleteByKey(PK id) {
		delete(get(id));
	}

	/**
	 * 删除指定的对象集合
	 * 
	 * @param entities
	 */
	@Override
	public void deleteAll(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * 操作数据库表
	 * 
	 * @param queryString
	 * @return 返回影响行数
	 */
	@Override
	public int bulkUpdate(String queryString) {

		return getHibernateTemplate().bulkUpdate(queryString);
	}

	/**
	 * 操作数据库表 可携带多个参数 替换？
	 * 
	 * @param queryString
	 * @return 返回影响行数
	 */
	@Override
	public int bulkUpdate(String queryString, Object... values) {
		return getHibernateTemplate().bulkUpdate(queryString, values);
	}

	/**
	 * 执行hql语句查找
	 * 
	 * @param queryString
	 * @param values
	 * @return list<T>集合
	 */
	@Override
	public List<?> find(String queryString, Object... values) {
		return getHibernateTemplate().find(queryString, values);
	}

	/**
	 * 执行hql带参数hql 的语句查找
	 * 
	 * @param queryString
	 * @param paramNames
	 *            参数名称
	 * @param values
	 *            参数值
	 * @return list<T>集合
	 */
	@Override
	public List<?> findByNamedParam(String queryString, String[] paramNames,
			Object[] values) {
		return getHibernateTemplate().findByNamedParam(queryString, paramNames,
				values);
	}

	/**
	 * 清除hibernate session 缓存
	 */
	@Override
	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 将hibernate session 关联的对象 保存到数据库
	 */
	@Override
	public void flush() {
		getHibernateTemplate().flush();

	}
}
