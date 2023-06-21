package eextr0.sttracker.DatabaseManagers;

import eextr0.sttracker.STTracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectors {

    private Connection connection;
    private final STTracker plugin;
    private static final String DB_FILE_PATH = "timers.db";

    public DatabaseConnectors(STTracker plugin) {
        this.plugin = plugin;
    }

    public void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE_PATH);
            plugin.setConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnectFromDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                plugin.setConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reloadDatabase() {
        disconnectFromDatabase();
        connectToDatabase();
    }
}
