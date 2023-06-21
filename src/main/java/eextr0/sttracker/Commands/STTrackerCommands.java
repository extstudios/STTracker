package eextr0.sttracker.Commands;

import eextr0.sttracker.STTracker;
import eextr0.sttracker.Time.DateTimeFormatter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class STTrackerCommands implements CommandExecutor {

    private final STTracker plugin;
    private final DateTimeFormatter dateTimeFormatter;

    public STTrackerCommands(STTracker plugin, DateTimeFormatter dateTimeFormatter) {
        this.plugin = plugin;
        this.dateTimeFormatter = dateTimeFormatter;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 0) {
            commandSender.sendMessage("Plugin:STTracker");
            commandSender.sendMessage("CommandList:");
            commandSender.sendMessage("/st check <player>");

            return true;
        }

        switch (args[0]) {
            case "check" -> {
                if(commandSender instanceof Player && commandSender.hasPermission("sttracker.check")) {
                    String playerName = args[1];
                    Player player = (Player) commandSender;
                    UUID playerUUID = null;
                    if(plugin.staffList.containsKey(playerName)) {
                        playerUUID = plugin.staffList.get(playerName);
                    } else {
                        player.sendMessage(playerName + " has never logged on");
                    }
                    try (PreparedStatement statement = plugin.getConnection().prepareStatement(
                            "SELECT login_time, logout_time FROM playtimes WHERE player_uuid = ?"
                    )) {
                        statement.setString(1, playerUUID.toString());
                        ResultSet resultSet = statement.executeQuery();
                        Duration totalPlayTime = Duration.ZERO;

                        while (resultSet.next()) {
                            LocalDateTime loginTime = LocalDateTime.parse(resultSet.getString("login_time"));
                            LocalDateTime logoutTime = LocalDateTime.parse(resultSet.getString("logout_time"));
                            totalPlayTime =totalPlayTime.plus(Duration.between(loginTime, logoutTime));
                        }
                        player.sendMessage(Bukkit.getOfflinePlayer(playerUUID).getName() + "'s total playtime: " + dateTimeFormatter.formatDuration(totalPlayTime));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return true;

                }
            }
            case "reload" -> {
                plugin.onDisable();
                plugin.onEnable();
            }
        }


        return true;
    }
}
