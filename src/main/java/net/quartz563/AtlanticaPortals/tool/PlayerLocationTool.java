package net.quartz563.AtlanticaPortals.tool;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.tool.Tool;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerLocationTool extends Tool {

	@Getter
	private static final Tool instance = new PlayerLocationTool();

	@Override
	public ItemStack getItem() {
		return null;
	}

	@Override
	protected void onBlockClick(PlayerInteractEvent event) {

	}
}
