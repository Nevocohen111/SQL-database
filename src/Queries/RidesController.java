package Queries;

import Configurations.MySQL;
import com.nevo.RideTableInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class RidesController {
    private final RideTableInfo rideTableInfo;
    private static volatile RidesController instance;
    private final MySQL sql;
    private final Scanner scanner = new Scanner(System.in);

    public static RidesController getInstance(MySQL sql) {
        RidesController localInstance = instance;
        if (localInstance == null) {
            synchronized (RidesController.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RidesController(sql);
                }
            }
        }
        return localInstance;
    }
    private RidesController(MySQL sql) {
        this.sql = sql;
        rideTableInfo = new RideTableInfo();
    }

    public static <T> T  validateUserInput(String message,Class<?> type) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        try {
            if (type == String.class) {
                return (T) scanner.nextLine();
            }
            if (type == Integer.class) {
                return (T) Integer.valueOf(scanner.nextLine());
            }
            if (type == Double.class) {
                return (T) Double.valueOf(scanner.nextLine());
            }
        }catch (InputMismatchException e) {
            System.out.println("Invalid input");
        }
        return null;
    }
    public static Boolean validateString(String name) {
        try{
            return name.length() > 0;
        }catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
        return false;
    }

    public static Boolean validateDate(String date) {
        try {
            return date.length() == 10;
        }catch (InputMismatchException e) {
            System.out.println("Invalid input");
            return false;
        }

    }



    public static Boolean validateInt(int someInt) {
        try {
            return someInt > 0 && someInt <= 100;
        }catch (NullPointerException e) {
            System.out.println("Invalid input");
            return false;
        }
    }

    public  void showAllRides() throws SQLException {
        String query = "SELECT * FROM public_rides";
        try (PreparedStatement stmt = sql.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rideTableInfo.setRideId(rs.getInt("rideId"));
                rideTableInfo.setComments(rs.getString("comments"));
                rideTableInfo.setStartPointName(rs.getString("startPointName"));
                rideTableInfo.setDestination(rs.getString("destination"));
                rideTableInfo.setRideTime(rs.getString("rideTime"));
                rideTableInfo.setBackTime(rs.getString("backTime"));
                rideTableInfo.setNumberOfPassengers(rs.getInt("numberOfPassengers"));
                rideTableInfo.setDate(rs.getString("date_time"));
                System.out.println(rideTableInfo);
            }
        }
    }

    public void addRide() throws SQLException {
        int id = validateUserInput("Enter the id of the ride: ",Integer.class);

        while(!validateInt(id)) {
            id = validateUserInput("Invalid id,Enter the id of the ride: ",Integer.class);
        }
        String comments = validateUserInput("Enter the comments for the ride: ",String.class);

        while(!validateString(Objects.requireNonNull(comments))) {
            comments = validateUserInput("Invalid comments,Enter the comments for the ride: ",String.class);
        }
        String startPointName = validateUserInput("Enter the start point name of the ride: ",String.class);

        while(!validateString(Objects.requireNonNull(startPointName))) {
            startPointName = validateUserInput("Invalid start point,Enter the start point of the ride: ",String.class);
        }
        String destination = validateUserInput("Enter destination point of the ride: ",String.class);

        while(!validateString(Objects.requireNonNull(destination))) {
            destination = validateUserInput("Invalid destination,Enter destination point of the ride: ",String.class);
        }
        String rideTime = validateUserInput("Enter the time of which you want to start to ride at: ",String.class);

        while(!validateString(Objects.requireNonNull(rideTime))) {
            rideTime = validateUserInput("Invalid time,Enter the time of which you want to start to ride at: ",String.class);
        }
        String backTime = validateUserInput("Enter the time of which you want to get back from the ride at: ",String.class);
        while(!validateString(Objects.requireNonNull(backTime))) {
            backTime = validateUserInput("Invalid time,Enter the time of which you want to get back from  ride at: ",String.class);
        }
        int numOfPassengers = validateUserInput("Enter the number of passengers: ",Integer.class);

        while(!validateInt(numOfPassengers)) {
            numOfPassengers = validateUserInput("Invalid number,Enter the number of passengers: ",Integer.class);
        }
        String date = validateUserInput("Enter the date of the ride: ",String.class);
        while(!validateDate(Objects.requireNonNull(date))) {
            date = validateUserInput("Invalid date,Enter the date of the ride: ", String.class);
        }
        System.out.println("Do you want to add a new ride? (y/n)");
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("y")) {
            String query = "INSERT INTO public_rides (rideId,comments,startPointName,destination,rideTime,backTime,numberOfPassengers,date_time) VALUES (?,?,?,?,?,?,?,?)";
            try (PreparedStatement stmt = sql.getConnection().prepareStatement(query)) {
                stmt.setInt(1, id);
                rideTableInfo.setRideId(id);
                stmt.setString(2, comments);
                rideTableInfo.setComments(comments);
                stmt.setString(3, startPointName);
                rideTableInfo.setStartPointName(startPointName);
                stmt.setString(4, destination);
                rideTableInfo.setDestination(destination);
                stmt.setString(5, rideTime);
                rideTableInfo.setRideTime(rideTime);
                stmt.setString(6, backTime);
                rideTableInfo.setBackTime(backTime);
                stmt.setInt(7, numOfPassengers);
                rideTableInfo.setNumberOfPassengers(numOfPassengers);
                stmt.setString(8, date);
                rideTableInfo.setDate(date);
                stmt.executeUpdate();
                System.out.println(rideTableInfo);
                System.out.println("Ride added successfully");
            }
        }else if(answer.equalsIgnoreCase("n")) {
            System.out.println("Ride not added");
        }else{
            System.out.println("Invalid answer");
        }
    }

    public void deleteRideById() throws SQLException {
        int id = validateUserInput("Enter the ride id: ",Integer.class);
        while(!validateInt(id)) {
            id = validateUserInput("Invalid id,Enter the ride id: ",Integer.class);
        }
        String query = "DELETE FROM public_rides WHERE rideId = ?";

        try (PreparedStatement stmt = sql.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            if(stmt.executeUpdate() == 1) {
                stmt.executeUpdate();
                System.out.println("Ride deleted successfully");
            }else{
                System.out.println("Ride not found");
            }
        }
    }

    public void searchRideById() throws SQLException {
        int id = validateUserInput("Enter the ride id: ",Integer.class);
        while(!validateInt(id)) {
            id = validateUserInput("Invalid id,Enter the ride id: ",Integer.class);
        }
        String query = "SELECT * FROM public_rides WHERE rideId = ?";
        try (PreparedStatement stmt = sql.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            //using if statement in order to get only 1 result due to primary Key =id.
            if(rs.next()) {
                rideTableInfo.setRideId(rs.getInt("rideId"));
                rideTableInfo.setComments(rs.getString("comments"));
                rideTableInfo.setStartPointName(rs.getString("startPointName"));
                rideTableInfo.setDestination(rs.getString("destination"));
                rideTableInfo.setRideTime(rs.getString("rideTime"));
                rideTableInfo.setBackTime(rs.getString("backTime"));
                rideTableInfo.setNumberOfPassengers(rs.getInt("numberOfPassengers"));
                rideTableInfo.setDate(rs.getString("date_time"));
                System.out.println(rideTableInfo);
            }else{
                System.out.println("Ride not found");
            }
        }
    }

    public void updateRideById() throws SQLException {
        int id = validateUserInput("Enter the ride id: ",Integer.class);
        while(!validateInt(id)) {
            id = validateUserInput("Invalid id,Enter the ride id: ",Integer.class);
        }
        String comments = validateUserInput("Enter the comments: ",String.class);
        while(!validateString(Objects.requireNonNull(comments))) {
            comments = validateUserInput("Invalid comments,Enter the comments: ",String.class);
        }
        String startPointName = validateUserInput("Enter the start point name: ",String.class);
        while(!validateString(Objects.requireNonNull(startPointName))) {
            startPointName = validateUserInput("Invalid start point name,Enter the start point name: ",String.class);
        }
        String destination = validateUserInput("Enter the destination: ",String.class);
        while(!validateString(Objects.requireNonNull(destination))) {
            destination = validateUserInput("Invalid destination,Enter the destination: ",String.class);
        }
        String rideTime = validateUserInput("Enter the ride time: ",String.class);
        while(!validateString(Objects.requireNonNull(rideTime))) {
            rideTime = validateUserInput("Invalid ride time,Enter the ride time: ",String.class);
        }
        String backTime = validateUserInput("Enter the back time: ",String.class);
        while(!validateString(Objects.requireNonNull(backTime))) {
            backTime = validateUserInput("Invalid back time,Enter the back time: ",String.class);
        }
        int numberOfPassengers = validateUserInput("Enter the number of passengers: ",Integer.class);
        while(!validateInt(numberOfPassengers)) {
            numberOfPassengers = validateUserInput("Invalid number of passengers,Enter the number of passengers: ",Integer.class);
        }
        String date_time = validateUserInput("Enter the date and time: ",String.class);
        while(!validateString(Objects.requireNonNull(date_time))) {
            date_time = validateUserInput("Invalid date and time,Enter the date and time: ",String.class);
        }

        String query = "UPDATE public_rides SET comments = ?, startPointName = ?, destination = ?, rideTime = ?, backTime = ?, numberOfPassengers = ?, date_time = ? WHERE rideId = ?";
        try (PreparedStatement stmt = sql.getConnection().prepareStatement(query)) {
            stmt.setString(1, comments);
            rideTableInfo.setComments(comments);
            stmt.setString(2, startPointName);
            rideTableInfo.setStartPointName(startPointName);
            stmt.setString(3, destination);
            rideTableInfo.setDestination(destination);
            stmt.setString(4, rideTime);
            rideTableInfo.setRideTime(rideTime);
            stmt.setString(5, backTime);
            rideTableInfo.setBackTime(backTime);
            stmt.setInt(6, numberOfPassengers);
            rideTableInfo.setNumberOfPassengers(numberOfPassengers);
            stmt.setString(7, date_time);
            rideTableInfo.setDate(date_time);
            stmt.setInt(8, id);
                rideTableInfo.setRideId(id);
                stmt.executeUpdate();
                System.out.println(rideTableInfo);
                System.out.println("Ride updated successfully");

        }
    }

    public void displayRidesByDate() throws SQLException {
        String query = "SELECT * FROM public_rides ORDER BY date_time ASC";
        try (PreparedStatement stmt = sql.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rideTableInfo.setRideId(rs.getInt("rideId"));
                rideTableInfo.setComments(rs.getString("comments"));
                rideTableInfo.setStartPointName(rs.getString("startPointName"));
                rideTableInfo.setDestination(rs.getString("destination"));
                rideTableInfo.setRideTime(rs.getString("rideTime"));
                rideTableInfo.setBackTime(rs.getString("backTime"));
                rideTableInfo.setNumberOfPassengers(rs.getInt("numberOfPassengers"));
                rideTableInfo.setDate(rs.getString("date_time"));
                System.out.println(rideTableInfo);

            }
        }
    }

    public void showRidesByDate() throws SQLException {
        String date = validateUserInput("Enter the date: ", String.class);
        while (!validateDate(Objects.requireNonNull(date))) {
            date = validateUserInput("Invalid date,Enter the date: ", String.class);
        }
        if (date.contains("/")) {
            date = date.replace("/", "-");
            String query = "SELECT * FROM public_rides WHERE date_time = ?";
            try (PreparedStatement stmt = sql.getConnection().prepareStatement(query)) {
                stmt.setString(1, date);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    rideTableInfo.setRideId(rs.getInt("rideId"));
                    rideTableInfo.setComments(rs.getString("comments"));
                    rideTableInfo.setStartPointName(rs.getString("startPointName"));
                    rideTableInfo.setDestination(rs.getString("destination"));
                    rideTableInfo.setRideTime(rs.getString("rideTime"));
                    rideTableInfo.setBackTime(rs.getString("backTime"));
                    rideTableInfo.setNumberOfPassengers(rs.getInt("numberOfPassengers"));
                    rideTableInfo.setDate(rs.getString("date_time"));
                    System.out.println(rideTableInfo);

                }
            }
        } else {
            System.out.println("date format needed is yyyy/mm/dd");
        }
    }

}
