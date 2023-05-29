package com.example.lab6.data

interface Repository {
    fun getAll(): List<Apartment>
    fun getById(id: Int): Apartment?
    fun getAllAvailable(bool: Boolean): List<Apartment>
    fun addApartment(apartment: Apartment): Boolean
    fun deleteApartment(id: Int): Boolean
}
