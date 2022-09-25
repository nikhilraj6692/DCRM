package com.smp.app.dao.impl;

import com.smp.app.dao.GenericDao;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public abstract class GenericDaoImpl<Entity, KeyType extends Serializable> implements GenericDao<Entity, KeyType> {

    @Autowired
    protected SessionFactory sessionFactory;
    private Class<Entity> type;

    public GenericDaoImpl(Class<Entity> type) {
        this.type = type;
    }

    public GenericDaoImpl() {
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    // @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public KeyType add(Entity object) {
        return (KeyType) this.getSession().save(object);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void update(Entity object) {
        this.getSession().update(object);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void delete(Entity object) {
        this.getSession().delete(object);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public Entity read(KeyType id) {
        return this.getSession().get(this.type, id);
    }

    /*
     * Created by:Sanath
     * Changed by:Amit(31 Oct 2011)
     * finds based on conditionsMap - conditionsMap is a HashMap object
     * containing column name as key and value to be searched as value
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<Entity> find(Map<String, Object> conditionsMap) {
        HashMap<String, String> orderMap = new HashMap<String, String>();
        return find(conditionsMap, orderMap, 1000, 0);
    }

    /*
     * Created by:Sanath
     * Changed by:Amit(31 Oct 2011)
     * finds based on conditionsMap and sorts based on orderMap returning
     * maxResult from the offSet - conditionsMap is a HashMap object containing
     * column name as key and value to be searched as value orderMap is a
     * HashMap object with column name to be ordered as key and either "ASC" or
     * "DESC" as value
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<Entity> find(Map<String, Object> conditionsMap, Map<String, String> orderMap, int maxResult, int offSet) {
        Criteria crit = this.getSession().createCriteria(type);

        Iterator<String> conditionsIt = conditionsMap.keySet().iterator();
        while (conditionsIt.hasNext()) {
            String key = conditionsIt.next();
            Object val = conditionsMap.get(key);

            if (val instanceof List<?>) {
                List<Object> tcVal = (List<Object>) val;
                if (tcVal.size() > 1) {
                    String op = (String) tcVal.get(1);
                    if (op.equalsIgnoreCase("eq")) {
                        crit.add(Restrictions.eq(key, tcVal.get(0)));
                    } else if (op.equalsIgnoreCase("ne")) {
                        crit.add(Restrictions.ne(key, tcVal.get(0)));
                    }
                }
            } else if (val instanceof Object) {
                if (val instanceof String) {
                    crit.add(Restrictions.eq(key, val.toString()).ignoreCase());
                } else {
                    crit.add(Restrictions.eq(key, val));
                }
            }
        }

        Iterator<String> orderIt = orderMap.keySet().iterator();
        while (orderIt.hasNext()) {
            String key = orderIt.next();
            String val = orderMap.get(key);

            if (val == "ASC") {
                crit.addOrder(Property.forName(key).asc());
            } else if (val == "DESC") {
                crit.addOrder(Property.forName(key).desc());
            }
        }

        crit.setFirstResult(offSet);
        crit.setMaxResults(maxResult);
        crit.setCacheable(true);
        List<Entity> list = crit.list();
        return list;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.castlerockresearch.docxchange.commons.storage.IBackEnd#load(java.
     * lang.Object)
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void load(Entity object, KeyType id) {
        Session session = this.getSession();
        if (!session.contains(object)) {
            session.load(object, id);
        }
    }

//	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
//	public void load(Entity object, KeyType id) {
//		Session session = this.getSession();
//	    	object = null;
//			session.load(object, id);
//	}


    /*
     * (non-Javadoc)
     *
     * @see com.castlerockresearch.docxchange.commons.storage.IBackEnd#flush()
     */
    public void flush() {
        this.getSession().flush();

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.castlerockresearch.docxchange.commons.storage.IBackEnd#saveOrUpdate
     * (java.lang.Object)
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void saveOrUpdate(Entity object) {
        this.getSession().saveOrUpdate(object);
    }

    public void refresh(Entity object) {
        this.getSession().refresh(object);
    }
}
