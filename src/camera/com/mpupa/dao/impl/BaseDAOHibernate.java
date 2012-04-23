package com.mpupa.dao.impl;



import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import com.mpupa.dao.IBaseDao;

/**
 * This class implement <code>BaseDAOImpl</code> is the root of the DAO implement hierarchy.
 * 
 * @version @version 1.0, Jun 1, 2010 3:09:00 PM
 * @author  Jianwei
 */
public class BaseDAOHibernate<T> extends HibernateDaoSupport implements IBaseDao<T> {

	private final Logger log = Logger.getLogger(BaseDAOHibernate.class);
	
	protected Class<T> clz;

	/*
	 * Construct method
	 */
	@SuppressWarnings (value={"unchecked"})
	public BaseDAOHibernate() {
		log.debug("start getting class type of template class.");
		clz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		log.debug("end getting class type of template class:" + clz);
	}

	@SuppressWarnings("unused")
	private String getErrorMessage(String message, DataAccessException dataAccessException) {
		StringBuilder sb = new StringBuilder(message);
		sb.append(dataAccessException.getMessage());
		sb.append("\n");
		sb.append(dataAccessException.getRootCause().toString());
		return sb.toString();
	}
	
	private String getErrorMessage(String message, Exception exception) {
		StringBuilder sb = new StringBuilder(message);
		sb.append(exception.getMessage());
		return sb.toString();
	}
	
	
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#getObjects()
	 * @author Liu Jianwei
	 */
	public List<T> getObjects(){
		try {
			return this.getHibernateTemplate().loadAll(clz);
		} catch (Exception e) {
			String message =  e.getMessage();
			log.error(message, e);
		}
		
		return null;
	}

	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#getObject(java.io.Serializable)
	 * @author Liu Jianwei
	 */
	
