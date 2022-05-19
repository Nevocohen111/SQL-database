package com.nevo;

import Configurations.MySQL;
import Queries.RidesController;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
       MySQL mySQL = new MySQL();
        mySQL.perform("CREATE TABLE IF NOT EXISTS public_rides(rideId INT PRIMARY KEY, comments VARCHAR(255), startPointName VARCHAR(255),destination VARCHAR(255), rideTime VARCHAR(255), backTime VARCHAR(255), numberOfPassengers INT, date_time Date)");
        Application.initiateApp(RidesController.getInstance(mySQL));
    }
}