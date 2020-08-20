package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.enumaration.Status;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.service.enumaration.SortParameter;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class OrderServiceImpl implements OrderService {
    @Dependency
    private OrderDao orderDao;
    @Dependency
    private PlaceDao placeDao;
    @Dependency
    private MasterDao masterDao;
    @ConfigProperty
    private boolean isBlockShiftTime;

    public OrderServiceImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getOrders() {
        List<Order> orders = orderDao.getAllRecords();
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addOrder(String automaker, String model, String registrationNumber) {
        checkMasters();
        checkPlaces();
        orderDao.createRecord(new Order(automaker, model, registrationNumber));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addOrderDeadlines(Date executionStartTime, Date leadTime) {
        DateUtil.checkDateTime(executionStartTime, leadTime, false);
        Order currentOrder = orderDao.getLastOrder();
        if (currentOrder == null) {
            throw new BusinessException("There are no orders");
        }
        String stringExecutionStartTime = DateUtil.getStringFromDate(executionStartTime, true);
        String stringLeadTime = DateUtil.getStringFromDate(leadTime, true);
        int numberFreeMasters = orderDao.getNumberFreeMasters(stringExecutionStartTime, stringLeadTime);
        int numberFreePlace = orderDao.getNumberFreePlaces(stringExecutionStartTime, stringLeadTime);
        if (numberFreeMasters == 0) {
            throw new BusinessException("The number of masters is zero");
        }
        if (numberFreePlace == 0) {
            throw new BusinessException("The number of places is zero");
        }
        currentOrder.setExecutionStartTime(executionStartTime);
        currentOrder.setLeadTime(leadTime);
        orderDao.updateRecord(currentOrder);
    }

    @Override
    public void addOrderMasters(Master master) {
        Order currentOrder = orderDao.getLastOrder();
        if (currentOrder == null) {
            throw new BusinessException("There are no orders");
        }
        List<Master> masters = orderDao.getOrderMasters(currentOrder);
        for (Master orderMaster : masters) {
            if (orderMaster.equals(master)) {
                throw new BusinessException("This master already exists");
            }
        }
        masters.add(master);
        orderDao.createRecordTableManyToMany(currentOrder, master);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addOrderPlace(Place place) {
        Order currentOrder = orderDao.getLastOrder();
        if (currentOrder == null) {
            throw new BusinessException("There are no orders");
        }
        currentOrder.setPlace(place);
        orderDao.updateRecord(currentOrder);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addOrderPrice(BigDecimal price) {
        Order currentOrder = orderDao.getLastOrder();
        if (currentOrder == null) {
            throw new BusinessException("There are no orders");
        }
        currentOrder.setPrice(price);
        orderDao.updateRecord(currentOrder);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void completeOrder(Order order) {
        checkStatusOrder(order);
        order.setStatus(Status.PERFORM);
        order.setExecutionStartTime(new Date());
        order.getPlace().setBusyStatus(true);
        orderDao.updateRecord(order);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void cancelOrder(Order order) {
        checkStatusOrder(order);
        order.setLeadTime(new Date());
        order.setStatus(Status.CANCELED);
        orderDao.updateRecord(order);
        Place place = order.getPlace();
        place.setBusyStatus(false);
        placeDao.updateRecord(place);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void closeOrder(Order order) {
        checkStatusOrder(order);
        order.setLeadTime(new Date());
        order.setStatus(Status.COMPLETED);
        orderDao.updateRecord(order);
        Place place = order.getPlace();
        place.setBusyStatus(false);
        placeDao.updateRecord(place);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteOrder(Order order) {
        orderDao.deleteRecord(order);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void shiftLeadTime(Order order, Date executionStartTime, Date leadTime) {
        if (isBlockShiftTime) {
            throw new BusinessException("Permission denied");
        }
        DateUtil.checkDateTime(executionStartTime, leadTime, false);
        checkStatusOrderShiftTime(order);
        order.setLeadTime(leadTime);
        order.setExecutionStartTime(executionStartTime);
        orderDao.updateRecord(order);
    }

    @Override
    public List<Order> getSortOrders(SortParameter sortParameter) {
        List<Order> orders = new ArrayList<>();
        if (sortParameter.equals(SortParameter.SORT_BY_FILING_DATE)){
            orders = orderDao.getOrdersSortByFilingDate();
        } else if (sortParameter.equals(SortParameter.SORT_BY_EXECUTION_DATE)){
            orders = orderDao.getOrdersSortByExecutionDate();
        } else if (sortParameter.equals(SortParameter.BY_PLANNED_START_DATE)){
            orders = orderDao.getOrdersSortByPlannedStartDate();
        } else if (sortParameter.equals(SortParameter.SORT_BY_PRICE)){
            orders = orderDao.getOrdersSortByPrice();
        } else if (sortParameter.equals(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE)){
            orders = orderDao.getExecuteOrderSortByFilingDate();
        } else if (sortParameter.equals(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE)){
            orders = orderDao.getExecuteOrderSortExecutionDate();
        }
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    @Override
    public List<Order> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter) {
        List<Order> orders = new ArrayList<>();
        DateUtil.checkDateTime(startPeriodDate, endPeriodDate, true);
        String stringStartPeriodDate = DateUtil.getStringFromDate(startPeriodDate, true);
        String stringEndPeriodDate = DateUtil.getStringFromDate(endPeriodDate, true);
        if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE)){
            orders = orderDao.getCompletedOrdersSortByFilingDate(stringStartPeriodDate, stringEndPeriodDate);
        } else if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE)){
            orders = orderDao.getCompletedOrdersSortByExecutionDate(stringStartPeriodDate, stringEndPeriodDate);
        } else if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE)){
            orders = orderDao.getCompletedOrdersSortByPrice(stringStartPeriodDate, stringEndPeriodDate);
        } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE)){
            orders = orderDao.getCanceledOrdersSortByFilingDate(stringStartPeriodDate, stringEndPeriodDate);
        } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE)){
            orders = orderDao.getCanceledOrdersSortByExecutionDate(stringStartPeriodDate, stringEndPeriodDate);
        } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_PRICE)){
            orders = orderDao.getCanceledOrdersSortByPrice(stringStartPeriodDate, stringEndPeriodDate);
        } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE)){
            orders = orderDao.getDeletedOrdersSortByFilingDate(stringStartPeriodDate, stringEndPeriodDate);
        } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE)){
            orders = orderDao.getDeletedOrdersSortByExecutionDate(stringStartPeriodDate, stringEndPeriodDate);
        } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_PRICE)){
            orders = orderDao.getDeletedOrdersSortByPrice(stringStartPeriodDate, stringEndPeriodDate);
        }
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        List<Order> orders = orderDao.getMasterOrders(master);
        if (orders.isEmpty()) {
            throw new BusinessException("Master doesn't have any orders");
        }
        return orders;
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        List<Master> masters = orderDao.getOrderMasters(order);
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters in order");
        }
        return masters;
    }

    private void checkMasters() {
        if (masterDao.getAllRecords().isEmpty()) {
            throw new BusinessException("There are no masters");
        }
    }

    private void checkPlaces() {
        if (masterDao.getAllRecords().isEmpty()) {
            throw new BusinessException("There are no places");
        }
    }

    private void checkStatusOrder(Order order) {
        if (order.isDeleteStatus()) {
            throw new BusinessException("The order has been deleted");
        }
        if (order.getStatus() == Status.COMPLETED) {
            throw new BusinessException("The order has been completed");
        }
        if (order.getStatus() == Status.PERFORM) {
            throw new BusinessException("Order is being executed");
        }
        if (order.getStatus() == Status.CANCELED) {
            throw new BusinessException("The order has been canceled");
        }
    }

    private void checkStatusOrderShiftTime(Order order) {
        if (order.isDeleteStatus()) {
            throw new BusinessException("The order has been deleted");
        }
        if (order.getStatus() == Status.COMPLETED) {
            throw new BusinessException("The order has been completed");
        }
        if (order.getStatus() == Status.CANCELED) {
            throw new BusinessException("The order has been canceled");
        }
    }
}