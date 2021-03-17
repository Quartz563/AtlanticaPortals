package net.quartz563.AtlanticaPortals.menu;

import net.quartz563.AtlanticaPortals.tool.PlayerLocationTool;
import net.quartz563.AtlanticaPortals.tool.PortalCreationTool;
import net.quartz563.AtlanticaPortals.tool.ServerLocationTool;
import org.mineacademy.fo.menu.MenuTools;

public class ToolMenu extends MenuTools {
	@Override
	protected Object[] compileTools() {
		if(getViewer().hasPermission("portals.admin")){
			return new Object[]{
				0, 0, PlayerLocationTool.getInstance(), 0, PortalCreationTool.getInstance(), 0, ServerLocationTool.getInstance(), 0, 0
			};
		} else {
			return  new Object[] {
					0, 0, PlayerLocationTool.getInstance(), 0, PortalCreationTool.getInstance(), 0, 0, 0, 0
			};
		}
	}

	@Override
	protected String[] getInfo() {
		return super.getInfo();
	}
}
