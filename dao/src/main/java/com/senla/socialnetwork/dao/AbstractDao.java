package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.AEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Slf4j
public abstract class AbstractDao<T extends AEntity, PK extends Serializable> implements GenericDao<T, PK> {
    private Class<T> type;
    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractDao() {
    }

    @Override
    public void setType(final Class<T> type) {
        this.type = type;
    }

    @Override
    public T saveRecord(T entity) {
        log.debug("[saveRecord]");
        log.trace("[entity: {}]", entity);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T findById(PK id) {
        log.debug("[findById]");
        log.trace("[type: {}, id: {}]", type, id);
        return entityManager.find(type, id);
    }

    @Override
    public List<T> getAllRecords() {
        log.debug("[getAllRecords]");
        log.trace("[type: {}]", type);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void updateRecord(T entity) {
        log.debug("[updateRecord]");
        log.trace("[entity: {}]", entity);
        entityManager.merge(entity);
    }

    @Override
    public void updateAllRecords(List<T> entities) {
        log.debug("[updateAllRecords]");
        log.trace("[entities: {}]", entities);
        for (T entity : entities) {
            entityManager.merge(entity);
        }
    }

    @Override
    public void deleteRecord(PK id) {
        log.debug("[deleteRecord]");
        log.trace("[id: {}]", id);
        entityManager.remove(id);
    }

}
