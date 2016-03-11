package edu.cuit.common.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cuit.common.dao.BaseDao;
import edu.cuit.common.service.BaseService;
import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;

public class BaseServiceImpl<T extends Serializable, PK extends Serializable> implements BaseService<T, PK> {
	private BaseDao<T, PK> baseDao;
	/*日志*/
	protected Logger log = LoggerFactory.getLogger(getClass());
	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 根据主键获取实体。如果没有相应的实体，返回 null。
	 * 
	 * @param id
	 * @return
	 */
	public T get(PK id) {
		return baseDao.get(id);
	}

	/**
	 * 根据主键获取实体。如果没有相应的实体，抛出异常。
	 * 
	 * @param id
	 * @return
	 */
	public T load(PK id) {
		return baseDao.load(id);
	}

	/** 
	 * 获取全部实体。
	 * 
	 * @return
	 */
	public List<T> loadAll() {
		return baseDao.loadAll();
	}

	public void update(T entity) {
		baseDao.update(entity);
	}

	/**
	 * 插入记录 返回 主键
	 * 
	 * @param entity
	 */
	public Serializable save(T entity) {
		return baseDao.save(entity);
	}

	/**
	 * 通过判断主键是否存在 保存或更新
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(T entity) {
		baseDao.saveOrUpdate(entity);
	}

	/**
	 * 删除相应实体记录
	 * 
	 * @param entity
	 */
	public void delete(T entity) {
		baseDao.delete(entity);
	}

	/**
	 * 通过实体主键来删除记录
	 * 
	 * @param id
	 */
	public void deleteByKey(PK id) {
		baseDao.deleteByKey(id);
	}

	/**
	 * 删除指定的对象集合
	 * 
	 * @param entities
	 */
	public void deleteAll(Collection<T> entities) {
		baseDao.deleteAll(entities);
	}

	/**
	 * 获取分页list集合
	 * 
	 * @param hql
	 * @param pageNo
	 * @param pageCountSize
	 * @return
	 */
	public List<?> getListForPage(String hql, int pageNo, int pageContSize) {
		return baseDao.getListForPage(hql, pageNo, pageContSize);
	}

	/**
	 * 获取分页
	 * 
	 * @param hql
	 * @param pageNo
	 * @param pageCountSize
	 * @return
	 */
	public Page getPage(String hql, int pageNo, int pageCountSize) {
		return baseDao.getPage(hql, pageNo, pageCountSize);
	}

	/**
	 * 操作数据库表
	 * 
	 * @param queryString
	 * @return 返回影响行数
	 */
	public int bulkUpdate(String queryString) {
		return baseDao.bulkUpdate(queryString);
	}

	/**
	 * 操作数据库表 可携带多个参数 替换？
	 * 
	 * @param queryString
	 * @return 返回影响行数
	 */
	public int bulkUpdate(String queryString, Object... values) {
		return baseDao.bulkUpdate(queryString, values);
	}

	/**
	 * 执行hql语句查找
	 * 
	 * @param queryString
	 * @param values
	 * @return list<T>集合
	 */
	public List<?> find(String queryString) {
		return baseDao.find(queryString);
	}

	/**
	 * 执行hql语句查找 参数替代？
	 * 
	 * @param queryString
	 * @param values
	 * @return list<T>集合
	 */
	public List<?> find(String queryString, Object... values) {
		return baseDao.find(queryString, values);
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
	public List<?> findByNamedParam(String queryString, String[] paramNames,
			Object[] values) {

		return baseDao.findByNamedParam(queryString, paramNames, values);
	}

	/**
	 * 清除hibernate session 缓存
	 */
	public void clear() {
		baseDao.clear();
	}

	/**
	 * 将hibernate session 关联的对象 保存到数据库
	 */
	public void flush() {
		baseDao.flush();
	}

}
