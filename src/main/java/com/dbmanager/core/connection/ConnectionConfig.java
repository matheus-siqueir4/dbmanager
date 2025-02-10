package com.dbmanager.core.connection;

import com.dbmanager.core.connection.enums.DatabaseType;

public class ConnectionConfig {
    private final DatabaseType databaseType;
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String database;
    private final String connectionUrl;

    private ConnectionConfig(Builder builder) {
        this.databaseType = builder.databaseType;
        this.host = builder.host;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.database = builder.database;
        this.connectionUrl = databaseType.buildConnectionUrl(host, port, database);
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public static class Builder {
        private DatabaseType databaseType;
        private String host = "localhost";
        private int port = 5432;
        private String username;
        private String password;
        private String database;

        public Builder withDatabaseType(DatabaseType databaseType) {
            this.databaseType = databaseType;
            return this;
        }

        public Builder withHost(String host) {
            this.host = host;
            return this;
        }

        public Builder withPort(int port) {
            this.port = port;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withDatabase(String database) {
            this.database = database;
            return this;
        }

        public ConnectionConfig build() {
            return new ConnectionConfig(this);
        }
    }
}
