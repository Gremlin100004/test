package com.senla.carservice.dao;

import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.ConstructorDependency;
import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.enumaration.Status;
import com.senla.carservice.exception.BusinessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends AbstractDao implements OrderDao{
    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO orders VALUES (NULL";
    private static final String END_REQUEST_TO_ADD_RECORD = ")";
    private static final String SEPARATOR = ", ";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS = "SELECT orders.id, orders.creation_time, " +
            "orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, orders.registration_number, " +
            "orders.price, orders.status, orders.delete_status, places.id AS order_place_id, places.number AS place_number, " +
            "places.busy_status AS place_busy_status, places.delete_status AS place_delete_status FROM orders JOIN " +
            "places ON orders.place_id = places.id;";
    private static final String SQL_REQUEST_TO_GET_LAST_RECORD = "SELECT orders.id, orders.creation_time, " +
            "orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, orders.registration_number, " +
            "orders.price, orders.status, orders.delete_status, places.id AS order_place_id, places.number AS place_number, " +
            "places.busy_status AS place_busy_status, places.delete_status AS place_delete_status FROM orders JOIN " +
            "places ON orders.place_id = places.id;";
    private static final String SQL_REQUEST_TO_UPDATE_RECORD = "UPDATE orders SET creation_time=";
    private static final String FIELD_EXECUTION_START_TIME = " execution_start_time=";
    private static final String FIELD_LEAD_TIME = " lead_time=";
    private static final String FIELD_AUTOMAKERS = " automaker=";
    private static final String FIELD_MODEL = " model=";
    private static final String FIELD_REGISTRATION_NUMBER = " registration_number=";
    private static final String FIELD_PRICE = " price=";
    private static final String FIELD_STATUS = " status=";
    private static final String FIELD_DELETE_STATUS = " delete_status=";
    private static final String FIELD_PLACE_ID = " place_id=";
    private static final String PRIMARY_KEY_FIELD = " WHERE id=";
    private static final String SQL_REQUEST_TO_DELETE_RECORD = "UPDATE orders SET delete_status=true WHERE id=";

    @ConstructorDependency
    public OrderDaoImpl(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

    public OrderDaoImpl() {
    }

    @Override
    public Order getLastOrder() {
        Order order = getOrdersFromDatabase(SQL_REQUEST_TO_GET_LAST_RECORD).get(0);
        //TODO check null and request
        return order;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<Order> parseResultSet(ResultSet resultSet) {
        try {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order(resultSet.getString("automaker"),
                        resultSet.getString("model"), resultSet.getString("registration_number"));
                order.setCreationTime(resultSet.getDate("creation_time"));
                order.setExecutionStartTime(resultSet.getDate("execution_start_time"));
                order.setLeadTime(resultSet.getDate("lead_time"));
                order.setPrice(resultSet.getBigDecimal("price"));
                order.setStatus(Status.valueOf(resultSet.getString("status")));
                order.setDeleteStatus(resultSet.getBoolean("delete_status"));
                Place place = new Place();
                place.setId(resultSet.getLong("order_place_id"));
                place.setNumber(resultSet.getInt("place_number"));
                place.setBusyStatus(resultSet.getBoolean("place_busy_status"));
                place.setDelete(resultSet.getBoolean("place_delete_status"));
                order.setPlace(place);
                orders.add(order);
            }
            return orders;
        } catch (SQLException ex) {
            throw new BusinessException("Error request get records orders");
        }
    }

    @Override
    protected String getCreateRequest(Object object) {
        Order order = (Order) object;
        return SQL_REQUEST_TO_ADD_RECORD + order.getCreationTime() +
                SEPARATOR + order.getExecutionStartTime() +
                SEPARATOR + order.getLeadTime() +
                SEPARATOR + order.getAutomaker() +
                SEPARATOR + order.getModel() +
                SEPARATOR + order.getRegistrationNumber() +
                SEPARATOR + order.getRegistrationNumber() +
                SEPARATOR + order.getPrice() +
                SEPARATOR + order.getStatus() +
                SEPARATOR + order.getPlace().getId() +
                END_REQUEST_TO_ADD_RECORD;
    }

    @Override
    protected String getReadAllRequest() {
        return SQL_REQUEST_TO_GET_ALL_RECORDS;
    }

    @Override
    protected String getUpdateRequest(Object object) {
        Order order = (Order) object;
        return SQL_REQUEST_TO_UPDATE_RECORD + order.getCreationTime() +
                FIELD_EXECUTION_START_TIME + order.getExecutionStartTime() +
                FIELD_LEAD_TIME + order.getLeadTime() +
                FIELD_AUTOMAKERS + order.getAutomaker() +
                FIELD_MODEL + order.getModel() +
                FIELD_REGISTRATION_NUMBER + order.getRegistrationNumber() +
                FIELD_PRICE + order.getPrice() +
                FIELD_STATUS + order.getStatus() +
                FIELD_DELETE_STATUS + order.isDeleteStatus() +
                FIELD_PLACE_ID + order.getPlace().getId() +
                PRIMARY_KEY_FIELD + order.getId();
    }

    @Override
    protected String getDeleteRequest(Object object) {
        Order order = (Order) object;
        return SQL_REQUEST_TO_DELETE_RECORD + order.getId();
    }

    private List<Order> getOrdersFromDatabase(String request){
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(request);
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            throw new BusinessException("Error request get records orders");
        }
    }
}