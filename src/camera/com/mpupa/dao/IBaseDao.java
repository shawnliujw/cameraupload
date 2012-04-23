package com.mpupa.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Interface <code>IBaseDAO</code> is the root of the DAO hierarchy.
 * 
 * @createDate 2011-5-26
 * @author Liu Jianwei
 */
public interface IBaseDao<T> {

	/**
	 * Gets all objects of a particular type. This is the same as lookup up all rows in a table
	 *
	 * @return
	 *
	 * @author Liu Jianwei
	 */
	List<T> getObjects();

	/**
	 * Gets an object based on identifier. if nothing is found, it will throw a
	 * DAOException exception.
	 * 
	 * @param id
	 *            the identifier (primary key) of the class
	 * @return return the object of T type if can be found, return null if can
	 *         not be found.
	 *
	 * @author Liu Jianwei
	 */
	T getObject(Serializable id);

	/**
	 * Inserts an object to DB.
	 * 
	 * @param obj
	 *            the object to insert.
	 * @return the id of object.
	 *
	 * @author Liu Jianwei
	 */
	Serializable createObject(T obj);

	/**
	 * Updates an object.
	 * 
	 * @param obj
	 *            the object to update.
	 * @author Liu Jianwei
	 * 
	 */
	void updateObject(T obj);

	/**
	 * Saves or updates an object.
	 * 
	 * @param obj
	 *            the object to save or update.
	 * @author Liu Jianwei
	 */
	void saveObject(T obj);

	/**
	 * Merges an object.
	 * 
	 * @param entity
	 *            the object to merge.
	 * @author Liu Jianwei
	 */
	void merge(T entity);

	/**
	 * Deletes an object by object.
	 * 
	 * @param obj
	 *            the object to delete.
	 * @author Liu Jianwei
	 */
	void deleteObject(T obj);

	/**
	 * Deletes an object by id.
	 * 
	 * @param id
	 *            the id.
	 * @author Liu Jianwei
	 */
	void deleteObject(Serializable id);

	/**
	 * Deletes objects.
	 * 
	 * @param objs
	 *            the objects to delete.
	 * @author Liu Jianwei
	 */
	void deleteObjects(Collection<T> objs);

	/**
	 * Get the total count according the hql clause.
	 *
	 * @param hql
	 * @return
	 * @author Liu Jianwei
	 */
	Integer getTotalCount(String hql);
	
	/**
	 * Queries objects according to hql clause.
	 * 
	 * @param hql
	 *            the hql clause.
	 * @return the list of queried objects.
	 * @author Liu Jianwei
	 */
	@SuppressWarnings(value = { "unchecked" })
	List find(String hql);

	/**
	 * Queries objects according to hql clause and a parameter
	 * 
	 * @param hql
	 *            The hql clause
	 * @param para
	 *            the object parameter.
	 * @return the list of queried objects.
	 * @author Liu Jianwei
	 */
	//@SuppressWarnings(value = { "unchecked" })
	//List find(String hql, Object para);

	/**
	 * Queries objects according to hql clause and parameters.
	 * 
	 * @param hql
	 *            the hql clause.
	 * @param paras
	 *            the parameters for the hql clause.
	 * @return the list of queried objects.
	 * @author Liu Jianwei
	 */
	//@SuppressWarnings(value = { "unchecked" })
	//List find(String hql, Object[] paras);

	/**
	 * Queries objects according to hql clause and parameters.
	 * 
	 * @param hql
	 *            The hql clause
	 * @param paraNames
	 *            the parameter names for the hql clause
	 * @param paraValues
	 *            the parameter values for the hql clause
	 * @return the list of queried objects.
	 * @author Liu Jianwei
	 */
	@SuppressWarnings(value = { "unchecked" })
	List find(String hql, String[] paraNames, Object[] paraValues);

