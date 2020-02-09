package me.Quartz563.AtlanticaPortals;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.EndGateway;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;

import static org.bukkit.ChatColor.*;

public class Main extends JavaPlugin implements Listener {

    /*todo list
       1) saved location (finished)
       2) personal vault (is this really required with the enderchest?)
       3) commands (done? I think?)
       4) player teleportation
       5) server locations (Finished, I believe.)
     */

    private Location previousLocation = null;
    static String locationName = null;
    private File playerDataFile = null;
    private FileConfiguration playerData = null;
    public Permission adminPermission = new Permission("portals.admin");
    public Permission locationPermission = new Permission("portals.saveLocation");
    public Permission serverLocationPermission = new Permission("portals.serverLocations");
    public Permission playerTeleportPermission = new Permission("portals.playerteleport");
    public Permission portalUsagePermission = new Permission("portals.use");
    public Permission enderChestPermission = new Permission("portals.EnderChest");
    public Permission personalVault = new Permission("portals.PersonalVault");
    public Permission randomTeleport = new Permission("portals.RandomTeleport");
    public Permission spawnTeleport = new Permission("portals.SpawnTeleport");
    public Permission bedTeleport = new Permission("portals.BedTeleport");
    public Permission returnTeleport = new Permission("portals.ReturnTeleport");

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getConfig().options().copyDefaults(true);
        this.getCommand("portals");
        reloadPlayerDataFile();
        savePlayerData();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        savePlayerData();
    }

    public void reloadPlayerDataFile() {
        if (this.playerData == null) {
            this.playerDataFile = new File(getDataFolder(), "players.yml");
        }
        this.playerData = YamlConfiguration.loadConfiguration(this.playerDataFile);
        Reader defConfigStream = null;
        try {
            defConfigStream = new InputStreamReader(getResource("players.yml"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);

        }
    }

    public FileConfiguration getPlayerData() {
        if (this.playerData == null) {
            reloadPlayerDataFile();
        }
        return this.playerData;
    }

    public void savePlayerData() {
        if ((this.playerData == null) || (this.playerDataFile == null)) {
            reloadPlayerDataFile();
        }
        try {
            getPlayerData().save(this.playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String playerToUUID(Player player) {
        return player.getUniqueId().toString();
    }

    public void makePortal(Location farPortalLocation, Player player) {
        //North = -Z
        //South = +Z
        //West = -X
        //East = +X
        Location loc = Objects.requireNonNull(player.getPlayer()).getLocation();

        PortalEast(farPortalLocation, player, loc);
        PortalWest(farPortalLocation, player, loc);
        PortalNorth(farPortalLocation, player, loc);
        PortalSouth(farPortalLocation, player, loc);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_END_GATEWAY_SPAWN, 0.5F, 1F);
    }

    public void PortalSouth(Location farPortalLocation, Player player, Location loc) {
        if (player.getFacing() == BlockFace.SOUTH) {
            Block blocks[] = new Block[12];
            blocks[0] = loc.getBlock().getRelative(1, 0, 3);
            blocks[1] = loc.getBlock().getRelative(1, 1, 3);
            blocks[2] = loc.getBlock().getRelative(1, 2, 3);
            blocks[3] = loc.getBlock().getRelative(1, 3, 3);
            blocks[4] = loc.getBlock().getRelative(0, 0, 3);
            blocks[5] = loc.getBlock().getRelative(0, 1, 3);
            blocks[6] = loc.getBlock().getRelative(0, 2, 3);
            blocks[7] = loc.getBlock().getRelative(0, 3, 3);
            blocks[8] = loc.getBlock().getRelative(-1, 0, 3);
            blocks[9] = loc.getBlock().getRelative(-1, 1, 3);
            blocks[10] = loc.getBlock().getRelative(-1, 2, 3);
            blocks[11] = loc.getBlock().getRelative(-1, 3, 3);
            if (!blocks[0].getType().isAir() || !blocks[1].getType().isAir() || !blocks[2].getType().isAir() ||
                    !blocks[3].getType().isAir() || !blocks[4].getType().isAir() || !blocks[5].getType().isAir() ||
                    !blocks[6].getType().isAir() || !blocks[7].getType().isAir() || !blocks[8].getType().isAir() ||
                    !blocks[9].getType().isAir() || !blocks[10].getType().isAir() || !blocks[11].getType().isAir()) {
                player.sendMessage(RED + "You cannot make a portal here.");
            } else {
                //Left Side
                loc.getBlock().getRelative(-1, 0, 3).setType(Material.END_GATEWAY);
                setPortal(player, -1, 0, 3, farPortalLocation);
                loc.getBlock().getRelative(-1, 1, 3).setType(Material.END_GATEWAY);
                setPortal(player, -1, 1, 3, farPortalLocation);
                loc.getBlock().getRelative(-1, 2, 3).setType(Material.END_GATEWAY);
                setPortal(player, -1, 2, 3, farPortalLocation);
                loc.getBlock().getRelative(-1, 3, 3).setType(Material.END_GATEWAY);
                setPortal(player, -1, 3, 3, farPortalLocation);
                //Middle
                loc.getBlock().getRelative(0, 0, 3).setType(Material.END_GATEWAY);
                setPortal(player, 0, 0, 3, farPortalLocation);
                loc.getBlock().getRelative(0, 1, 3).setType(Material.END_GATEWAY);
                setPortal(player, 0, 1, 3, farPortalLocation);
                loc.getBlock().getRelative(0, 2, 3).setType(Material.END_GATEWAY);
                setPortal(player, 0, 2, 3, farPortalLocation);
                loc.getBlock().getRelative(0, 3, 3).setType(Material.END_GATEWAY);
                setPortal(player, 0, 3, 3, farPortalLocation);
                //Right Side
                loc.getBlock().getRelative(1, 0, 3).setType(Material.END_GATEWAY);
                setPortal(player, 1, 0, 3, farPortalLocation);
                loc.getBlock().getRelative(1, 1, 3).setType(Material.END_GATEWAY);
                setPortal(player, 1, 1, 3, farPortalLocation);
                loc.getBlock().getRelative(1, 2, 3).setType(Material.END_GATEWAY);
                setPortal(player, 1, 2, 3, farPortalLocation);
                loc.getBlock().getRelative(1, 3, 3).setType(Material.END_GATEWAY);
                setPortal(player, 1, 3, 3, farPortalLocation);

                MainTask mainTask = new MainTask(blocks, 0);
                MainTask mainTask2 = new MainTask(blocks, 1);
                MainTask mainTask3 = new MainTask(blocks, 2);
                MainTask mainTask4 = new MainTask(blocks, 3);
                MainTask mainTask5 = new MainTask(blocks, 4);
                MainTask mainTask6 = new MainTask(blocks, 5);
                MainTask mainTask7 = new MainTask(blocks, 6);
                MainTask mainTask8 = new MainTask(blocks, 7);
                MainTask mainTask9 = new MainTask(blocks, 8);
                MainTask mainTask10 = new MainTask(blocks, 9);
                MainTask mainTask11 = new MainTask(blocks, 10);
                MainTask mainTask12 = new MainTask(blocks, 11);
                mainTask.runTaskLater(this, 600L);
                mainTask2.runTaskLater(this, 600L);
                mainTask3.runTaskLater(this, 600L);
                mainTask4.runTaskLater(this, 600L);
                mainTask5.runTaskLater(this, 600L);
                mainTask6.runTaskLater(this, 600L);
                mainTask7.runTaskLater(this, 600L);
                mainTask8.runTaskLater(this, 600L);
                mainTask9.runTaskLater(this, 600L);
                mainTask10.runTaskLater(this, 600L);
                mainTask11.runTaskLater(this, 600L);
                mainTask12.runTaskLater(this, 600L);
            }
        }
    }

    public void PortalNorth(Location farPortalLocation, Player player, Location loc) {
        if (player.getFacing() == BlockFace.NORTH) {
            Block blocks[] = new Block[12];
            blocks[0] = loc.getBlock().getRelative(-1, 0, -3);
            blocks[1] = loc.getBlock().getRelative(-1, 1, -3);
            blocks[2] = loc.getBlock().getRelative(-1, 2, -3);
            blocks[3] = loc.getBlock().getRelative(-1, 3, -3);
            blocks[4] = loc.getBlock().getRelative(0, 0, -3);
            blocks[5] = loc.getBlock().getRelative(0, 1, -3);
            blocks[6] = loc.getBlock().getRelative(0, 2, -3);
            blocks[7] = loc.getBlock().getRelative(0, 3, -3);
            blocks[8] = loc.getBlock().getRelative(1, 0, -3);
            blocks[9] = loc.getBlock().getRelative(1, 1, -3);
            blocks[10] = loc.getBlock().getRelative(1, 2, -3);
            blocks[11] = loc.getBlock().getRelative(1, 3, -3);
            if (!blocks[0].getType().isAir() || !blocks[1].getType().isAir() || !blocks[2].getType().isAir() ||
                    !blocks[3].getType().isAir() || !blocks[4].getType().isAir() || !blocks[5].getType().isAir() ||
                    !blocks[6].getType().isAir() || !blocks[7].getType().isAir() || !blocks[8].getType().isAir() ||
                    !blocks[9].getType().isAir() || !blocks[10].getType().isAir() || !blocks[11].getType().isAir()) {
                player.sendMessage(RED + "You cannot make a portal here.");
            } else {
                //Left Side
                loc.getBlock().getRelative(-1, 0, -3).setType(Material.END_GATEWAY);
                setPortal(player, -1, 0, -3, farPortalLocation);
                loc.getBlock().getRelative(-1, 1, -3).setType(Material.END_GATEWAY);
                setPortal(player, -1, 1, -3, farPortalLocation);
                loc.getBlock().getRelative(-1, 2, -3).setType(Material.END_GATEWAY);
                setPortal(player, -1, 2, -3, farPortalLocation);
                loc.getBlock().getRelative(-1, 3, -3).setType(Material.END_GATEWAY);
                setPortal(player, -1, 3, -3, farPortalLocation);
                //Middle
                loc.getBlock().getRelative(0, 0, -3).setType(Material.END_GATEWAY);
                setPortal(player, 0, 0, -3, farPortalLocation);
                loc.getBlock().getRelative(0, 1, -3).setType(Material.END_GATEWAY);
                setPortal(player, 0, 1, -3, farPortalLocation);
                loc.getBlock().getRelative(0, 2, -3).setType(Material.END_GATEWAY);
                setPortal(player, 0, 2, -3, farPortalLocation);
                loc.getBlock().getRelative(0, 3, -3).setType(Material.END_GATEWAY);
                setPortal(player, 0, 3, -3, farPortalLocation);
                //Right Side
                loc.getBlock().getRelative(1, 0, -3).setType(Material.END_GATEWAY);
                setPortal(player, 1, 0, -3, farPortalLocation);
                loc.getBlock().getRelative(1, 1, -3).setType(Material.END_GATEWAY);
                setPortal(player, 1, 1, -3, farPortalLocation);
                loc.getBlock().getRelative(1, 2, -3).setType(Material.END_GATEWAY);
                setPortal(player, 1, 2, -3, farPortalLocation);
                loc.getBlock().getRelative(1, 3, -3).setType(Material.END_GATEWAY);
                setPortal(player, 1, 3, -3, farPortalLocation);

                MainTask mainTask = new MainTask(blocks, 0);
                MainTask mainTask2 = new MainTask(blocks, 1);
                MainTask mainTask3 = new MainTask(blocks, 2);
                MainTask mainTask4 = new MainTask(blocks, 3);
                MainTask mainTask5 = new MainTask(blocks, 4);
                MainTask mainTask6 = new MainTask(blocks, 5);
                MainTask mainTask7 = new MainTask(blocks, 6);
                MainTask mainTask8 = new MainTask(blocks, 7);
                MainTask mainTask9 = new MainTask(blocks, 8);
                MainTask mainTask10 = new MainTask(blocks, 9);
                MainTask mainTask11 = new MainTask(blocks, 10);
                MainTask mainTask12 = new MainTask(blocks, 11);
                mainTask.runTaskLater(this, 600L);
                mainTask2.runTaskLater(this, 600L);
                mainTask3.runTaskLater(this, 600L);
                mainTask4.runTaskLater(this, 600L);
                mainTask5.runTaskLater(this, 600L);
                mainTask6.runTaskLater(this, 600L);
                mainTask7.runTaskLater(this, 600L);
                mainTask8.runTaskLater(this, 600L);
                mainTask9.runTaskLater(this, 600L);
                mainTask10.runTaskLater(this, 600L);
                mainTask11.runTaskLater(this, 600L);
                mainTask12.runTaskLater(this, 600L);
            }
        }
    }

    public void PortalWest(Location farPortalLocation, Player player, Location loc) {
        if (player.getFacing() == BlockFace.WEST) {
            Block blocks[] = new Block[12];
            blocks[0] = loc.getBlock().getRelative(-3, 0, 1);
            blocks[1] = loc.getBlock().getRelative(-3, 1, 1);
            blocks[2] = loc.getBlock().getRelative(-3, 2, 1);
            blocks[3] = loc.getBlock().getRelative(-3, 3, 1);
            blocks[4] = loc.getBlock().getRelative(-3, 0, 0);
            blocks[5] = loc.getBlock().getRelative(-3, 1, 0);
            blocks[6] = loc.getBlock().getRelative(-3, 2, 0);
            blocks[7] = loc.getBlock().getRelative(-3, 3, 0);
            blocks[8] = loc.getBlock().getRelative(-3, 0, -1);
            blocks[9] = loc.getBlock().getRelative(-3, 1, -1);
            blocks[10] = loc.getBlock().getRelative(-3, 2, -1);
            blocks[11] = loc.getBlock().getRelative(-3, 3, -1);
            if (!blocks[0].getType().isAir() || !blocks[1].getType().isAir() || !blocks[2].getType().isAir() ||
                    !blocks[3].getType().isAir() || !blocks[4].getType().isAir() || !blocks[5].getType().isAir() ||
                    !blocks[6].getType().isAir() || !blocks[7].getType().isAir() || !blocks[8].getType().isAir() ||
                    !blocks[9].getType().isAir() || !blocks[10].getType().isAir() || !blocks[11].getType().isAir()) {
                player.sendMessage(RED + "You cannot make a portal here.");
            } else {
                //Left Side
                loc.getBlock().getRelative(-3, 0, 1).setType(Material.END_GATEWAY);
                setPortal(player, -3, 0, 1, farPortalLocation);
                loc.getBlock().getRelative(-3, 1, 1).setType(Material.END_GATEWAY);
                setPortal(player, -3, 1, 1, farPortalLocation);
                loc.getBlock().getRelative(-3, 2, 1).setType(Material.END_GATEWAY);
                setPortal(player, -3, 2, 1, farPortalLocation);
                loc.getBlock().getRelative(-3, 3, 1).setType(Material.END_GATEWAY);
                setPortal(player, -3, 3, 1, farPortalLocation);
                //Middle
                loc.getBlock().getRelative(-3, 0, 0).setType(Material.END_GATEWAY);
                setPortal(player, -3, 0, 0, farPortalLocation);
                loc.getBlock().getRelative(-3, 1, 0).setType(Material.END_GATEWAY);
                setPortal(player, -3, 1, 0, farPortalLocation);
                loc.getBlock().getRelative(-3, 2, 0).setType(Material.END_GATEWAY);
                setPortal(player, -3, 2, 0, farPortalLocation);
                loc.getBlock().getRelative(-3, 3, 0).setType(Material.END_GATEWAY);
                setPortal(player, -3, 3, 0, farPortalLocation);
                //Right Side
                loc.getBlock().getRelative(-3, 0, -1).setType(Material.END_GATEWAY);
                setPortal(player, -3, 0, -1, farPortalLocation);
                loc.getBlock().getRelative(-3, 1, -1).setType(Material.END_GATEWAY);
                setPortal(player, -3, 1, -1, farPortalLocation);
                loc.getBlock().getRelative(-3, 2, -1).setType(Material.END_GATEWAY);
                setPortal(player, -3, 2, -1, farPortalLocation);
                loc.getBlock().getRelative(-3, 3, -1).setType(Material.END_GATEWAY);
                setPortal(player, -3, 3, -1, farPortalLocation);

                MainTask mainTask = new MainTask(blocks, 0);
                MainTask mainTask2 = new MainTask(blocks, 1);
                MainTask mainTask3 = new MainTask(blocks, 2);
                MainTask mainTask4 = new MainTask(blocks, 3);
                MainTask mainTask5 = new MainTask(blocks, 4);
                MainTask mainTask6 = new MainTask(blocks, 5);
                MainTask mainTask7 = new MainTask(blocks, 6);
                MainTask mainTask8 = new MainTask(blocks, 7);
                MainTask mainTask9 = new MainTask(blocks, 8);
                MainTask mainTask10 = new MainTask(blocks, 9);
                MainTask mainTask11 = new MainTask(blocks, 10);
                MainTask mainTask12 = new MainTask(blocks, 11);
                mainTask.runTaskLater(this, 600L);
                mainTask2.runTaskLater(this, 600L);
                mainTask3.runTaskLater(this, 600L);
                mainTask4.runTaskLater(this, 600L);
                mainTask5.runTaskLater(this, 600L);
                mainTask6.runTaskLater(this, 600L);
                mainTask7.runTaskLater(this, 600L);
                mainTask8.runTaskLater(this, 600L);
                mainTask9.runTaskLater(this, 600L);
                mainTask10.runTaskLater(this, 600L);
                mainTask11.runTaskLater(this, 600L);
                mainTask12.runTaskLater(this, 600L);
            }
        }
    }

    public void PortalEast(Location farPortalLocation, Player player, Location loc) {
        if (player.getFacing() == BlockFace.EAST) {
            Block blocks[] = new Block[12];
            blocks[0] = loc.getBlock().getRelative(3, 0, -1);
            blocks[1] = loc.getBlock().getRelative(3, 1, -1);
            blocks[2] = loc.getBlock().getRelative(3, 2, -1);
            blocks[3] = loc.getBlock().getRelative(3, 3, -1);
            blocks[4] = loc.getBlock().getRelative(3, 0, 0);
            blocks[5] = loc.getBlock().getRelative(3, 1, 0);
            blocks[6] = loc.getBlock().getRelative(3, 2, 0);
            blocks[7] = loc.getBlock().getRelative(3, 3, 0);
            blocks[8] = loc.getBlock().getRelative(3, 0, 1);
            blocks[9] = loc.getBlock().getRelative(3, 1, 1);
            blocks[10] = loc.getBlock().getRelative(3, 2, 1);
            blocks[11] = loc.getBlock().getRelative(3, 3, 1);
            if (!blocks[0].getType().isAir() || !blocks[1].getType().isAir() || !blocks[2].getType().isAir() ||
                    !blocks[3].getType().isAir() || !blocks[4].getType().isAir() || !blocks[5].getType().isAir() ||
                    !blocks[6].getType().isAir() || !blocks[7].getType().isAir() || !blocks[8].getType().isAir() ||
                    !blocks[9].getType().isAir() || !blocks[10].getType().isAir() || !blocks[11].getType().isAir() ) {
                player.sendMessage(RED + "You cannot make a portal here.");
            } else {
                //Left Side
                loc.getBlock().getRelative(3, 0, -1).setType(Material.END_GATEWAY);
                setPortal(player, 3, 0, -1, farPortalLocation);
                loc.getBlock().getRelative(3, 1, -1).setType(Material.END_GATEWAY);
                setPortal(player, 3, 1, -1, farPortalLocation);
                loc.getBlock().getRelative(3, 2, -1).setType(Material.END_GATEWAY);
                setPortal(player, 3, 2, -1, farPortalLocation);
                loc.getBlock().getRelative(3, 3, -1).setType(Material.END_GATEWAY);
                setPortal(player, 3, 3, -1, farPortalLocation);
                //Middle
                loc.getBlock().getRelative(3, 0, 0).setType(Material.END_GATEWAY);
                setPortal(player, 3, 0, 0, farPortalLocation);
                loc.getBlock().getRelative(3, 1, 0).setType(Material.END_GATEWAY);
                setPortal(player, 3, 1, 0, farPortalLocation);
                loc.getBlock().getRelative(3, 2, 0).setType(Material.END_GATEWAY);
                setPortal(player, 3, 2, 0, farPortalLocation);
                loc.getBlock().getRelative(3, 3, 0).setType(Material.END_GATEWAY);
                setPortal(player, 3, 3, 0, farPortalLocation);
                //Right Side
                loc.getBlock().getRelative(3, 0, 1).setType(Material.END_GATEWAY);
                setPortal(player, 3, 0, 1, farPortalLocation);
                loc.getBlock().getRelative(3, 1, 1).setType(Material.END_GATEWAY);
                setPortal(player, 3, 1, 1, farPortalLocation);
                loc.getBlock().getRelative(3, 2, 1).setType(Material.END_GATEWAY);
                setPortal(player, 3, 2, 1, farPortalLocation);
                loc.getBlock().getRelative(3, 3, 1).setType(Material.END_GATEWAY);
                setPortal(player, 3, 3, 1, farPortalLocation);

                MainTask mainTask = new MainTask(blocks, 0);
                MainTask mainTask2 = new MainTask(blocks, 1);
                MainTask mainTask3 = new MainTask(blocks, 2);
                MainTask mainTask4 = new MainTask(blocks, 3);
                MainTask mainTask5 = new MainTask(blocks, 4);
                MainTask mainTask6 = new MainTask(blocks, 5);
                MainTask mainTask7 = new MainTask(blocks, 6);
                MainTask mainTask8 = new MainTask(blocks, 7);
                MainTask mainTask9 = new MainTask(blocks, 8);
                MainTask mainTask10 = new MainTask(blocks, 9);
                MainTask mainTask11 = new MainTask(blocks, 10);
                MainTask mainTask12 = new MainTask(blocks, 11);
                mainTask.runTaskLater(this, 600L);
                mainTask2.runTaskLater(this, 600L);
                mainTask3.runTaskLater(this, 600L);
                mainTask4.runTaskLater(this, 600L);
                mainTask5.runTaskLater(this, 600L);
                mainTask6.runTaskLater(this, 600L);
                mainTask7.runTaskLater(this, 600L);
                mainTask8.runTaskLater(this, 600L);
                mainTask9.runTaskLater(this, 600L);
                mainTask10.runTaskLater(this, 600L);
                mainTask11.runTaskLater(this, 600L);
                mainTask12.runTaskLater(this, 600L);
            }
        }
    }

    public void setPortal(Player player, int X, int Y, int Z, Location exitLocation) {
        Location loc = player.getPlayer().getLocation();
        Block block = loc.getBlock().getRelative(X, Y, Z);
        BlockState blockState = block.getState();
        EndGateway gate = (EndGateway) blockState;
        gate.setExitLocation(exitLocation);
        blockState.update();
    }

    void openGUI(Player player) {
        Inventory mainScreen = Bukkit.createInventory(null, 45, LIGHT_PURPLE + "Portal Menu");
        if (player.hasPermission(spawnTeleport)) {
            mainScreen.setItem(0, createItem("Spawn", GOLD, Material.STICK, "Go to Spawn"));
        }
        //todo write up saved locations
        mainScreen.setItem(28, createItem("Accept", ChatColor.GREEN, Material.GREEN_WOOL, "Accept teleportation"));
        mainScreen.setItem(34, createItem("Deny", RED, Material.RED_WOOL, "Reject current teleport"));
        if(player.hasPermission(serverLocationPermission)){
            mainScreen.setItem(16, createItem("Server Locations", BLUE, Material.STONE_BRICKS, "Open a portal to a preset location."));
        }
        if (player.hasPermission(locationPermission)) {
            for (int x = 1; x < 9; x++) {
                mainScreen.setItem(x, createItem("New Location", ChatColor.WHITE, Material.GREEN_WOOL, "Click me to save a new location!"));
            }
            for (int x = 18; x < 27; x++) {
                mainScreen.setItem(x, createItem("New Location", ChatColor.WHITE, Material.GREEN_WOOL, "Click me to save a new location"));
            }
            ArrayList<String> locations = new ArrayList<>(getPlayerData().getConfigurationSection("Players." + playerToUUID(player) + ".Saved-Locations").getKeys(false));
            if(locations != null){
                if(locations.size() > 9){
                    //split the first lot amongst the 9, then the leftovers on the second set
                    for(int x = 1; x < 9; x++){
                        mainScreen.setItem(x, createItem(locations.get(x-1), ChatColor.BLUE, Material.STICK, getPlayerData().getString("Players." + playerToUUID(player) + ".Saved-Locations." + locations.get(x-1))));
                   }
                    for(int x = 18; x < 27; x++){
                        mainScreen.setItem(x, createItem(locations.get(x-9), ChatColor.BLUE, Material.STICK, getPlayerData().getString("Players." + playerToUUID(player) + ".Saved-Locations." + locations.get(x-9))));
                    }
                } else {
                    for(int x = 1; x < 9; x++){
                        mainScreen.setItem(x, createItem(locations.get(x-1), ChatColor.BLUE, Material.STICK, getPlayerData().getString("Players." + playerToUUID(player) + ".Saved-Locations." + locations.get(x-1))));
                    }
                }
            }
        }
        if (player.hasPermission(bedTeleport)){
            mainScreen.setItem(14, createItem("Bed", RED, Material.RED_BED, "Go to Bed"));
        }
        if (player.hasPermission(randomTeleport)){
            mainScreen.setItem(12, createItem("Random Location", AQUA, Material.ENDER_PEARL, "Teleport to a random location"));
        }
        if (player.hasPermission(enderChestPermission)) {
            mainScreen.setItem(40, createItem("Enderchest", LIGHT_PURPLE, Material.ENDER_CHEST, "Open your Enderchest."));
        }
        if (player.hasPermission(personalVault)){
            Inventory personalVault = Bukkit.createInventory(null, 54, "Personal Vault");
            player.openInventory(personalVault);
        }
        if(player.hasPermission(playerTeleportPermission)){
            mainScreen.setItem(39, createItem("Teleport to Players", GREEN, Material.WITHER_SKELETON_SKULL, "Teleport to any player on the server!"));
        }
        if (player.hasPermission(returnTeleport)){
            mainScreen.setItem(15, createItem("Return Portal", ChatColor.AQUA, Material.QUARTZ, "Create a portal to the previous location"));
        }
        player.openInventory(mainScreen);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView iView = event.getView();
        if(iView.getTitle().equals(DARK_PURPLE + "Portal Menu")) {
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getItemMeta() != null) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Random Location") && event.getCurrentItem().getType() == Material.ENDER_PEARL) {
                        event.setCancelled(true);
                        Location location = player.getLocation();
                        if (location.getWorld() != null) {
                            WorldBorder worldBorder = location.getWorld().getWorldBorder();
                            Random rnd = new Random();
                            int x, y, z;
                            if (getConfig().getInt("General.Random-Travel-Value") > worldBorder.getSize()) {
                                x = rnd.nextInt(worldBorder.getCenter().getBlockX() + (int) (worldBorder.getSize() * 2 + 1)) - (int) worldBorder.getSize();
                                y = rnd.nextInt(worldBorder.getCenter().getBlockY() + (int) (worldBorder.getSize() * 2 + 1)) - (int) worldBorder.getSize();
                                z = rnd.nextInt(worldBorder.getCenter().getBlockZ() + (int) (worldBorder.getSize() * 2 + 1)) - (int) worldBorder.getSize();
                            } else {
                                x = rnd.nextInt(getConfig().getInt("General.Random-Travel-Value") * 2) - getConfig().getInt("General.Random-Travel-Value");
                                y = rnd.nextInt(getConfig().getInt("General.Random-Travel-Value") * 2) - getConfig().getInt("General.Random-Travel-Value");
                                z = rnd.nextInt(getConfig().getInt("General.Random-Travel-Value") * 2) - getConfig().getInt("General.Random-Travel-Value");
                            }
                            Location portalLocation = new Location(player.getWorld(), x, y, z);
                            makePortal(portalLocation, player);
                            player.closeInventory();
                        }
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(LIGHT_PURPLE + "Enderchest") && event.getCurrentItem().getType() == Material.ENDER_CHEST) {
                        event.setCancelled(true);
                        player.openInventory(player.getEnderChest());
                        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 0.5F, 1F);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(GOLD + "Spawn") && event.getCurrentItem().getType() == Material.STICK) {
                        event.setCancelled(true);
                        makePortal(getConfig().getLocation("General.Spawn"), player);
                        player.closeInventory();
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(RED + "Bed") && event.getCurrentItem().getType() == Material.RED_BED) {
                        event.setCancelled(true);
                        makePortal(player.getWorld().getSpawnLocation(), player);
                        player.closeInventory();
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "New Location")) {
                        ConversationFactory cf = new ConversationFactory(this);
                        Conversation conv = cf.withFirstPrompt(new ChatPrompt()).withLocalEcho(false).buildConversation(player);
                        player.closeInventory();
                        conv.begin();
                        getPlayerData().set("Players." + playerToUUID(player) + ".Saved-Location." + locationName, player.getLocation());
                        savePlayerData();
                    }
                    if (event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore() && event.getCurrentItem().getType() == Material.STICK) {
                        player.closeInventory();
                        makePortal(getPlayerData().getLocation("Players." + playerToUUID(player) + "." + event.getCurrentItem().getItemMeta().getDisplayName()), player);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(GREEN + "Teleport to Players") && event.getCurrentItem().getType() == Material.WITHER_SKELETON_SKULL) {
                        openPlayerTeleportMenu(player);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(BLUE + "Server Locations") && event.getCurrentItem().getType() == Material.STONE_BRICKS) {
                        openServerLocations(player);
                    }
                }
            }
        }
        //I guarentee you, these methods will error because why on earth would they work otherwise?
        if(iView.getTitle().equals(LIGHT_PURPLE + "Server Locations")){
            if(event.getCurrentItem().getType() != null || event.getCurrentItem().getType() != Material.AIR){
                player.closeInventory();
                makePortal(getConfig().getLocation("General.Server-Locations." + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())), player);
            }
        }
        if(iView.getTitle().equals(AQUA + "Player Teleport")){
            if(event.getCurrentItem().getType() != null || event.getCurrentItem().getType() != Material.AIR){
                player.closeInventory();
                makePortal(Bukkit.getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())).getLocation(), player);
            }
        }
    }

    public void openPlayerTeleportMenu(Player player){
        Inventory playerTeleport = Bukkit.createInventory(null, 54, AQUA + "Player Teleport");
        int counter = 0;
        playerTeleport.setItem(45, createItem("Previous Page", AQUA, Material.BLUE_WOOL, null));
        playerTeleport.setItem(53, createItem("Next Page", AQUA, Material.BLUE_WOOL, null));
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ItemStack charSkull = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
            SkullMeta charMeta = (SkullMeta) charSkull.getItemMeta();
            charMeta.setOwningPlayer((OfflinePlayer) onlinePlayer);
            charMeta.setDisplayName(ChatColor.GOLD + player.getDisplayName());
            playerTeleport.setItem(counter, charSkull);
            counter++;
            if(counter == 45){
                break;
            }
        }
        player.openInventory(playerTeleport);
    }

    public void openServerLocations(Player player){
        //todo same as the above method really, just grabbing from the config instead of online players
        Inventory serverLocations = Bukkit.createInventory(null, 45, LIGHT_PURPLE + "Server Locations");
        if(getConfig().getInt("General.Server-Locations.Total") == 0) {
            for (Integer i = 0; i < getConfig().getInt("General.Server-Locations.Total"); i++) {
                //oh right, this will error too because why in the god damn wouldnt it
                serverLocations.setItem(i, createItem(getConfig().getString("General.Server-Locations." + i.toString() + ".Name"), GREEN, Material.getMaterial(getConfig().getString("General.Server-Locations." + i.toString() + ".Material")), getConfig().getLocation("General.Server-Locations." + i.toString() + ".Location").toString()));
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
            //System.out.println("doesn't equal click block or click air");
            return;
        }
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Portal Stick") && player.getInventory().getItemInMainHand().getItemMeta().hasLore() && player.getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD){
            if(player.hasPermission(portalUsagePermission)){
                openGUI(player);
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to create portals!");
            }
        }
    }


    public ItemStack createItem(String name, ChatColor nameColour, Material material, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(nameColour + name);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("portals")){
            if (!(args.length == 0)) {
                //change this into opening the help menu
                if (args[0].equalsIgnoreCase("open")) {
                    openGUI((Player) sender);
                }
            } else {
                sender.sendMessage(RED + "Please use " + GOLD + "/portals open");
            }
            if(args[0].equalsIgnoreCase("give")){
                if(sender.hasPermission(adminPermission)) {
                    if (args[1].isEmpty()) {
                        Player player = (Player) sender;
                        player.getInventory().addItem(createItem("Portal Stick", ChatColor.GOLD, Material.BLAZE_ROD, "Used to create portals to other locations"));
                    } else {
                        try {
                            Player player = Bukkit.getPlayer(args[1]);
                            player.getInventory().addItem(createItem("Portal Stick", ChatColor.GOLD, Material.BLAZE_ROD, "Used to create portals to other locations"));
                        } catch(Exception e){
                            sender.sendMessage(RED + "Error - Player not found");
                        }
                    }
                }
            }
            if(args[0].equalsIgnoreCase("set")) {
                if (sender.hasPermission(adminPermission)) {
                    if (args[1].equalsIgnoreCase("spawn")) {
                        Player player = (Player) sender;
                        getConfig().set("General.Spawn", player.getLocation());
                    } else if (args[1].equalsIgnoreCase("location")) {
                        if (args[2].isEmpty()) {
                            sender.sendMessage(RED + "Error - Correct usage is /portals set location [location name]");
                        } else {
                            Player player = (Player) sender;
                            if (getConfig().getInt("General.Server-Locations.Total") == 0) {
                                getConfig().set("General.Server-Locations.Total", 1);
                            } else {
                                getConfig().set("General.Server-Locations.Total", getConfig().getInt("General.Server-Locations.Total") + 1);
                            }
                            getConfig().set("General.Server-Locations." + args[2], player.getLocation());
                        }
                    } else {
                        sender.sendMessage(RED + "Error - Correct usage is /portals set [spawn|location] <location name>");
                    }
                }
            }
        }
        return true;
    }

    public void helpMenu(Player player){
        player.sendMessage(ChatColor.GOLD + "===================[Atlantica Portals]==================");
        player.sendMessage(ChatColor.GOLD + "Key: [] = required argument, <> = optional requirement ");
        player.sendMessage(GOLD + "Note: certain commands have arguments that become required.");
    }


}

