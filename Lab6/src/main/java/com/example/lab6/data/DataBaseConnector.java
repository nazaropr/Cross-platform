package com.example.lab6.data;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DataBaseConnector {
    private String dataBaseUrl;
    private String dataBaseUser;
    private String dataBasePassword;
    private String apartmentClass;
    public DataBaseConnector(String dataBaseName){
        this.dataBaseUrl = "jdbc:h2:/POLITECH/6_sem/крос-платформенне/лаби/Lab_6/Lab6/db/Apartments";
        this.dataBaseUser = "User";
        this.dataBasePassword = "password";
        this.apartmentClass = "org.h2.Driver";
    }

    public boolean testDriver(){
        try{
            Class.forName(apartmentClass)
                    .getDeclaredConstructor().newInstance();
            return true;
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dataBaseUrl,
                dataBaseUser,dataBasePassword);
    }
}


