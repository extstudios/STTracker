package eextr0.sttracker.DatabaseManagers;

import eextr0.sttracker.STTracker;
import eextr0.sttracker.Time.DateTimeFormatter;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DatabaseCleaner {
    private static final int DB_FILE_LIFETIME = 35;
    private final STTracker plugin;

    private LocalDateTime lastCleanupTime;

    public DatabaseCleaner(STTracker plugin) {
        this.plugin = plugin;
        this.lastCleanupTime = LocalDateTime.now();
    }
    public void cleanupDatabase() {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration elapsedDuration = Duration.between(lastCleanupTime, currentTime);
        long elapsedDays = elapsedDuration.toDays();

        if (elapsedDays >= DB_FILE_LIFETIME) {
            try(Statement statement = plugin.getConnection().createStatement()) {
                statement.executeUpdate("DELETE FROM playtimes");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            lastCleanupTime = currentTime.truncatedTo(ChronoUnit.DAYS);
        }
    }
}
