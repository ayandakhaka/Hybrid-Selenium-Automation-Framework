package database.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConfig {

	private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static Connection getConnection() throws SQLException {

        String url = getRequiredValue("db.url");
        String username = getRequiredValue("db.username");
        String password = getRequiredValue("db.password");

        return DriverManager.getConnection(
                url, username, password
        );
    }

    private static String getRequiredValue(String key) {
        String value = dotenv.get(key);

        if (value == null || value.isBlank()) {
            value = System.getenv(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required environment variable: " + key
            );
        }

        return value;
    }
}