package com.nevo;

import Queries.RidesController;

import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);

    public static void messages() {
        System.out.println("1. Show all rides");
        System.out.println("2. Add a new ride");
        System.out.println("3. Delete a ride by id");
        System.out.println("4. Search a ride by id");
        System.out.println("5. Update a ride by id");
        System.out.println("6. Show all rides by an ascending date order");
        System.out.println("7. Show all rides by date format: yyyy-mm-dd");
        System.out.println("Write bye or Bye to stop the application");
        System.out.println();
        System.out.print("Please enter your choice: ");
    }

    public static void initiateApp(RidesController ridesController) throws SQLException {
        System.out.println("Welcome to the Ride archive!");
        System.out.println("Here is our menu");
        messages();
        String choice = scanner.nextLine();
        while (!choice.equalsIgnoreCase("Bye")) {
            switch (choice) {
                case "1" -> {
                    ridesController.showAllRides();
                    System.out.println("*******************************************************************************************************************************************************************************************");
                    System.out.println();
                }
                case "2" -> {
                    ridesController.addRide();
                    System.out.println("*******************************************************************************************************************************************************************************************");
                    System.out.println();
                }
                case "3" -> {
                    ridesController.deleteRideById();
                    System.out.println("*******************************************************************************************************************************************************************************************");
                    System.out.println();
                }
                case "4" -> {
                    ridesController.searchRideById();
                    System.out.println("*******************************************************************************************************************************************************************************************");
                    System.out.println();
                }
                case "5" -> {
                    ridesController.updateRideById();
                    System.out.println("*******************************************************************************************************************************************************************************************");
                    System.out.println();
                }
                case "6" -> {
                    ridesController.displayRidesByDate();
                    System.out.println("*******************************************************************************************************************************************************************************************");
                    System.out.println();
                }
                case "7" -> {
                    ridesController.showRidesByDate();
                    System.out.println("*******************************************************************************************************************************************************************************************");
                    System.out.println();
                }
                default -> {
                    System.out.println("Invalid choice");
                }
            }
            messages();
            choice = scanner.nextLine();
        }
        System.out.println("Bye!");
    }
}
