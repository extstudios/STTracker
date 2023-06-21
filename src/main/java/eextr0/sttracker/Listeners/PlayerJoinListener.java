package eextr0.sttracker.Listeners;

import eextr0.sttracker.STTracker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDateTime;

public class PlayerJoinListener implements Listener {

    private final STTracker plugin;

    public PlayerJoinListener (STTracker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("sttracker.timer")) {
            plugin.playerLoginTimes.put(player.getUniqueId(), LocalDateTime.now());
            plugin.staffList.put(player.getName(), player.getUniqueId());
            plugin.setStaffList(plugin.staffList);
            System.out.println(plugin.staffList);
        }
    }
}
