package com.example.lab6.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseRepository implements Repository{
    private DataBaseConnector dataBaseConnector;

    public DataBaseRepository(DataBaseConnector dataBaseConnector) {

        this.dataBaseConnector = dataBaseConnector;
        try (Connection conn = dataBaseConnector.getConnection()) {
            String tableCreateStr =
                    "CREATE TABLE IF NOT EXISTS Apartments (\n" +
                            "    id INT NOT NULL AUTO_INCREMENT,\n" +
                            "    address VARCHAR(50),\n" +
                            "    numberOfRooms INT,\n" +
                            "    price INT,\n" +
                            "    isRented BOOLEAN,\n" +
                            "    rentalDate VARCHAR(50),\n" +
                            "    rentalTerm VARCHAR(50),\n" +
                            "    PRIMARY KEY (id)\n" +
                            ");";

            Statement createTable = conn.createStatement();
            createTable.execute(tableCreateStr);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Apartment> getAll() {
        List<Apartment> apartments = new ArrayList<>();

        try (Connection connection = dataBaseConnector.getConnection()){

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Apartments");
            while (rs.next()){
                apartments.add(new Apartment(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getBoolean(5),
                        rs.getString(6),
                        rs.getString(7)));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }

        return apartments;
    }

    @Override
    public Apartment getById(int id) {
        Apartment apartment = null;
        try(Connection connection = dataBaseConnector.getConnection()){
            PreparedStatement statement =
                    connection.prepareStatement("select * from Apartments where id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                apartment = new Apartment(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getBoolean(5),
                        rs.getString(6),
                        rs.getString(7));
            }
            rs.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            return apartment;
        }
    }

    @Override
    public List<Apartment> getAllAvailable(boolean bool) {
        List<Apartment> apartments = new ArrayList<>();
        try(Connection connection = dataBaseConnector.getConnection()){
            PreparedStatement statement =
                    connection.prepareStatement(
                            "select * from Apartments where isRented = ?"
                    );
            statement.setBoolean(1,  bool);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                apartments.add(new Apartment(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getBoolean(5),
                        rs.getString(6),
                        rs.getString(7)));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return apartments;
    }

    @Override
    public boolean addApartment(Apartment apartment) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement =
                    conn.prepareStatement("INSERT INTO Apartments (address, numberOfRooms," +
                            "price, isRented, rentalDate, rentalTerm) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, apartment.getAddress());
            preparedStatement.setInt(2, apartment.getNumberOfRooms());
            preparedStatement.setInt(3, apartment.getPrice());
            preparedStatement.setBoolean(4, apartment.isRented());
            preparedStatement.setString(5, apartment.getRentalDate());
            preparedStatement.setString(6, apartment.getRentalTerm());
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount>0;
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

    @Override
    public boolean deleteApartment(int id) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement =
                    conn.prepareStatement("DELETE FROM Apartments WHERE id = ?");
            preparedStatement.setInt(1, id);
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount>0;
    }

}

