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
    private static AtlanticaPortals instance;

    static public Location previousLocation = null;
    static String locationName = null;
    private int locationCounter = 0;
    private int playerCounter = 0;

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
    protected void onPluginStart() {
        registerCommands("portals|por", new PortalsGroupCommand());
    }

    public static AtlanticaPortals getInstance() {
        return instance;
    }

    @Override
    public List<Class<? extends YamlStaticConfig>> getSettings() {
        return Arrays.asList(Settings.class);
    }



    /*
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

            ArrayList<String> locations = playerData.getLocationKeys(playerToUUID(player));
            if(locations != null) {
                if (locations.size() > 9) {
                    //split the first lot amongst the 9, then the leftovers on the second set
                    for (int x = 1; x < 9; x++) {
                        mainScreen.setItem(x, createItem(locations.get(x - 1), ChatColor.BLUE, Material.STICK, playerData.getSavedLocation(locations.get(x - 1), playerToUUID(player)).toString()));
                    }
                    for (int x = 18; x < 27; x++) {
                        mainScreen.setItem(x, createItem(locations.get(x - 9), ChatColor.BLUE, Material.STICK, playerData.getSavedLocation(locations.get(x - 9), playerToUUID(player)).toString()));
                    }
                } else {
                    for (int x = 1; x < 9; x++) {
                        mainScreen.setItem(x, createItem(locations.get(x - 1), ChatColor.BLUE, Material.STICK, playerData.getSavedLocation(locations.get(x - 1), playerToUUID(player)).toString()));
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
            //todo test later...
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
                            if (configData.getRandomTravelValue() > worldBorder.getSize()) {
                                x = rnd.nextInt(worldBorder.getCenter().getBlockX() + (int) (worldBorder.getSize() * 2 + 1)) - (int) worldBorder.getSize();
                                y = rnd.nextInt(worldBorder.getCenter().getBlockY() + (int) (worldBorder.getSize() * 2 + 1)) - (int) worldBorder.getSize();
                                z = rnd.nextInt(worldBorder.getCenter().getBlockZ() + (int) (worldBorder.getSize() * 2 + 1)) - (int) worldBorder.getSize();
                            } else {
                                x = rnd.nextInt(configData.getRandomTravelValue() * 2) - configData.getRandomTravelValue();
                                y = rnd.nextInt(configData.getRandomTravelValue() * 2) - configData.getRandomTravelValue();
                                z = rnd.nextInt(configData.getRandomTravelValue() * 2) - configData.getRandomTravelValue();
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
                        makePortal(configData.getSpawn(), player);
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
                        playerData.setNewSavedLocation(locationName, player.getLocation(), playerToUUID(player));
                    }
                    if (event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore() && event.getCurrentItem().getType() == Material.STICK) {
                        player.closeInventory();
                        makePortal(playerData.getSavedLocation(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()), playerToUUID(player)), player);
                        previousLocation = player.getLocation();
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(GREEN + "Teleport to Players") && event.getCurrentItem().getType() == Material.WITHER_SKELETON_SKULL) {
                        openPlayerTeleportMenu(player);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(BLUE + "Server Locations") && event.getCurrentItem().getType() == Material.STONE_BRICKS) {
                        openServerLocations(player, 0);
                    }
                    if(event.getCurrentItem().isSimilar(createItem("Return Portal", ChatColor.AQUA, Material.QUARTZ, "Create a portal to the previous location"))){
                        makePortal(previousLocation, player);
                        previousLocation = player.getLocation();
                    }
                }
            }
        }
        if(iView.getTitle().contains(LIGHT_PURPLE + "Server Locations")){
            if(event.getCurrentItem().getType() != null || event.getCurrentItem().getType() != Material.AIR){
                player.closeInventory();
                makePortal(getConfig().getLocation("General.Server-Locations." + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())), player);
                previousLocation = player.getLocation();
            } else if(event.getCurrentItem() == null){
                if(locationCounter > (int) Math.round(configData.getServerLocationTotal()/45.0)){
                    openServerLocations(player, 0);
                } else {
                    openServerLocations(player, locationCounter);
                    locationCounter++;
                }
            }
        }
        if(iView.getTitle().contains(AQUA + "Player Teleport")){
            if(event.getCurrentItem().getType() != null || event.getCurrentItem().getType() != Material.AIR){
                player.closeInventory();
                makePortal(Bukkit.getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())).getLocation(), player);
                previousLocation = player.getLocation();
            } else if(event.getCurrentItem() == null){
                player.sendMessage("Null Clicked.");
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

    public void openServerLocations(Player player, int screen){
        Inventory serverLocations = Bukkit.createInventory(null, 45, LIGHT_PURPLE + "Server Locations - Page " + screen);
        if(configData.getServerLocationTotal() > 45) {
            String[][] locations = new String[(int) Math.floor(configData.getServerLocationTotal()/45.0)][45];
            int counter = 0;
            for (int i = 0; i < (int) Math.round(configData.getServerLocationTotal() / 45.0); i++) {
                for (int x = 0; x < 45; x++) {
                    locations[i][x] = configData.getNextLocation(counter);
                    counter++;
                }
            }
            for (int i = 0; i < Math.min(45, locations[screen].length); i++) {
                serverLocations.setItem(i, createLocationItem(locations[screen][i]));
            }
        } else {
            for (int i = 0; i < getConfig().getInt("General.Server-Locations.Total"); i++) {
                serverLocations.setItem(i, createLocationItem(configData.getNextLocation(i)));
            }
        }
        player.openInventory(serverLocations);
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

    public ItemStack createLocationItem(String name){
        ItemStack item = new ItemStack(configData.getServerLocationMaterial(name));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(AQUA + name);
        meta.setLore(Arrays.asList(configData.getServerLocation(name).toString()));
        item.setItemMeta(meta);
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
    */


}

