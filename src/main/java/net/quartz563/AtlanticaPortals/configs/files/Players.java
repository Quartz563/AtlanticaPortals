package net.quartz563.AtlanticaPortals.configs.files;

import net.quartz563.AtlanticaPortals.Main;
import net.quartz563.AtlanticaPortals.configs.AbstractFile;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Players extends AbstractFile {

    public Players() {
        super(Main.getInstance(), "players,yml");
    }

    public void setNewSavedLocation(String locationName, Location location, String playerUUID){
        config.set("Players." + playerUUID + ".Saved-Location." + locationName, location);
        saveConfig();
    }

    public Location getSavedLocation(String locationName, String playerUUID){
       return config.getLocation("Players." + playerUUID + ".Saved-Locations." + locationName);
    }

    public ArrayList<String> getLocationKeys(String playerUUID){
        return new ArrayList<>(config.getConfigurationSection("Players." + playerUUID + ".Saved-Locations").getKeys(false));
    }


}
