package com.dbmanager.core.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionFactory {
    private final Map<String, HikariDataSource> datasource;

    public ConnectionFactory() {
        this.datasource = new HashMap<>();
    }

    public Connection getConnection(ConnectionConfig config) throws SQLException {
        String key = String.format("%s_%s", config.getDatabaseType().name(), config.getDatabase());

        if (!datasource.containsKey(key)) {
            datasource.put(key, createDataSource(config));
        }

        return datasource.get(key).getConnection();
    }

    private HikariDataSource createDataSource(ConnectionConfig config) {
        HikariConfig hikariConfig = createHikariConfig(config);
        hikariConfig.setMaximumPoolSize(5); 
        hikariConfig.setMinimumIdle(1);

        return new HikariDataSource(hikariConfig);   

    }

    private HikariConfig createHikariConfig(ConnectionConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        boolean isNull = config.getDatabaseType().getDataSourceClassName() == null;
        
        if (isNull) {
            hikariConfig.setJdbcUrl(config.getConnectionUrl());
            hikariConfig.setUsername(config.getUsername());
            hikariConfig.setPassword(config.getPassword());

            return hikariConfig;
        }

        hikariConfig.setDataSourceClassName(config.getDatabaseType().getDataSourceClassName());
        hikariConfig.addDataSourceProperty("serverName", config.getHost());
        hikariConfig.addDataSourceProperty("portNumber", config.getPort());
        hikariConfig.addDataSourceProperty("databaseName", config.getDatabase());
        hikariConfig.addDataSourceProperty("user", config.getUsername());
        hikariConfig.addDataSourceProperty("password", config.getPassword());

        return hikariConfig;
    }
}
