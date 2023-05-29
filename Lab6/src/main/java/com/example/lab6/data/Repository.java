package com.example.lab6.data;

import java.util.List;

public interface Repository {
    List<Apartment> getAll();
    Apartment getById(int id);
    List<Apartment> getAllAvailable(boolean bool);
    boolean addApartment(Apartment apartment);
    boolean deleteApartment(int id);
}
