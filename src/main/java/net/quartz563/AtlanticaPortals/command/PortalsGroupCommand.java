package net.quartz563.AtlanticaPortals.command;

import net.quartz563.AtlanticaPortals.command.portals.ToolSubCommand;
import org.mineacademy.fo.command.SimpleCommandGroup;

public class PortalsGroupCommand extends SimpleCommandGroup {

	@Override
	public void registerSubcommands() {
		registerSubcommand(new ToolSubCommand(this));
	}


	@Override
	protected String getCredits() {
		return "Created by Quartz563 and RivkiinShadows";
	}
}
