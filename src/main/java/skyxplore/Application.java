package skyxplore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@SpringBootApplication
@Slf4j
public class Application {
    private static final String DB_PASSWORD_KEY = "spring.datasource.password";
    private static final String DB_USERNAME_KEY = "spring.datasource.username";
    private static final String DB_NAME_KEY = "database.name";

    private final Properties properties = new Properties();

    public static void main(String[] args) {
        new Application().init();
        SpringApplication.run(Application.class, args);
    }

    private void init() {
        loadProperties();
        createDb();
    }

    private void loadProperties() {
        log.info("Loading properties...");
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDb() {
        try {
            log.info("Creating database...");
            String userName = properties.getProperty(DB_USERNAME_KEY, "root");
            String password = properties.getProperty(DB_PASSWORD_KEY, "");
            String databaseName = properties.getProperty(DB_NAME_KEY);
            String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            log.info("Username: {}, Password: ***, sql: {}", userName, sql);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", userName, password);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
