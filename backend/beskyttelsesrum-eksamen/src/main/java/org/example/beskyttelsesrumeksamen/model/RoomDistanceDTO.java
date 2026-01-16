package org.example.beskyttelsesrumeksamen.model;

public class RoomDistanceDTO {
    private Beskyttelsesrum room;
    private double distance;

    public RoomDistanceDTO(Beskyttelsesrum room, double distance) {
        this.room = room;
        this.distance = distance;
    }

    public Beskyttelsesrum getRoom() {
        return room;
    }

    public void setRoom(Beskyttelsesrum room) {
        this.room = room;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
