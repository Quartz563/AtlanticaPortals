package net.quartz563.AtlanticaPortals.command.portals;

import net.quartz563.AtlanticaPortals.menu.ToolMenu;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

public class ToolSubCommand extends SimpleSubCommand {

	public ToolSubCommand(SimpleCommandGroup parent) {
		super(parent, "wand|tools|w|t");
	}

	@Override
	protected void onCommand() {
		checkConsole();
		new ToolMenu().displayTo(getPlayer());
	}
}
