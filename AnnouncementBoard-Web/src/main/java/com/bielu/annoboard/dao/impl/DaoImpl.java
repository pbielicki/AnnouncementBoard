package com.bielu.annoboard.dao.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bielu.annoboard.dao.Dao;
import com.bielu.annoboard.dao.DaoIntegrityException;
import com.bielu.annoboard.dao.DaoUtil;

public abstract class DaoImpl<T> extends HibernateDaoSupport implements Dao<T> {

	private static final long serialVersionUID = 7680240287088376694L;

	private static final Logger LOG = Logger.getLogger(DaoImpl.class);

	protected final String getPersistentClassName() {
		return getPersistentClass().getName();
	}

	protected abstract Class<T> getPersistentClass();

	public T findById(long id) {
		return getPersistentClass().cast(getHibernateTemplate().get(getPersistentClass(), Long.valueOf(id)));
	}

	@SuppressWarnings("unchecked")
	public List<T> loadAll() {
		return getHibernateTemplate().find("from " + getPersistentClassName());
	}

	@Transactional(readOnly = false)
	public void saveOrUpdate(T object) throws DaoIntegrityException {
		try {
			getHibernateTemplate().saveOrUpdate(object);
		} catch (DataIntegrityViolationException e) {
			LOG.debug("Could not store object [" + object.toString() + "]", e);
			throw DaoUtil.translateException(e);
		}
	}

	public void deleteAll() {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(String.format("delete from %s", getPersistentClassName())).executeUpdate();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	protected List freeTextSearch(final String stringQuery, final String... fields) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				FullTextSession fullTextSession = Search.createFullTextSession(session);
				MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer());
				Query query = null;
				try {
					query = parser.parse(stringQuery);
				} catch (ParseException e) {
					LOG.debug("Could not parse given Lucene query [" + stringQuery + "]");
					return Collections.EMPTY_LIST;
				}
				org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, getPersistentClass());

				return hibQuery.list();
			}
		});
	}
	
	public void purgeLuceneIndex() {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				FullTextSession fullTextSession = Search.createFullTextSession(session);
				fullTextSession.purgeAll(getPersistentClass());
				fullTextSession.getSearchFactory().optimize(getPersistentClass());

				return null;
			}
		});
	}
}