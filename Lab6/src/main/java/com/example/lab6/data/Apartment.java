package com.example.lab6.data;

import java.io.Serializable;

public class Apartment implements Serializable {
    private int id;
    private final String address;
    private final int numberOfRooms;
    private int price;
    private boolean isRented;
    private String rentalDate;
    private String rentalTerm;

    public Apartment(String address, int numberOfRooms, int price, boolean isRented, String rentalDate, String rentalTerm) {
        this.address = address;
        this.numberOfRooms = numberOfRooms;
        this.price = price;
        this.rentalDate = rentalDate;
        this.rentalTerm = rentalTerm;
        this.isRented = isRented;
    }

    public Apartment(int id, String address, int numberOfRooms, int price, boolean isRented, String rentalDate, String rentalTerm) {
        this.id = id;
        this.address = address;
        this.numberOfRooms = numberOfRooms;
        this.price = price;
        this.rentalDate = rentalDate;
        this.rentalTerm = rentalTerm;
        this.isRented = isRented;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public String getRentalTerm() {
        return rentalTerm;
    }

    public void setRentalTerm(String rentalTerm) {
        this.rentalTerm = rentalTerm;
    }

    @Override
    public String toString() {
        return address + ", " + numberOfRooms + ", " + price + ", " + rentalDate + ", " + rentalTerm + ", ";
    }

}

