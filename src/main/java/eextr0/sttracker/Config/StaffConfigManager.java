package eextr0.sttracker.Config;

import eextr0.sttracker.STTracker;
import org.bukkit.configuration.ConfigurationSection;

import java.io.IOException;
import java.lang.module.Configuration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaffConfigManager extends ConfigManager{

    private HashMap<String, UUID> staffList;

    public StaffConfigManager(STTracker plugin) {
        super(plugin);
        if(plugin.staffListFile != null && plugin.staffListConfigStream != null) {
            updateConfig(plugin.staffListFile, plugin.staffListConfigStream);
        }
        createConfig("staffList.yml");

        load();
    }

    public void load() {
        staffList = getMap("staffList");
        plugin.loadStaffList(staffList);
    }

    public HashMap<String, UUID> getMap(String path) {
        ConfigurationSection section = config.getConfigurationSection(path);
        HashMap<String, UUID> hashMap = new HashMap<>();
        if (section != null) {
            for (String key : section.getKeys(false)) {
                hashMap.put(key, (UUID) section.get(key));
            }
        }
        return hashMap;
    }

    public void setHashMap(String path, HashMap<String,UUID> hashMap) {
        ConfigurationSection section = config.createSection(path);
        for(Map.Entry<String, UUID> entry : hashMap.entrySet()) {
            section.set(entry.getKey(), entry.getValue());
        }
        saveConfig();
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
