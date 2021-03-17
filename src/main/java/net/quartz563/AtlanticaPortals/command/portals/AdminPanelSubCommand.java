package net.quartz563.AtlanticaPortals.command.portals;

import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

public class AdminPanelSubCommand extends SimpleSubCommand {

	protected AdminPanelSubCommand(SimpleCommandGroup parent) {
		super(parent, "admin|a|apanel");
		setPermission("portals.admin");
		setDescription("An administrative management tool for the Portals plugin");
	}

	@Override
	protected void onCommand() {

	}
}
