package net.quartz563.AtlanticaPortals.configs.files;

import net.quartz563.AtlanticaPortals.Main;
import net.quartz563.AtlanticaPortals.configs.AbstractFile;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Objects;

public class Config extends AbstractFile {

    public Config() {
        super(Main.getInstance(), "config.yml");
    }

    public void setSpawn(Location spawn){
        config.set("General.Spawn", spawn.toString());
    }

    public Location getSpawn(){
        return config.getLocation("General.Spawn");
    }

    public int getRandomTravelValue(){
        return config.getInt("General.Random-Travel-Value");
    }

    public int getServerLocationTotal(){
        return config.getInt("General.Server-Locations.Total");
    }

    public String getNextLocation(int counter){
        ArrayList<String> locations = new ArrayList<>(Objects.requireNonNull(config.getConfigurationSection("General.Server-Locations")).getKeys(false));
        return locations.get(counter);
    }

    public Location getServerLocation(String locationName){
        return config.getLocation("General.Server-Locations." + locationName);
    }

    public Material getServerLocationMaterial(String locationName){
        return Material.valueOf(config.getString("General.Server-Location." + locationName + ".Material"));
    }

}
