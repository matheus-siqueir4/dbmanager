package com.dbmanager.core.connection.enums;

public enum DatabaseType {
    
    POSTGRESQL("jdbc:postgresql://", "org.postgresql.ds.PGSimpleDataSource") {    
        @Override
        public String buildConnectionUrl(String host, int port, String database) {
            String baseUrl = String.format("%s%s:%d/%s", getUrlPrefix(), host, port, database);
            return baseUrl;
        }
    },

    MYSQL("jdbc:mysql://", null) {
        @Override
        public String buildConnectionUrl(String host, int port, String database) {
            String baseUrl = String.format("%s%s:%d/%s", getUrlPrefix(), host, port, database);
            return baseUrl;
        }
    },
    
    ORACLE("jdbc:oracle:thin:@", "oracle.jdbc.pool.OracleDataSource") {
        @Override
        public String buildConnectionUrl(String host, int port, String database) {
            String baseUrl = String.format("%s//%s:%d/%s", getUrlPrefix(), host, port, database);
            return baseUrl;
        }
    }, 

    SQLSERVER("jdbc:sqlserver://", "com.microsoft.sqlserver.jdbc.SQLServerDataSource") {

        @Override
        public String buildConnectionUrl(String host, int port, String database) {
            String baseUrl = String.format("%s//%s:%d;databaseName=%s", getUrlPrefix(), host, port, database);
            return baseUrl;
        }
    };

    private final String urlPrefix;
    private final String dataSourceClassName;

    DatabaseType(String urlPrefix, String dataSourceClassName) {
        this.urlPrefix = urlPrefix;
        this.dataSourceClassName = dataSourceClassName;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public String getDataSourceClassName() {
        return dataSourceClassName;
    }

    public abstract String buildConnectionUrl(String host, int port, String database);
    
    
}
