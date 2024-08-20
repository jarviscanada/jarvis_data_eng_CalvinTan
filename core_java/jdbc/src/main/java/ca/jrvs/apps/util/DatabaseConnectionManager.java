package ca.jrvs.apps.util;

import org.slf4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {

    private String url;
    private final Properties properties;
    private final Logger logger = LoggerUtil.getLogger();

    public DatabaseConnectionManager(String host, String databaseName, String username, String password) {
        this.url = "jdbc:postgresql://" + host + "/" + databaseName;
        this.properties = new Properties();
        this.properties.setProperty("user", username);
        this.properties.setProperty("password", password);
    }

    public DatabaseConnectionManager(String host, String databaseName) {
        this.url = "jdbc:postgresql://" + host + "/" + databaseName;
        this.properties = new Properties();
        this.loadProperties();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(this.url, this.properties);
        } catch (SQLException e) {
            this.logger.error("ERROR: failed to establish connection");
        }
        throw new RuntimeException();
    }

    private void loadProperties() {
        try {
            this.properties.load(new FileInputStream("db.properties"));
        } catch (IOException e) {
            logger.error("ERROR: failed to load user and password from properties file");
        }
    }
}
