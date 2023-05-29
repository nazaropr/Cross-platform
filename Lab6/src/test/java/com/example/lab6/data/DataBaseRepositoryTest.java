package com.example.lab6.data;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseRepositoryTest {

    @Test
    void testDriver() {
        DataBaseConnector testConnector = new DataBaseConnector(("Apartments"));
        assertTrue((testConnector.testDriver()));
    }

    @Test
    void getConnection() {
        DataBaseConnector testConnector = new DataBaseConnector(("Apartments"));
        try {
            assertNotNull((testConnector.getConnection()));
        } catch (SQLException e) {
            fail();
        }
    }
    @Test
    void addApartment() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("Apartments"));
        repository.addApartment(new Apartment(7, "address7", 1, 10000, false, "", ""));
        repository.addApartment(new Apartment(8, "address8", 2, 20000, true, "date rent8", "term rent38"));
        repository.addApartment(new Apartment(9, "address9", 3, 30000, false, "", ""));
        repository.addApartment(new Apartment(10, "address10", 4, 40000, true, "date rent10", "term rent10"));
        repository.addApartment(new Apartment(11, "address11", 5, 50000, false, "", ""));
        repository.addApartment(new Apartment(12, "address12", 6, 60000, true, "date rent12", "term rent12"));

        assertNotNull(repository.getById(2));
        java.util.List<Apartment> apartments = repository.getAll();
        assertTrue(apartments.size()>0);
    }

    @Test
    void getAll() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("Apartments"));
        System.out.println(repository.getAll());
    }

    @Test
    void getById() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("Apartments"));
        System.out.println(repository.getById(1));
    }

    @Test
    void getAllAvailableTrue() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("Apartments"));
        System.out.println("true " + repository.getAllAvailable(true));
    }
    @Test
    void getAllAvailableFalse() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("Apartments"));
        System.out.println("false " + repository.getAllAvailable(false));
    }


   /* @Test
    void deleteApartment() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("test2"));
        repository.deleteApartment(1);
        assertNull(repository.getById(1));
    }*/
}