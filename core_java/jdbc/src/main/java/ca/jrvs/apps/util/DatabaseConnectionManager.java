package ca.jrvs.apps.util;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
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

    public DatabaseConnectionManager() {
        this.properties = new Properties();
        this.loadProperties();
        this.url = "jdbc:postgresql://" + this.properties.getProperty("host") + "/" + this.properties.getProperty("database");
    }

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(this.url, this.properties);
        } catch (SQLException e) {
            this.logger.error("ERROR: failed to establish connection to database");
            throw new SQLException();
        }
    }

    private void loadProperties() {
        try {
            String path = Paths.get("db.properties").toString();
            this.properties.load(this.getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            logger.error("ERROR: failed to load database properties from properties file");
        }
    }
}
