package com.senla.carservice.api.printer;

import com.senla.carservice.domain.Order;

import java.util.ArrayList;
import java.util.Arrays;

public class PrinterOrder {

    public static void printOrder(ArrayList<Order> orders) {
        final int LENGTH = 167;
        char line = '-';
        char [] arrayChar = new char[LENGTH];
        Arrays.fill(arrayChar, line);
        StringBuilder stringBuilder = new StringBuilder(String.format(" %s\n", String.valueOf(arrayChar)));
        stringBuilder.append(String.format("|%-4s|%-10s|%-12s|%-19s|%-28s|%-28s|%-28s|%-10s|%-12s|%-7s|\n",
                "№",
                "Automaker",
                "Model",
                "Registration Number",
                "Creation Time",
                "Execution Start Time",
                "Lead Time",
                "Price",
                "Status",
                "Deleted"));
        stringBuilder.append(String.format("|%s|\n", String.valueOf(arrayChar)));
        for (int i = 0; i < orders.size(); i++){
            stringBuilder.append(String.format("|%-4s|%-10s|%-12s|%-19s|%s|%s|%s|%-10s|%-12s|%-7s|\n",
                    i+1,
                    orders.get(i).getCar().getAutomaker(),
                    orders.get(i).getCar().getModel(),
                    orders.get(i).getCar().getRegistrationNumber(),
                    orders.get(i).getCreationTime(),
                    orders.get(i).getExecutionStartTime(),
                    orders.get(i).getLeadTime(),
                    orders.get(i).getPrice(),
                    orders.get(i).getStatus(),
                    orders.get(i).isDeleteStatus()));
        }
        stringBuilder.append(String.format(" %s", String.valueOf(arrayChar)));
        System.out.println(stringBuilder.toString());
        }
    }
