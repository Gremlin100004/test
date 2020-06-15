package com.senla.carservice.comporator;

import com.senla.carservice.domain.Order;

import java.util.Comparator;

public class OrderPriceComparator implements Comparator<Order> {

    @Override
    public int compare(Order orderOne, Order orderTwo) {
        if (orderOne.getPrice() == null && orderTwo.getPrice() == null) return 0;
        if (orderOne.getPrice() == null) return -1;
        if (orderTwo.getPrice() == null) return 1;
        return orderOne.getPrice().compareTo(orderTwo.getPrice());
    }
}