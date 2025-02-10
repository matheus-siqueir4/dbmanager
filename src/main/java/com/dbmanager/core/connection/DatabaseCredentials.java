package com.dbmanager.core.connection;

import com.dbmanager.core.connection.enums.DatabaseType;

/**
* Represents the credentials and connection information required to establish a database connection.
* This class uses the Builder pattern to create immutable instances with all necessary connection details.
*/
public class DatabaseCredentials {
    private final DatabaseType databaseType;
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String database;
    private final String connectionUrl;
 
    /**
     * Private constructor used by the Builder to create a new DatabaseCredentials instance.
     *
     * @param builder The Builder instance containing all the connection parameters
     */
    private DatabaseCredentials(Builder builder) {
        this.databaseType = builder.databaseType;
        this.host = builder.host;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.database = builder.database;
        this.connectionUrl = databaseType.buildConnectionUrl(host, port, database);
    }
 
    /**
     * @return The type of database (PostgreSQL, MySQL, etc.)
     */
    public DatabaseType getDatabaseType() {
        return databaseType;
    }
 
    /**
     * @return The host name or IP address of the database server
     */
    public String getHost() {
        return host;
    }
 
    /**
     * @return The port number where the database server is listening
     */
    public int getPort() {
        return port;
    }
 
    /**
     * @return The username for database authentication
     */
    public String getUsername() {
        return username;
    }
 
    /**
     * @return The password for database authentication
     */
    public String getPassword() {
        return password;
    }
 
    /**
     * @return The name of the database to connect to
     */
    public String getDatabase() {
        return database;
    }
 
    /**
     * @return The complete connection URL built using the database type and connection parameters
     */
    public String getConnectionUrl() {
        return connectionUrl;
    }
 
    /**
     * Builder class for creating DatabaseCredentials instances.
     * Provides a fluent interface for setting connection parameters.
     */
    public static class Builder {
        private DatabaseType databaseType;
        private String host = "localhost";
        private int port = 5432;
        private String username;
        private String password;
        private String database;
 
        /**
         * Sets the database type (PostgreSQL, MySQL, etc.).
         *
         * @param databaseType The type of database to connect to
         * @return This Builder instance
         */
        public Builder withDatabaseType(DatabaseType databaseType) {
            this.databaseType = databaseType;
            return this;
        }
 
        /**
         * Sets the database server host.
         * Defaults to "localhost" if not specified.
         *
         * @param host The hostname or IP address
         * @return This Builder instance
         */
        public Builder withHost(String host) {
            this.host = host;
            return this;
        }
 
        /**
         * Sets the database server port.
         * Defaults to 5432 if not specified.
         *
         * @param port The port number
         * @return This Builder instance
         */
        public Builder withPort(int port) {
            this.port = port;
            return this;
        }
 
        /**
         * Sets the username for database authentication.
         *
         * @param username The database username
         * @return This Builder instance
         */
        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }
 
        /**
         * Sets the password for database authentication.
         *
         * @param password The database password
         * @return This Builder instance
         */
        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }
 
        /**
         * Sets the database name to connect to.
         *
         * @param database The name of the database
         * @return This Builder instance
         */
        public Builder withDatabase(String database) {
            this.database = database;
            return this;
        }
 
        /**
         * Builds a new DatabaseCredentials instance with the current builder settings.
         *
         * @return A new DatabaseCredentials instance
         */
        public DatabaseCredentials build() {
            return new DatabaseCredentials(this);
        }
    }
}