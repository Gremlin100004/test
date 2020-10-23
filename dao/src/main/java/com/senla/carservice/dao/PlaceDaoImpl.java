package com.senla.carservice.dao;

import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Order_;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.Place_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Date;
import java.util.List;

@Repository
@Slf4j
public class PlaceDaoImpl extends AbstractDao<Place, Long> implements PlaceDao {

    public PlaceDaoImpl() {
        setType(Place.class);
    }

    @Override
    public List<Place> getFreePlaces(Date executeDate) {
        log.debug("[getFreePlaces]");
        log.trace("[executeDate: {}]", executeDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Place> criteriaQuery = criteriaBuilder.createQuery(Place.class);
        Root<Place> placeRoot = criteriaQuery.from(Place.class);
        criteriaQuery.select(placeRoot);
        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Order> subOrderRoot = subquery.from(Order.class);
        subOrderRoot.join(Order_.place);
        subquery.select(subOrderRoot.get(Order_.place).get(Place_.id));
        subquery.where(criteriaBuilder.greaterThanOrEqualTo(subOrderRoot.get(Order_.leadTime), executeDate));
        criteriaQuery.where(placeRoot.get(Place_.id).in(subquery).not());
        TypedQuery<Place> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Place findByNumber(int number) {
        log.debug("[findByNumber]");
        log.trace("[number: {}]", number);
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Place> criteriaQuery = criteriaBuilder.createQuery(Place.class);
            Root<Place> placeRoot = criteriaQuery.from(Place.class);
            criteriaQuery.select(placeRoot);
            criteriaQuery.where(criteriaBuilder.equal(placeRoot.get(Place_.number), number));
            entityManager.createQuery(criteriaQuery).getSingleResult();
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public Long getNumberFreePlaces(Date executeDate) {
        log.debug("[getFreePlaces]");
        log.trace("[executeDate: {}]", executeDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Place> placeRoot = criteriaQuery.from(Place.class);
        criteriaQuery.select(criteriaBuilder.count(placeRoot.get(Place_.id)));
        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Order> subOrderRoot = subquery.from(Order.class);
        subOrderRoot.join(Order_.place);
        subquery.select(subOrderRoot.get(Order_.place).get(Place_.id));
        subquery.where(criteriaBuilder.greaterThanOrEqualTo(subOrderRoot.get(Order_.leadTime), executeDate));
        criteriaQuery.where(placeRoot.get(Place_.id).in(subquery).not());
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Long getNumberPlaces() {
        log.debug("[getNumberPlaces]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Place> placeRoot = criteriaQuery.from(Place.class);
        criteriaQuery.select(criteriaBuilder.count(placeRoot));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
