package database;

import database.connection.DatabaseConnection;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import utility.FrameworkLogger;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;

public class DatabaseConnectionTest {

    @Test
    @Owner("Ayanda Khaka")
    @Story("Database connection")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test connection database")
    public void verifyDatabaseConnection() {

        try (Connection connection =
                 DatabaseConnection.getConnection()) {

            Assert.assertNotNull(
                connection,
                "Database connection should not be null"
            );

            Assert.assertFalse(
                connection.isClosed(),
                "Database connection should be open"
            );
            
            FrameworkLogger.info("Database connection successful!");

        } catch (Exception e) {

            Assert.fail(
                "Database connection failed: "
                + e.getMessage()
            );
        }
    }
}