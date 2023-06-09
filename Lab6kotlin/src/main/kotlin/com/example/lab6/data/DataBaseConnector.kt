package com.example.lab6.data

import java.lang.reflect.InvocationTargetException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DataBaseConnector(dataBaseName: String?) {
    private val dataBaseUrl = "jdbc:h2:/POLITECH/6_sem/крос-платформенне/лаби/Lab_6/Lab6kotlin/db/Apartments"
    private val dataBaseUser = "User"
    private val dataBasePassword = "password"
    private val apartmentClass = "org.h2.Driver"

    fun testDriver(): Boolean {
        return try {
            Class.forName(apartmentClass)
                    .getDeclaredConstructor().newInstance()
            true
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            false
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            false
        } catch (e: InstantiationException) {
            e.printStackTrace()
            false
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            false
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            false
        }
    }

    @get:Throws(SQLException::class)
    val connection: Connection
        get() = DriverManager.getConnection(dataBaseUrl,
                dataBaseUser, dataBasePassword)
}
