package com.nevo;

public class RideTableInfo {
    private int rideId;
    private String comments;
    private String startPointName;
    private String destination;
    private String rideTime;
    private String backTime;
    private int numberOfPassengers;
    private String date;
    public RideTableInfo() {}

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setStartPointName(String startPointName) {
        this.startPointName = startPointName;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String toString(){
        return "RideTableInfo{" +
                "rideId=" + rideId +
                ", comments='" + comments + '\'' +
                ", startPointName='" + startPointName + '\'' +
                ", destination='" + destination + '\'' +
                ", rideTime='" + rideTime + '\'' +
                ", backTime='" + backTime + '\'' +
                ", numberOfPassengers=" + numberOfPassengers +
                ", date='" + date + '\'' +
                '}';
    }


}
