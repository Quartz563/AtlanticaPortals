package net.quartz563.AtlanticaPortals.configs;

import java.io.File;
import java.io.IOException;

import net.quartz563.AtlanticaPortals.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class AbstractFile {

    protected Main wd;
    private File file;
    public FileConfiguration config;

    public AbstractFile(Main wd, String fileName) {

        this.wd = wd;
        this.file = new File(wd.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getServer().getPluginManager().callEvent(new FileCreationEvent(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }}

    public void reloadConfig() {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }
}
