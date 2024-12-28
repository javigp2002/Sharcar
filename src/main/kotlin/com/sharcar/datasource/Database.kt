package com.sharcar.datasource

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

object DatabaseConnection {
    private var dataSource: HikariDataSource

    init {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:mariadb://localhost:3306/sharcardb"
            username = "sharcar"
            password = "sharcar"
            driverClassName = "org.mariadb.jdbc.Driver"
            maximumPoolSize = 10
            minimumIdle = 2
            idleTimeout = 10000
            connectionTimeout = 30000
            maxLifetime = 1800000
        }

        dataSource = HikariDataSource(config)
    }

    fun executeQuery(query: String, parameters: List<Any>): ResultSet {
        return try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(query).use { statement ->
                    parameters.forEachIndexed { index, param ->
                        statement.setObject(index + 1, param)
                    }
                    statement.executeQuery()
                }
            }
        } catch (e: SQLException) {
            throw RuntimeException("Error executing query", e)
        }
    }

    fun executeInsert(query: String, parameters: List<Any>): Int {
        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use { statement ->
                    parameters.forEachIndexed { index, param ->
                        statement.setObject(index + 1, param)
                    }
                    statement.executeUpdate()
                    val generatedKeys = statement.generatedKeys
                    generatedKeys.next()
                    return generatedKeys.getInt(1)
                }
            }
        } catch (e: SQLException) {
            throw RuntimeException("Error executing query", e)
        }
    }

    fun executeNoGeneratedKey(query: String, parameters: List<Any>): Int {
        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(query).use { statement ->
                    parameters.forEachIndexed { index, param ->
                        statement.setObject(index + 1, param)
                    }
                    return statement.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw RuntimeException("Error executing query", e)
        }
    }
}