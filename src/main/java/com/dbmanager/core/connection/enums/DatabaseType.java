    package com.dbmanager.core.connection.enums;

/**
 * Enum representing supported database types with their specific connection configurations.
 * Each database type contains the necessary information to build connection URLs and manage data sources.
 */
public enum DatabaseType {
    
    /**
     * PostgreSQL database configuration.
     * Uses the standard PostgreSQL JDBC URL format: jdbc:postgresql://host:port/database
     */
    POSTGRESQL("jdbc:postgresql://", "org.postgresql.ds.PGSimpleDataSource") {    
        @Override
        public String buildConnectionUrl(String host, int port, String database) {
            String baseUrl = String.format("%s%s:%d/%s", getUrlPrefix(), host, port, database);
            return baseUrl;
        }
    },


    /**
     * MySQL database configuration.
     * Uses the standard MySQL JDBC URL format: jdbc:mysql://host:port/database
     * Note: DataSource class name is null as MySQL preferably uses JDBC URL connection
     */
    MYSQL("jdbc:mysql://", null) {
        @Override
        public String buildConnectionUrl(String host, int port, String database) {
            String baseUrl = String.format("%s%s:%d/%s", getUrlPrefix(), host, port, database);
            return baseUrl;
        }
    },
    
    /**
     * Oracle database configuration.
     * Uses the Oracle thin driver URL format: jdbc:oracle:thin:@//host:port/database
     */
    ORACLE("jdbc:oracle:thin:@", "oracle.jdbc.pool.OracleDataSource") {
        @Override
        public String buildConnectionUrl(String host, int port, String database) {
            String baseUrl = String.format("%s//%s:%d/%s", getUrlPrefix(), host, port, database);
            return baseUrl;
        }
    }, 

    /**
     * SQL Server database configuration.
     * Uses SQL Server URL format with databaseName parameter: jdbc:sqlserver://host:port;databaseName=database
     */
    SQLSERVER("jdbc:sqlserver://", "com.microsoft.sqlserver.jdbc.SQLServerDataSource") {

        @Override
        public String buildConnectionUrl(String host, int port, String database) {
            String baseUrl = String.format("%s//%s:%d;databaseName=%s", getUrlPrefix(), host, port, database);
            return baseUrl;
        }
    };

    private final String urlPrefix;
    private final String dataSourceClassName;

    /**
     * Constructor for DatabaseType enum.
     *
     * @param urlPrefix           The JDBC URL prefix specific to each database type
     * @param dataSourceClassName The full class name of the DataSource implementation,
     *                           can be null if the database preferably uses JDBC URL connection
     */
    DatabaseType(String urlPrefix, String dataSourceClassName) {
        this.urlPrefix = urlPrefix;
        this.dataSourceClassName = dataSourceClassName;
    }

     /**
     * Gets the JDBC URL prefix for the database type.
     *
     * @return The JDBC URL prefix as a String
     */
    public String getUrlPrefix() {
        return urlPrefix;
    }

    /**
     * Gets the DataSource class name for the database type.
     *
     * @return The DataSource class name as a String, can be null for databases that prefer JDBC URL connection
     */
    public String getDataSourceClassName() {
        return dataSourceClassName;
    }

    /**
     * Builds the complete connection URL for the database.
     *
     * @param host     The hostname or IP address of the database server
     * @param port     The port number where the database server is listening
     * @param database The name of the database to connect to
     * @return A formatted connection URL string specific to the database type
     */
    public abstract String buildConnectionUrl(String host, int port, String database);
    
}
