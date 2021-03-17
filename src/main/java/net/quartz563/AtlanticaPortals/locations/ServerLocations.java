package net.quartz563.AtlanticaPortals.locations;

import lombok.Getter;
import org.mineacademy.fo.settings.YamlConfig;

@Getter
public class ServerLocations extends YamlConfig {

	public ServerLocations(final String name){
		setHeader("Welcome to the primary server location configuration file");
		loadConfiguration("portal-location.yml", "locations/" + name + (name.endsWith(".yml") ? ".yml":""));
	}

	@Override
	protected void onLoadFinish() {
		super.onLoadFinish();
	}

	@Override
	public String getName() {
		return getFileName().replace(".yml", "");
	}
}
