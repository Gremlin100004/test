package com.senla.carservice.ui.action;

import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;
import com.senla.carservice.ui.printer.PrinterOrder;

import java.util.List;

public class ExecutedSortExecutionOrderActionImpl implements Action {

    public ExecutedSortExecutionOrderActionImpl() {
    }

    @Override
    public void execute() {
        OrderController orderController = OrderController.getInstance();
        List<Order> executedOrders = orderController.getExecuteOrder();
        executedOrders = orderController.sortOrderByLeadTime(executedOrders);
        if (executedOrders.isEmpty()) {
            System.out.println("There are no orders!");
            return;
        }
        PrinterOrder.printOrder(executedOrders);
    }
}