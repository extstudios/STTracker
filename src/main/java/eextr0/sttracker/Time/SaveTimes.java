package eextr0.sttracker.Time;

import eextr0.sttracker.STTracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class SaveTimes {
    private final STTracker plugin;
    private final DateTimeFormatter dateTimeFormatter;

    public SaveTimes(STTracker plugin, DateTimeFormatter dateTimeFormatter) {
        this.plugin = plugin;
        this.dateTimeFormatter = dateTimeFormatter;
    }
    public void savePlayerTimes() {
        for(Map.Entry<UUID, LocalDateTime> entry : plugin.playerLoginTimes.entrySet()) {
            UUID playerUUID = entry.getKey();
            LocalDateTime loginTime = entry.getValue();
            LocalDateTime logoutTime = LocalDateTime.now();

            savePlayTime(playerUUID, loginTime, logoutTime);
        }

    }

    public void savePlayTime(UUID playerUUID, LocalDateTime loginTime, LocalDateTime logoutTime) {
        try (PreparedStatement statement = plugin.getConnection().prepareStatement(
                "INSERT INTO playtimes (player_uuid, login_time, logout_time) VALUES (?, ?, ?)"
        )) {
            statement.setString(1, playerUUID.toString());
            statement.setString(2, dateTimeFormatter.formatDateTime(loginTime));
            statement.setString(3, dateTimeFormatter.formatDateTime(logoutTime));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
