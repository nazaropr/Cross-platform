package com.example.lab6.data

import java.sql.SQLException
import java.util.*

class DataBaseRepository(private val dataBaseConnector: DataBaseConnector) : Repository {
    init {
        try {
            dataBaseConnector.connection.use { conn ->
                val tableCreateStr = """CREATE TABLE IF NOT EXISTS Apartments (
                id INT NOT NULL AUTO_INCREMENT,
                address VARCHAR(50),
                numberOfRooms INT,
                price INT,
                isRented BOOLEAN,
                rentalDate VARCHAR(50),
                rentalTerm VARCHAR(50),
                PRIMARY KEY (id)
            );"""
                val createTable = conn.createStatement()
                createTable.execute(tableCreateStr)
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    override fun getAll(): List<Apartment> {
        val apartments: MutableList<Apartment> = ArrayList()
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.createStatement()
                val rs = statement.executeQuery("select * from Apartments")
                while (rs.next()) {
                    apartments.add(Apartment(rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getBoolean(5),
                            rs.getString(6),
                            rs.getString(7)))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return apartments
    }

    override fun getById(id: Int): Apartment? {
        var apartment: Apartment? = null
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.prepareStatement("select * from Apartments where id = ?")
                statement.setInt(1, id)
                val rs = statement.executeQuery()
                if (rs.next()) {
                    apartment = Apartment(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getBoolean(5),
                            rs.getString(6),
                            rs.getString(7)
                    )
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            exception.printStackTrace()
        }
        return apartment
    }


    override fun getAllAvailable(bool: Boolean): List<Apartment> {
        val apartments: MutableList<Apartment> = ArrayList()
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.prepareStatement(
                        "select * from Apartments where isRented = ?"
                )
                statement.setBoolean(1, bool)
                val rs = statement.executeQuery()
                while (rs.next()) {
                    apartments.add(Apartment(rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getBoolean(5),
                            rs.getString(6),
                            rs.getString(7)))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return apartments
    }

    override fun addApartment(apartment: Apartment): Boolean {
        var updCount = 0
        try {
            dataBaseConnector.connection.use { conn ->
                val preparedStatement = conn.prepareStatement("INSERT INTO Apartments (address, numberOfRooms," +
                        "price, isRented, rentalDate, rentalTerm) VALUES (?,?,?,?,?,?)")
                preparedStatement.setString(1, apartment.address)
                preparedStatement.setInt(2, apartment.numberOfRooms)
                preparedStatement.setInt(3, apartment.price)
                preparedStatement.setBoolean(4, apartment.isRented)
                preparedStatement.setString(5, apartment.rentalDate)
                preparedStatement.setString(6, apartment.rentalTerm)
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }

    /*@Override
    public boolean updateApartment(int id, Apartment apartment) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement =
                    conn.prepareStatement( "UPDATE Apartments " +
                            "SET address = ?,\n" +
                            "numberOfRooms = ?,\n" +
                            "price = ?,\n" +
                            "isRented = ?,\n" +
                            "rentalDate = ?,\n" +
                            "rentalTerm = ?\n" +
                            "WHERE id = ?");
            preparedStatement.setString(1, apartment.getAddress());
            preparedStatement.setInt(2, apartment.getNumberOfRooms());
            preparedStatement.setInt(3, apartment.getPrice());
            preparedStatement.setBoolean(4, apartment.isRented());
            preparedStatement.setString(5, apartment.getRentalDate());
            preparedStatement.setString(6, apartment.getRentalTerm());
            preparedStatement.setInt(7, id);
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount>0;
    }*/
    override fun deleteApartment(id: Int): Boolean {
        var updCount = 0
        try {
            dataBaseConnector.connection.use { conn ->
                val preparedStatement = conn.prepareStatement("DELETE FROM Apartments WHERE id = ?")
                preparedStatement.setInt(1, id)
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }
}