	public T getObject(Serializable id) {
		String methodName = "Error in getObject(Serializable id) method. ";
		try {
			Assert.notNull(id, methodName + " the value of parameter 'id' should not be null.");
			T obj = (T) this.getHibernateTemplate().get(clz, id);
			if (obj == null) {
				log.warn("Warn in getObject(Serializable id) method. " + " Object for class:" + clz.getSimpleName() + " with id:" + id+ " was not found.");
			}
			return obj;
		} catch (IllegalArgumentException e) {
			String message =  e.getMessage();
			log.error(message, e);
		} catch (Exception e) {
			String message =  e.getMessage();
			log.error(message, e);
		}
		return null;
	}

	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#createObject(java.lang.Object)
	 * @author Liu Jianwei
	 */
	public Serializable createObject(T obj) {
		String methodName = "Error in createObject(T obj) method. ";
		try {
			Assert.notNull(obj, methodName + " the value of parameter 'obj' should not be null.");
			return this.getHibernateTemplate().save(obj);
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}

	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#saveObject(java.lang.Object)
     * @author Liu Jianwei
	 */
	public void saveObject(T obj) {
		String methodName = "Error in saveObject(T obj) method. ";
		try {
			Assert.notNull(obj, methodName + " the value of parameter 'obj' should not be null.");
			this.getHibernateTemplate().saveOrUpdate(obj);
			System.out.println("===========");
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
	}

	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#merge(java.lang.Object)
     * @author Liu Jianwei
	 */
	public void merge(T entity) {
		String methodName = "Error in merge(T entity) method. ";
		try {
			Assert.notNull(entity, methodName + " the value of parameter 'entity' should not be null.");
			getSession().merge(entity);
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
	}
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#updateObject(java.lang.Object)
	 * @author Liu Jianwei
	 */
	public void updateObject(T obj) {
		String methodName = "Error in updateObject(T obj) method. ";
		try {
			Assert.notNull(obj, methodName + " the value of parameter 'obj' should not be null.");
			this.getHibernateTemplate().update(obj);
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
	}

	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#deleteObject(java.lang.Object)
	 * @author Liu Jianwei
	 */
	public void deleteObject(T obj) {
		String methodName = "Error in deleteObject(T obj) method. ";
		try {
			Assert.notNull(obj, methodName + " the value of parameter 'obj' should not be null.");
			this.getHibernateTemplate().delete(obj);
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
	}

	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#deleteObject(java.io.Serializable)
	 * @author Liu Jianwei
	 */
	public void deleteObject(Serializable id) {
		String methodName = "Error in deleteObject(Serializable id) method. ";
		try {
			Assert.notNull(id, methodName + " the value of parameter 'id' should not be null.");
			T obj = (T) this.getHibernateTemplate().get(clz, id);
			if (obj == null) {
				log.info(" Warn :: In deleteObject(Serializable id) method. Object for class:"+ clz.getSimpleName() + " with id:" + id + " was not found.");
			} else {
				this.getHibernateTemplate().delete(obj);
			}
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
	}
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#deleteObjects(java.util.Collection)
	 * @author Liu Jianwei
	 */
	public void deleteObjects(Collection<T> objs) {
		String methodName = "Error in deleteObjects(Collection<T> objs) method. ";
		try {
			Assert.notNull(objs, methodName + " the value of parameter 'objs' should not be null.");
			Assert.notEmpty(objs, methodName + " the value of parameter 'objs' should not be empty.");
			this.getHibernateTemplate().deleteAll(objs);
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
	}

	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#find(java.lang.String)
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("unchecked")
	public List find(String hql) {
		String methodName = "Error in find(String hql) method. ";
		try {
			Assert.hasText(hql, methodName + " the value of parameter 'hql' should not be null and it also should be not blank or empty character.");
			//int 
			//return createQuery(hql).setFirstResult(1).setMaxResults(9).list();
			return this.getHibernateTemplate().find(hql);
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}

    /*
     * @see com.mpupa.barratts.dao.IBaseDao#find(java.lang.String, java.lang.Object)
	 * @author Liu Jianwei
     */
	@SuppressWarnings("unchecked")
	public List find(String hql, Object para) {
		String methodName = "Error in find(String hql, Object para) method. ";
		try {
			Assert.hasText(hql, methodName + " the value of parameter 'hql' should not be null and it also should be not blank or empty character.");
			Assert.notNull(para, methodName + " the value of parameter 'para' should not be null.");
			return getHibernateTemplate().find(hql, para);
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message);
		}
		return null;
	}

	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#find(java.lang.String, java.lang.Object[])
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("unchecked")
	public List find(String hql, Object[] paras) {
		String methodName = "Error in find(String hql, Object para) method. ";
		try {
			Assert.hasText(hql, methodName + " the value of parameter 'hql' should not be null and it also should be not blank or empty character.");
			Assert.notNull(paras, methodName + " the value of parameter 'paras' should not be null.");
			Assert.notEmpty(paras, methodName + " the value of parameter 'paras' should not be empty.");
			return this.getHibernateTemplate().find(hql, paras);
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}

	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#find(java.lang.String, java.lang.String[], java.lang.Object[])
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("unchecked")
	public List find(String hql, String[] paraNames, Object[] paraValues)  {
		String methodName = "Error in find(String hql, String[] paraNames, Object[] paraValues) method. ";
		try {
			Assert.hasText(hql, methodName + " the value of parameter 'hql' should not be null and it also should be not blank or empty character.");
			Assert.notNull(paraNames, methodName + " the value of parameter 'paraNames' should not be null.");
			Assert.notEmpty(paraNames, methodName + " the value of parameter 'paraNames' should not be empty.");
			Assert.notNull(paraValues, methodName + " the value of parameter 'paraValues' should not be null.");
			Assert.notEmpty(paraValues, methodName + " the value of parameter 'paraValues' should not be empty.");
			return this.getHibernateTemplate().findByNamedParam(hql, paraNames, paraValues);
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}

	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#find(java.lang.String, java.lang.String, java.lang.Object)
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("unchecked")
	public List find(String hql, String paraName, Object paraValue) {
		String methodName = "Error in find(String hql, String paraName, Object paraValue) method. ";
		try {
			Assert.hasText(hql, methodName + " the value of parameter 'hql' should not be null and it also should be not blank or empty character.");
			Assert.hasText(paraName, methodName + " the value of parameter 'paraName' should not be null and it also should be not blank or empty character.");
			Assert.notNull(paraValue, methodName + " the value of parameter 'paraValue' should not be null.");
			return this.getHibernateTemplate().findByNamedParam(hql, paraName, paraValue);
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#find(java.lang.String, int, int)
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("unchecked")
	public List find(final String hql, final int firstRow, final int maxRow) {
		String methodName = "Error in find(final String hsql, final int firstRow, final int maxRow) method. ";
		try {
			Assert.hasText(hql, methodName + " the value of parameter 'hql' should not be null and it also should be not blank or empty character.");
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session s) throws HibernateException,
						SQLException {
					Query query = s.createQuery(hql);
					query.setFirstResult(firstRow);
					query.setMaxResults(maxRow);
					List list = query.list();
					return list;
				}
			});
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#findUnique(java.lang.String, java.lang.Object[])
	 * @author Liu Jianwei
	 */
	public Object findUnique(String hql, Object... values) {
		String methodName = "Error in findUnique(String hql, Object... values)  method. ";
		try {
			Assert.hasText(hql, methodName + " the value of parameter 'hql' should not be null and it also should be not blank or empty character.");
			return createQuery(hql, values).setMaxResults(1).uniqueResult();
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#createQuery(java.lang.String, java.lang.Object[])
	 * @author Liu Jianwei
	 */
	public Query createQuery(String hsql, Object... values)  {
		String methodName = "Error in createQuery(String queryString, Object... values)  method. ";
		try {
			Assert.hasText(hsql, methodName + " the value of parameter 'hsql' should not be null and it also should be not blank or empty character.");
			Query queryObject = getSession().createQuery(hsql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					queryObject.setParameter(i, values[i]);
				}
			}
			return queryObject;
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}

	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#findByPropertyEq(java.lang.String, java.lang.Object)
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPropertyEq(String propertyName, Object value) {
		String methodName = "Error in createQuery(String queryString, Object... values)  method. ";
		try {
			Assert.hasText(propertyName, methodName + " the value of parameter 'propertyName' should not be null and it also should be not blank or empty character.");
			Assert.notNull(value, methodName + " the value of parameter 'value' should not be null.");
			
			return createCriteria(Restrictions.eq(propertyName, value)).list();
		}  catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#findByPropertyLike(java.lang.String, java.lang.Object)
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPropertyLike(String propertyName, Object value) {
		String methodName = "Error in createQuery(String queryString, Object... values)  method. ";
		try {
			Assert.hasText(propertyName, methodName + " the value of parameter 'propertyName' should not be null and it also should be not blank or empty character.");
			Assert.notNull(value, methodName + " the value of parameter 'value' should not be null.");
			return createCriteria(Restrictions.like(propertyName, value)).list();
		}  catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#findUniqueByProperty(java.lang.String, java.lang.Object)
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByProperty(String propertyName, Object value) {
		String methodName = "Error in createQuery(String queryString, Object... values)  method. ";
		try {
			Assert.hasText(propertyName, methodName + " the value of parameter 'propertyName' should not be null and it also should be not blank or empty character.");
			Assert.notNull(value, methodName + " the value of parameter 'value' should not be null.");
			return (T) createCriteria(Restrictions.eq(propertyName, value)).setMaxResults(1).uniqueResult();
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}
	
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#getCurrentSession()
	 * @author Liu Jianwei
	 */
	public Session getCurrentSession() {
		String methodName = "Error in getCurrentSession() method. ";
		try {
			return this.getSession();
		} catch (Exception e) {
			String message = methodName + e.getMessage();
			log.error(message, e);
		}
		return null;
	}


	private Criteria createCriteria(Criterion... criterions) {
		String methodName = "Error in createCriteria(Criterion... criterions) method. ";
		try {
			Criteria criteria = getSession().createCriteria(clz);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			return criteria;
		} catch (Exception e) {
			String message = methodName + e.getMessage();
			log.error(message, e);
		}
		return null;

	}
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#getConnection()
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("deprecation")
	public Connection getConnection() {
		String methodName = "Error in getConnection() method. ";
		try {
			return getCurrentSession().connection();
		} catch (Exception e) {
			String message = methodName + e.getMessage();
			log.error(message, e);
		}
		return null;
	}

	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#getObjectListForPage(java.lang.String, int, int)
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("unchecked")
	public List<T> getObjectListForPage(String sql, int rowStart, int rowEnd) {
		String methodName = "Error in getObjectListForPage(String sql, int rowStart, int rowEnd) method. ";
		try {
			Assert.hasText(sql, methodName + " the value of parameter 'sql' must not be null and it also should be not blank or empty character.");
			return createQuery(sql).setFirstResult(rowStart).setMaxResults(rowEnd).list();
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;
	}
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#deleteObjectsByIds(java.util.Collection)
	 * @author Liu Jianwei
	 */
	public int deleteObjectsByIds(Collection<Integer> ids) {
		String methodName = "Error in deleteObjectsByIds(Collection<Integer> ids)  method. ";
		try {
			Assert.notNull(ids, methodName + " the value of parameter 'ids' should not be null.");
			Assert.notEmpty(ids, methodName + " the value of parameter 'ids' should not be empty.");
			StringBuilder sb = new StringBuilder();
			sb.append(" delete from " + clz.getSimpleName() + " where id in (:ids)");
			return this.createQuery(sb.toString()).setParameterList("ids", ids).executeUpdate();
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return 0;
	}

	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#executeInsertOrUpdateSql(java.lang.String)
	 * @author Liu Jianwei
	 */
	public int executeInsertOrUpdateSql(String sql) {
		String methodName = "Error in executeInsertOrUpdateSql(String sql)  method. ";
		try {
			Assert.hasText(sql, methodName + " the value of parameter 'sql' must not be null and it also should be not blank or empty character.");
			int count = this.getSession().createSQLQuery(sql).executeUpdate();
			return count;
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return 0;	
	}
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#executeSelectSql(java.lang.String)
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("unchecked")
	public List<?> executeSelectSql(String sql) {
		String methodName = "Error in executeSelectSql(String sql)  method. ";
		try {
			Assert.hasText(sql, methodName + " the value of parameter 'sql' must not be null and it also should be not blank or empty character.");
			SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
			List<Object> list = sqlQuery.list();
			List<Object> result = new ArrayList<Object>();
			if (list != null && !list.isEmpty()) {
				result = list;
			}
			return result;
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;	
	}
	
	/*
	 * @see com.mpupa.barratts.dao.IBaseDao#executeSelectSql(java.lang.String, boolean)
	 * @author Liu Jianwei
	 */
	@SuppressWarnings("unchecked")
	public List<?> executeSelectSql(String sql, boolean falg) {
		String methodName = "Error in executeSelectSql(String sql, boolean falg) method. ";
		try {
			Assert.hasText(sql, methodName + " the value of parameter 'sql' must not be null and it also should be not blank or empty character.");
			SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
			List<Object> list = sqlQuery.list();
			if (falg) {
				sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			} 
			return (list != null) ? list : new ArrayList<Object>();
		} catch (Exception e) {
			String message = this.getErrorMessage(methodName, e);
			log.error(message, e);
		}
		return null;	
	}

	public Integer getTotalCount(String hql) {
		// TODO Auto-generated method stub
		//List list = this.getHibernateTemplate().find(hql);
		//List list1 = this.createQuery(hql).list();
		return this.getHibernateTemplate().find(hql).size();
	}

	 /*private SessionFactory sessionFacotry;  
	    //注入一个bean, 默认(name = "sessionFactory"), 因此只写@Resource  
	    @Resource       
	    public void setSessionFacotry(SessionFactory sessionFacotry) {     
	                super.setSessionFactory(sessionFacotry);     
	    }  */

}
