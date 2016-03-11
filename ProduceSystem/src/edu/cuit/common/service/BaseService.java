package edu.cuit.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import edu.cuit.common.util.Page;

public interface BaseService <T extends Serializable, PK extends Serializable> {
	// 根据主键获取实体。如果没有相应的实体，返回 null。
		public T get(PK id);

		// 根据主键获取实体。如果没有相应的实体抛出异常。
		public T load(PK id);

		// 获取全部实体。
		public List<T> loadAll();

		// 更新实体
		public void update(T entity);

		// 存储实体到数据库
		public Serializable save(T entity);

		// 存储或保存实体到数据库
		public void saveOrUpdate(T entity);

		// 从数据库删除实体
		public void delete(T entity);

		// 根据主键删除指定实体
		public void deleteByKey(PK id);

		// 删除集合中的全部实体
		public void deleteAll(Collection<T> entities);

		// 获取分页list集合
		public List<?> getListForPage(final String hql, final int pageNo,
				final int pageContSize);

		// 获取分页对象
		public Page getPage(final String hql, final int pageNo,
				final int pageCountSize);

		// 使用HQL语句直接增加、更新、删除实体
		public int bulkUpdate(String queryString);

		// 使用带参数的HQL语句增加、更新、删除实体
		public int bulkUpdate(String queryString, Object... values);

		/**
		 * 查询hql
		 * @param queryString hql 语句
		 * @param values ？对应的参数值
		 * @return list集合，没找到size为0
		 */
		public List<?> find(String queryString, Object... values);

		// 使用带命名的参数的HQL语句检索数据
		public List<?> findByNamedParam(String queryString, String[] paramNames,
				Object[] values);

		// 清空session
		public void clear();

		// 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
		public void flush();
}
