package com.smp.app.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;

public interface GenericDao<Entity, KeyType extends Serializable> {

    KeyType add(Entity object) throws HibernateException;

    void update(Entity object) throws HibernateException;

    void delete(Entity object) throws HibernateException;

    Entity read(KeyType id);


    List<Entity> find(Map<String, Object> conditionsMap);

    List<Entity> find(Map<String, Object> conditionsMap, Map<String, String> orderMap, int maxResult, int offSet);

    void load(Entity object, KeyType id);

    void flush();

    void saveOrUpdate(Entity object) throws HibernateException;
}
