package eextr0.sttracker.DatabaseManagers;

import eextr0.sttracker.STTracker;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {

    private final STTracker plugin;

    public CreateTables(STTracker plugin) {
        this.plugin = plugin;
    }
    public void createTables() {
        try(Statement statement = plugin.getConnection().createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS playtimes (id INTEGER PRIMARY KEY AUTOINCREMENT, player_uuid TEXT, login_time TEXT, logout_Time TEXT)"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
