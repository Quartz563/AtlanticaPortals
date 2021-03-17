package net.quartz563.AtlanticaPortals;

import net.quartz563.AtlanticaPortals.command.PortalsGroupCommand;
import net.quartz563.AtlanticaPortals.settings.Settings;
import org.bukkit.*;
import org.bukkit.permissions.Permission;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;

import java.util.*;

public class AtlanticaPortals extends SimplePlugin {

    /*todo list
       1) saved location (finished)
       2) personal vault (is this really required with the enderchest?)
       3) commands (done? I think?)
       4) player teleportation
       5) server locations (Finished, I believe.)
     */


    @Override
    protected void onPluginStart() {
        registerCommands("portals|por", new PortalsGroupCommand());
    }


    @Override
    public List<Class<? extends YamlStaticConfig>> getSettings() {
        return Arrays.asList(Settings.class);
    }




}

