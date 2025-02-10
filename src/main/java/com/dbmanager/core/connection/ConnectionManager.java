package com.dbmanager.core.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
* Manages database connection pools using HikariCP.
* This utility class maintains separate connection pools for different databases,
* with each pool containing a single connection for optimal resource usage in a desktop environment.
*/
public class ConnectionManager {
    private static final Map<String, HikariDataSource> poolmap = new HashMap<>();
 
    /**
     * Private constructor to prevent instantiation.
     * This class should only be used through its static methods.
     *
     * @throws UnsupportedOperationException if someone tries to instantiate this class
     */
    private ConnectionManager() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
 
    /**
     * Gets a database connection from the appropriate connection pool.
     * If a pool doesn't exist for the given credentials, it creates one.
     *
     * @param dbCredentials The database credentials to connect with
     * @return A connection from the pool
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection(DatabaseCredentials dbCredentials) throws SQLException {
        String key = generateKey(dbCredentials.getDatabaseType().name(), dbCredentials.getDatabase());

        if (!poolmap.containsKey(key)) {
            poolmap.put(key, createDataSource(dbCredentials));
        }

        return poolmap.get(key).getConnection();
    }
 
    /**
     * Closes and removes a specific connection pool.
     *
     * @param dbCredentials The database credentials associated with the pool to close
     */
    public static void closePool(DatabaseCredentials dbCredentials) {
        String key = generateKey(dbCredentials.getDatabaseType().name(), dbCredentials.getDatabase());
        HikariDataSource dataSource = poolmap.remove(key);

        if (dataSource != null) {
            dataSource.close();
        }
    }
 
    /**
     * Closes all connection pools and clears the pool map.
     * Should be called when shutting down the application.
     */
    public static void closeAllPools() {
        poolmap.values().forEach(HikariDataSource::close);
        poolmap.clear();
    }
 
    /**
     * Creates a new HikariCP data source with the given credentials.
     * Configures the pool to maintain a single connection.
     *
     * @param dbCredentials The database credentials to configure the data source
     * @return A configured HikariDataSource
     */
    private static HikariDataSource createDataSource(DatabaseCredentials dbCredentials) {
        HikariConfig poolConfig = createPoolConfig(dbCredentials);
        poolConfig.setMaximumPoolSize(1);

        return new HikariDataSource(poolConfig);
    }
 
    /**
     * Creates a HikariCP configuration based on the database credentials.
     * Handles both JDBC URL and DataSource-based configurations.
     *
     * @param dbCredentials The database credentials to use for configuration
     * @return A configured HikariConfig object
     */
    private static HikariConfig createPoolConfig(DatabaseCredentials dbCredentials) {
        HikariConfig poolConfig = new HikariConfig();
        boolean isDataSourceClassNameNull = dbCredentials.getDatabaseType().getDataSourceClassName() == null;
        
        if (isDataSourceClassNameNull) {
            poolConfig.setJdbcUrl(dbCredentials.getConnectionUrl());
            poolConfig.setUsername(dbCredentials.getUsername());
            poolConfig.setPassword(dbCredentials.getPassword());

            return poolConfig;
        }
 
        poolConfig.setDataSourceClassName(dbCredentials.getDatabaseType().getDataSourceClassName());
        poolConfig.addDataSourceProperty("serverName", dbCredentials.getHost());
        poolConfig.addDataSourceProperty("portNumber", dbCredentials.getPort());
        poolConfig.addDataSourceProperty("databaseName", dbCredentials.getDatabase());
        poolConfig.addDataSourceProperty("user", dbCredentials.getUsername());
        poolConfig.addDataSourceProperty("password", dbCredentials.getPassword());
        
        return poolConfig;
    }
 
    /**
     * Generates a unique key for identifying connection pools.
     * The key is a combination of database type and database name.
     *
     * @param dbType The type of the database (e.g., POSTGRESQL, MYSQL)
     * @param dbName The name of the database
     * @return A unique key string
     */
    private static String generateKey(String dbType, String dbName) {
        return String.format("%s_%s", dbType, dbName);
    }
}