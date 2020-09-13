package com.senla.carservice.dao;

import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Order_;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.Place_;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Date;
import java.util.List;

@Component
public class PlaceDaoImpl extends AbstractDao<Place, Long> implements PlaceDao {

    public PlaceDaoImpl() {
    }

    @Override
    public List<Place> getFreePlaces(Date executeDate) {
        LOGGER.debug("Method getFreePlaces");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
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
        List<Place> places = query.getResultList();
        if (places == null) {
            throw new DaoException("Error getting busy places");
        }
        return places;
    }

    @Override
    public Long getNumberFreePlaces(Date executeDate) {
        LOGGER.debug("Method getFreePlaces");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
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
        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public Long getNumberPlaces() {
        LOGGER.debug("Method getNumberPlaces");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Place> placeRoot = criteriaQuery.from(Place.class);
        criteriaQuery.select(criteriaBuilder.count(placeRoot));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}