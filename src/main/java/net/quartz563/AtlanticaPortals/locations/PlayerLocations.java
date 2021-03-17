package net.quartz563.AtlanticaPortals.locations;

import org.bukkit.Location;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.List;

public class PlayerLocations extends YamlConfig implements ConfigSerializable {

	//todo figure out save/load for this

	private CompMaterial icon;
	private Location location;
	private String name;


	@Override
	public SerializedMap serialize() {
		final SerializedMap map = new SerializedMap();

		return map;
	}

	public List<PlayerLocations> compileLocations(List<String> name){
		List<PlayerLocations> compiledLocations = null;
		for(String locName:name){
			assert false;
			//compiledLocations.add()
		}
		return compiledLocations;
	}

	//public static PlayerLocations deserialize(final SerializedMap map){

	//}

}
