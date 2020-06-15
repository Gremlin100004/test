package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterGarages;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.domain.Garage;

import java.util.ArrayList;
import java.util.Scanner;

public final class DeletePlaceActionImpl implements Action {
    private static DeletePlaceActionImpl instance;

    public DeletePlaceActionImpl() {
    }

    public static DeletePlaceActionImpl getInstance() {
        if (instance == null) {
            instance = new DeletePlaceActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        GarageController garageController = new GarageController();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Garage> garages = garageController.getArrayGarages();
        if (garages.size() == 0) {
            System.out.println("There are no garages to delete place!");
            return;
        }
        PrinterGarages.printGarages(garages);
        System.out.println("0. Previous menu");
        String message;
        while (true) {
            System.out.println("Enter the index number of the garage to delete:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            int index = scanner.nextInt();
            if (index == 0){
                return;
            }
            if (index > garages.size() || index < 0) {
                System.out.println("There is no such garage");
                continue;
            }
            if (garages.get(index-1).getPlaces().size() < 1) {
                System.out.println("There are no places in garage!");
                continue;
            }
            message = garageController.deleteGaragePlace(garages.get(index - 1));
            break;
        }
        System.out.println(message);
    }
}