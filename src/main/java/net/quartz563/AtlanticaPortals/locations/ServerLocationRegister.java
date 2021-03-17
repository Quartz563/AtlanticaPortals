package net.quartz563.AtlanticaPortals.locations;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mineacademy.fo.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class ServerLocationRegister {

	@Getter
	private static final ServerLocationRegister instance = new ServerLocationRegister();

	@Getter
	private final List<ServerLocations> loadedLocations = new ArrayList<>();

	public ServerLocations getLocation(String name){
		for (ServerLocations location:loadedLocations)
			if(location.getName().equals(name))
				return location;
		return null;
	}

	public void loadLocations(){
		loadedLocations.clear();
		for(File file: FileUtil.getFiles("locations", ".yml")){
			ServerLocations location = new ServerLocations(file.getName());
			loadedLocations.add(location);
		}
	}

}
