package net.quartz563.AtlanticaPortals.menu;


import net.quartz563.AtlanticaPortals.PlayerCache;
import net.quartz563.AtlanticaPortals.PortalSetUp;
import net.quartz563.AtlanticaPortals.locations.PlayerLocations;
import net.quartz563.AtlanticaPortals.locations.ServerLocationRegister;
import net.quartz563.AtlanticaPortals.locations.ServerLocations;
import net.quartz563.AtlanticaPortals.runnable.MainTask;
import net.quartz563.AtlanticaPortals.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Random;

public class PortalCreationMenu extends Menu {

	private final Button spawnButton;
	private final Button serverLocations; //requires paged
	//private final Button playerLocations; //requires paged
	private final Button randomLocation; //requires worldguard info
	private final Button returnLocation;
	private final Button playerTeleport; //requires paged
	private final Button bedTeleport;
	private final Button enderChest;
	//private final Button levelButton;

	public PortalCreationMenu(){
		setTitle("Portal Menu");
		setSize(9*5);
		setSlotNumbersVisible();
		this.spawnButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				Location spawn = Settings.SPAWN_LOCATION;
				PortalSetUp.getInstance().makePortal(spawn, player);
				PlayerCache.getCache(player).setPreviousLocation(player.getLocation());
				player.closeInventory();
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.STICK,
						"Spawn",
						"",
						"Create a portal to the server spawn").build().make();
			}
		};
		this.returnLocation = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {

			}

			@Override
			public ItemStack getItem() {
				boolean access = allowedAccess(getViewer(), "RETURN");
				return ItemCreator.of(access ? CompMaterial.QUARTZ:CompMaterial.BARRIER,
						"Return Portal",
						"",
						"Create a portal to where you previously were"
				).build().make();
			}
		};
		this.bedTeleport = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				PortalSetUp.getInstance().makePortal(player.getWorld().getSpawnLocation(), player);
				PlayerCache.getCache(player).setPreviousLocation(player.getLocation());
				player.closeInventory();
			}

			@Override
			public ItemStack getItem() {
				boolean access = allowedAccess(getViewer(), "BED");
				return ItemCreator.of(access ? CompMaterial.RED_BED:CompMaterial.BARRIER,
						"Bed Portal",
						"",
						"Create a portal to your bed").build().make();
			}
		};
		this.enderChest = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				player.closeInventory();
				player.openInventory(player.getEnderChest());
			}

			@Override
			public ItemStack getItem() {
				boolean access = allowedAccess(getViewer(), "ENDER");
				return ItemCreator.of(access ? CompMaterial.ENDER_CHEST:CompMaterial.BARRIER,
						"Personal Vault",
						"",
						"Access your personal vault").build().make();
			}
		};
		this.randomLocation = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				Location location = player.getLocation();
				if (location.getWorld() != null) {
					WorldBorder worldBorder = location.getWorld().getWorldBorder();
					Random rnd = new Random();
					int x, y, z;
					if (Settings.RANDOM_TRAVEL_VALUE > worldBorder.getSize()) {
						x = rnd.nextInt(worldBorder.getCenter().getBlockX() + (int) (worldBorder.getSize() * 2 + 1)) - (int) worldBorder.getSize();
						y = rnd.nextInt(worldBorder.getCenter().getBlockY() + (int) (worldBorder.getSize() * 2 + 1)) - (int) worldBorder.getSize();
						z = rnd.nextInt(worldBorder.getCenter().getBlockZ() + (int) (worldBorder.getSize() * 2 + 1)) - (int) worldBorder.getSize();
					} else {
						x = rnd.nextInt(Settings.RANDOM_TRAVEL_VALUE * 2) - Settings.RANDOM_TRAVEL_VALUE;
						y = rnd.nextInt(Settings.RANDOM_TRAVEL_VALUE * 2) - Settings.RANDOM_TRAVEL_VALUE;
						z = rnd.nextInt(Settings.RANDOM_TRAVEL_VALUE * 2) - Settings.RANDOM_TRAVEL_VALUE;
					}
					Location portalLocation = new Location(player.getWorld(), x, y, z);
					PortalSetUp.getInstance().makePortal(portalLocation, player);
					PlayerCache.getCache(player).setPreviousLocation(player.getLocation());
					player.closeInventory();
				}
			}

			@Override
			public ItemStack getItem() {
				boolean access = allowedAccess(getViewer(), "RANDOM");
				return ItemCreator.of(access ? CompMaterial.ENDER_PEARL:CompMaterial.BARRIER,
						"Random Portal",
						"",
						"Open a portal to a random location").build().make();
			}
		};
		boolean accessServLoc = allowedAccess(getViewer(), "SERVER_LOC");
		serverLocations = new ButtonMenu(new ServerLocationsMenu(),
				accessServLoc ? CompMaterial.ENDER_PEARL:CompMaterial.BARRIER,
				"Server Locations",
				"",
				"Open a portal to a preset server location");
		boolean accessPlayLoc = allowedAccess(getViewer(), "PLAYER_LOC");
		/*
		playerLocations = new ButtonMenu(new PlayerLocationsMenu(),
				accessPlayLoc ? CompMaterial.ENDER_PEARL:CompMaterial.BARRIER,
				"Player Locations",
				"",
				"Open a portal to a location you saved"
				);

		 */
		boolean accessPlayTele = allowedAccess(getViewer(), "PLAYER_TELE");
		playerTeleport  = new ButtonMenu(new PlayerTeleportMenu(), accessPlayTele ? CompMaterial.TOTEM_OF_UNDYING:CompMaterial.BARRIER,
				"Player Teleport",
				"",
				"Open a portal to another player"
				);
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == 9*1 + 2)
			return spawnButton.getItem();

		if (slot == 9*1 + 4)
			return randomLocation.getItem();

		if (slot == 9*1 + 6)
			return returnLocation.getItem();

		if (slot == 9*3 + 2)
			return bedTeleport.getItem();

		if (slot == 9*3 + 4)
			return enderChest.getItem();

		return null;
	}

	@Override
	protected String[] getInfo() {
		return new String[]{
				"The primary menu",
				"for portal creation"
		};
	}

	public Boolean allowedAccess(Player player, String category){
		if(Settings.Level.LEVEL_ENABLE){
			int level = PlayerCache.getCache(player).getLevel();
			switch (category){
				case "BED":
					if(player.hasPermission("portals.bed") && level == Settings.Level.BED_REQUIREMENT){
						return true;
					}
				case "ENDER" :
					if(player.hasPermission("portals.ender") && level == Settings.Level.ENDER_REQUIREMENT){
						return true;
					}
				case "PLAYER_LOC":
					if(player.hasPermission("portals.playerlocation") && level == Settings.Level.PLAYER_LOC_REQUIREMENT){
						return true;
					}
				case "SERVER_LOC":
					if(player.hasPermission("portals.serverlocation") && level == Settings.Level.SERVER_LOC_REQUIREMENT){
						return true;
					}
				case "PLAYER_TELE":
					if(player.hasPermission("portals.playerteleport") && level == Settings.Level.PLAYER_TELEPORT_REQUIREMENT){
						return true;
					}
				case "RETURN":
					if(player.hasPermission("portals.return") && level == Settings.Level.RETURN_REQUIREMENT){
						return true;
					}
				case "RANDOM":
					if(player.hasPermission("portals.random") && level == Settings.Level.RANDOM_REQUIREMENT){
						return true;
					}
				default: return false;
			}
		} else {
			switch (category){
				case "BED":
					if(player.hasPermission("portals.bed")){
						return true;
					}
				case "ENDER" :
					if(player.hasPermission("portals.ender")){
						return true;
					}
				case "PLAYER_LOC":
					if(player.hasPermission("portals.playerlocation")){
						return true;
					}
				case "SERVER_LOC":
					if(player.hasPermission("portals.serverlocation")){
						return true;
					}
				case "PLAYER_TELE":
					if(player.hasPermission("portals.playerteleport")){
						return true;
					}
				case "RETURN":
					if(player.hasPermission("portals.return")){
						return true;
					}
				case "RANDOM":
					if(player.hasPermission("portals.random")){
						return true;
					}
				default: return false;
			}
		}
	}


	private final class ServerLocationsMenu extends MenuPagged<ServerLocations>{

		protected ServerLocationsMenu() {
			super(PortalCreationMenu.this, ServerLocationRegister.getInstance().getLoadedLocations());
		}


		@Override
		protected ItemStack convertToItemStack(ServerLocations item) {
			return null;
		}

		@Override
		protected void onPageClick(org.bukkit.entity.Player player, ServerLocations item, ClickType click) {

		}
	}

	/*
	private final class PlayerLocationsMenu extends MenuPagged<PlayerLocations>{

		protected PlayerLocationsMenu() {
			super(PortalCreationMenu.this, );
		}

		@Override
		protected ItemStack convertToItemStack(PlayerLocations item) {
			return null;
		}

		@Override
		protected void onPageClick(Player player, PlayerLocations item, ClickType click) {

		}
	}

	 */

	private final class PlayerTeleportMenu extends MenuPagged<Player> {

		protected PlayerTeleportMenu() {
			super(PortalCreationMenu.this, (Iterable<Player>) Bukkit.getOnlinePlayers());
		}

		@Override
		protected ItemStack convertToItemStack(Player item) {
			return null;
		}

		@Override
		protected void onPageClick(Player player, Player item, ClickType click) {

		}
	}

}
