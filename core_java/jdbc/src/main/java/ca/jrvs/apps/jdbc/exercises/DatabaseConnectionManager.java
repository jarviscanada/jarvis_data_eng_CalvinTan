package ca.jrvs.apps.jdbc.exercises;

import ca.jrvs.apps.util.LoggerUtil;
import org.slf4j.Logger;

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

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(this.url.toString(), this.properties);
        } catch (SQLException e) {
            this.logger.error("ERROR: failed to establish connection");
        }
        throw new RuntimeException();
    }
}