	/**
	 * Queries objects according to hql clause and a parameter.
	 * 
	 * @param hql
	 *            the hql clause.
	 * @param paraName
	 *            the parameter name for the hql clause.
	 * @param paraValue
	 *            the parameter value for the hql clause.
	 * @return the list of queried objects.
	 * @author Liu Jianwei
	 */
	List<T> find(String hql, String paraName, Object paraValue);

	/**
	 * Queries the object list by properties value.
	 * 
	 * @param propertyName
	 *            the property name.
	 * @param value
	 *            the value of property.
	 * @return the list of queried objects.
	 * @author Liu Jianwei
	 */
	List<T> findByPropertyLike(String propertyName, Object value);
	/**
	 * Queries the object list by properties value.
	 * 
	 * @param propertyName
	 *            the property name.
	 * @param value
	 *            the value of property.
	 * @return the list of queried objects.
	 * @author Liu Jianwei
	 */
	List<T> findByPropertyEq(String propertyName, Object value);

	/**
	 * Queries the unique Object by the property value.
	 * 
	 * @param propertyName
	 *            the property name.
	 * @param value
	 *            the value of property.
	 * @return a populated object.
	 * @author Liu Jianwei
	 */
	T findUniqueByProperty(String propertyName, Object value);

	/**
	 * Queries the unique Object by the parameters value array.
	 * 
	 * @param hql
	 *            the hql clause.
	 * @param values
	 *            the parameters value array.
	 * @return a populated object.
	 * @author Liu Jianwei
	 */
	Object findUnique(String hql, Object... values);

	/**
	 * Creates the Query object by the query function and the parameters value
	 * array.
	 * 
	 * @param queryString
	 *            the query sql clause.
	 * @param values
	 *            the parameters value array.
	 * @return the Query object.
	 * @author Liu Jianwei
	 */
	Query createQuery(String queryString, Object... values);

	/**
	 * Gets the current session object.
	 * 
	 * @return session the current session object.
	 * @author Liu Jianwei
	 */
	Session getCurrentSession();

	/**
	 * Gets the connection object.
	 * 
	 * @return connection the connection object.
	 * @author Liu Jianwei
	 */
	Connection getConnection();

	/**
	 * Queries the object by hql clause and first row value and max row value.
	 * 
	 * @param hsql
	 *            the hql clause.
	 * @param firstRow
	 *            the first row value.
	 * @param maxRow
	 *            the max row value.
	 * @return the object list.
	 * @author Liu Jianwei
	 */
	@SuppressWarnings(value = { "unchecked" })
	List find(final String hql, final int firstRow, final int maxRow);

	/**
	 * Gets the object list for page.
	 * 
	 * @param sql
	 *            the query sql clause.
	 * @param rowStart
	 *            the begin index value.
	 * @param rowEnd
	 *            the end index value.
	 * @return the object list.
	 * @author Bao Jingbin
	 */
	List<T> getObjectListForPage(String sql, int rowStart, int rowEnd);

	/**
	 * Deletes objects by ids.
	 * 
	 * @param ids
	 *            the id array.
	 * @return the total count value of delete successful.
	 * @author Bao Jingbin
	 */
	int deleteObjectsByIds(Collection<Integer> ids);

	/**
	 * Executes the standard insert or update or delete SQL clause.
	 * 
	 * @param sql
	 *            the standard SQL string.
	 * @return the count number of insert or update.
	 * @author Bao Jingbin
	 */
	int executeInsertOrUpdateSql(String sql);

	/**
	 * Executes the standard select SQL clause.
	 * 
	 * @param sql
	 *            the standard SQL string.
	 * @return some column values of table.
	 * @author Liu Jianwei
	 */
	List<?> executeSelectSql(String sql);

	/**
	 * Executes the standard select SQL clause.
	 * 
	 * @param sql
	 *            the standard SQL string.
	 * @param falg
	 * @return if flag is true it will return the List<Object[]> object, false
	 *         return the List<Object> object.
	 * @author Liu Jianwei
	 */
	List<?> executeSelectSql(String sql, boolean falg);
}
