package com.example.lab6.data

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.sql.SQLException

class DataBaseRepositoryTest {

    @Test
    fun testDriver() {
        val testConnector = DataBaseConnector("Apartments")
        assertTrue(testConnector.testDriver())
    }

    @Test
    fun getConnection() {
        val testConnector = DataBaseConnector("Apartments")
        try {
            assertNotNull(testConnector.connection)
        } catch (e: SQLException) {
            fail<Any>()
        }
    }

    @Test
    fun getAll() {
        val repository = DataBaseRepository(
                DataBaseConnector("Apartments"))
        println(repository.getAll())
    }

    @Test
    fun getById() {
        val repository = DataBaseRepository(
                DataBaseConnector("Apartments"))
        println(repository.getById(2));
    }

    @Test
    fun getAllAvailableTrue() {
        val repository = DataBaseRepository(
                DataBaseConnector("Apartments"))
        println("true " + repository.getAllAvailable(true))
    }

    @Test
    fun getAllAvailableFalse() {
        val repository = DataBaseRepository(
                DataBaseConnector("Apartments"))
        println("false " + repository.getAllAvailable(false))
    }

  /*  @Test
    fun addApartment() {
        val repository = DataBaseRepository(
                DataBaseConnector("Apartments"))
        repository.addApartment(Apartment( "address5", 3, 30000, false, "", ""))
        assertNotNull(repository.getById(2))
        val apartments = repository.getAll()
        assertTrue(apartments.size > 0)
    }

   @Test
    fun deleteApartment() {
       val repository = DataBaseRepository(
               DataBaseConnector("Apartments"))
       repository.deleteApartment(4)
       assertNull(repository.getById(4))
    }*/
}