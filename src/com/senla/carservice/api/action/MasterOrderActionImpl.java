package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterMaster;
import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;
import java.util.Scanner;

public final class MasterOrderActionImpl implements Action {
    private static MasterOrderActionImpl instance;

    public MasterOrderActionImpl() {
    }

    public static MasterOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new MasterOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        MasterController masterController = new MasterController();
        OrderController orderController = new OrderController();
        ArrayList<Master> masters = masterController.getMasters();
        Scanner scanner = new Scanner(System.in);
        int index;
        if (masters.size() == 0) {
            System.out.println("There are no masters.");
            return;
        }
        PrinterMaster.printMasters(masters);
        ArrayList<Order> orders;
        while (true) {
            System.out.println("Enter the index number of the master to view orders:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            index = scanner.nextInt();
            if (index > masters.size() || index < 1) {
                System.out.println("There is no such master");
                continue;
            }
            orders = orderController.getMasterOrders(masters.get(index - 1));
            if (orders.size() < 1) {
                System.out.println("Such master has no orders!");
                continue;
            }
            PrinterOrder.printOrder(orders);
            break;
        }
    }
}