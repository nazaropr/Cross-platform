package com.example.lab6.data

import java.io.Serializable

class Apartment : Serializable {
    var id: Int
    val address: String
    val numberOfRooms: Int
    var price: Int
    var isRented: Boolean
    var rentalDate: String
    var rentalTerm: String

    constructor(id: Int, address: String, numberOfRooms: Int, price: Int, isRented: Boolean, rentalDate: String, rentalTerm: String) {
        this.id = id
        this.address = address
        this.numberOfRooms = numberOfRooms
        this.price = price
        this.rentalDate = rentalDate
        this.rentalTerm = rentalTerm
        this.isRented = isRented

    }
    constructor(address: String, numberOfRooms: Int, price: Int, isRented: Boolean, rentalDate: String, rentalTerm: String) {
        id=0
        this.address = address
        this.numberOfRooms = numberOfRooms
        this.price = price
        this.rentalDate = rentalDate
        this.rentalTerm = rentalTerm
        this.isRented = isRented
    }

    override fun toString(): String {
        return "$address, $numberOfRooms, $price, $rentalDate, $rentalTerm, "
    }
}
