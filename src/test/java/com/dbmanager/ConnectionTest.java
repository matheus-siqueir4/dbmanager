package com.dbmanager;

import java.sql.Connection;
import java.sql.SQLException;

import com.dbmanager.core.connection.ConnectionConfig;
import com.dbmanager.core.connection.ConnectionFactory;
import com.dbmanager.core.connection.enums.DatabaseType;

public class ConnectionTest {
    public static void main(String[] args) {
        try {
            // Criar configuração
            ConnectionConfig config = new ConnectionConfig.Builder()
                .withDatabaseType(DatabaseType.POSTGRESQL)
                .withHost("localhost")
                .withPort(5432)
                .withDatabase("mat")
                .withUsername("postgres")
                .withPassword("teste123")
                .build();

            // Criar factory
            ConnectionFactory factory = new ConnectionFactory();

            // Tentar conexão
            try (Connection conn = factory.getConnection(config)) {
                System.out.println("Conexão bem sucedida!");
                
                // Testar se conexão é válida
                if (conn.isValid(5)) {
                    System.out.println("Conexão está válida!");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
