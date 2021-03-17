package net.quartz563.AtlanticaPortals;

import lombok.Getter;
import net.quartz563.AtlanticaPortals.locations.PlayerLocations;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.settings.YamlSectionConfig;

import java.util.*;

@Getter
public class PlayerCache extends YamlSectionConfig {

	private static final Map<UUID, PlayerCache> cacheMap = new HashMap<>();

	private Location previousLocation;
	private int level;
	private final List<PlayerLocations> playerLocations = new ArrayList<>();

	protected PlayerCache(String uuid) {
		super(uuid);
		loadConfiguration(null, "data.db");
	}

	public void setPreviousLocation(Location previousLocation) {
		this.previousLocation = previousLocation;
	}

	public static PlayerCache getCache(final Player player){
		PlayerCache cache = cacheMap.get(player.getUniqueId());
		if(cache == null){
			cache = new PlayerCache(player.getUniqueId().toString());
			cacheMap.put(player.getUniqueId(), cache);
		}
		return cache;
	}


	@Override
	protected void onLoadFinish() {
		if(isSet("Level")){
			level = getInteger("Level");
		}
	}

	public SerializedMap serialize() {
		final SerializedMap map = new SerializedMap();
		for (PlayerLocations location: playerLocations){
			map.put("Locations", location.serialize());
		}
		map.put("Level", this.level);
		map.put("Uuid", getSection());
		return map;
	}

}
