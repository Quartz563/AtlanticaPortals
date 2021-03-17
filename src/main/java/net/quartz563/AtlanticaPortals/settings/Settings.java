package net.quartz563.AtlanticaPortals.settings;

import org.bukkit.Location;
import org.mineacademy.fo.settings.SimpleSettings;

public class Settings extends SimpleSettings {
	@Override
	protected int getConfigVersion() {
		return 1;
	}

	public static int RANDOM_TRAVEL_VALUE;
	public static Location SPAWN_LOCATION;

	private static void init(){
		pathPrefix(null);
		RANDOM_TRAVEL_VALUE = getInteger("Random-Travel-Value");
		SPAWN_LOCATION = (Location) getObject("Spawn");
	}


	public static class Level{

		public static Boolean LEVEL_ENABLE;
		public static int BED_REQUIREMENT;
		public static int RANDOM_REQUIREMENT;
		public static int SERVER_LOC_REQUIREMENT;
		public static int PLAYER_LOC_REQUIREMENT;
		public static int PLAYER_TELEPORT_REQUIREMENT;
		public static int RETURN_REQUIREMENT;
		public static int ENDER_REQUIREMENT;
		public static int MAX_LEVEL;


		private static void init(){
			pathPrefix(null);
			LEVEL_ENABLE = getBoolean("Enable-Leveling");
			pathPrefix("Level");
			MAX_LEVEL = getInteger("Maximum-Level");
			BED_REQUIREMENT = getInteger("Bed-Level-Requirement");
			RANDOM_REQUIREMENT = getInteger("Random-Level-Requirement");
			SERVER_LOC_REQUIREMENT = getInteger("Server-Location-Level-Requirement");
			PLAYER_LOC_REQUIREMENT = getInteger("Player-Location-Level-Requirement");
			PLAYER_TELEPORT_REQUIREMENT = getInteger("Player-Teleport-Level-Requirement");
			RETURN_REQUIREMENT = getInteger("Return-Level-Requirement");
			ENDER_REQUIREMENT = getInteger("Ender-Level-Requirement");
		}

	}





}
