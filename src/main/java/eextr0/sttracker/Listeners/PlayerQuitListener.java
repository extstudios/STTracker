package eextr0.sttracker.Listeners;

import eextr0.sttracker.STTracker;
import eextr0.sttracker.Time.DateTimeFormatter;
import eextr0.sttracker.Time.SaveTimes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final STTracker plugin;
    private final SaveTimes saveTimes;

    public PlayerQuitListener (STTracker plugin, SaveTimes saveTimes) {
        this.plugin = plugin;
        this.saveTimes = saveTimes;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("sttracker.timer")) {
            LocalDateTime loginTime = plugin.playerLoginTimes.remove(player.getUniqueId());
            LocalDateTime logoutTime = LocalDateTime.now();
            saveTimes.savePlayTime(player.getUniqueId(), loginTime, logoutTime);
        }
    }

}
