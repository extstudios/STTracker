package eextr0.sttracker;

import eextr0.sttracker.Commands.STTrackerCommands;
import eextr0.sttracker.Config.StaffConfigManager;
import eextr0.sttracker.DatabaseManagers.CreateTables;
import eextr0.sttracker.DatabaseManagers.DatabaseCleaner;
import eextr0.sttracker.DatabaseManagers.DatabaseConnectors;
import eextr0.sttracker.Listeners.PlayerJoinListener;
import eextr0.sttracker.Listeners.PlayerQuitListener;
import eextr0.sttracker.Time.DateTimeFormatter;
import eextr0.sttracker.Time.SaveTimes;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class STTracker extends JavaPlugin {

    public Map<UUID, LocalDateTime> playerLoginTimes;
    public File staffListFile;
    public InputStream staffListConfigStream;
    private Connection connection;
    private StaffConfigManager staffConfigManager;
    public HashMap<String, UUID> staffList;

    public Connection getConnection() {return connection;}
    public Connection setConnection(Connection connection) {this.connection = connection;
        return connection;}

    public void setStaffList(HashMap<String,UUID> hashMap) {
        staffConfigManager.setHashMap("staffList", hashMap);
    }
    public void loadStaffList(HashMap<String, UUID> hashmap) {
        this.staffList = hashmap;
    }
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        staffListFile = new File(getDataFolder(), "staffList.yml");
        staffListConfigStream = getResource("staffList.yml");
        staffConfigManager = new StaffConfigManager(this);

        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter();
        SaveTimes saveTimes = new SaveTimes(this, dateTimeFormatter);
        CreateTables createTables = new CreateTables(this);

        DatabaseConnectors databaseConnectors = new DatabaseConnectors(this);
        DatabaseCleaner databaseCleaner = new DatabaseCleaner(this);
        playerLoginTimes = new HashMap<>();
        databaseConnectors.connectToDatabase();
        createTables.createTables();
        databaseCleaner.cleanupDatabase();

        getCommand("st").setExecutor(new STTrackerCommands(this, dateTimeFormatter));
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this, saveTimes), this);
    }

    @Override
    public void onDisable() {

        DatabaseConnectors databaseConnectors = new DatabaseConnectors(this);
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter();
        SaveTimes saveTimes = new SaveTimes(this, dateTimeFormatter);
        databaseConnectors.disconnectFromDatabase();
        saveTimes.savePlayerTimes();
    }
}
