package net.quartz563.AtlanticaPortals.tool;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.quartz563.AtlanticaPortals.AtlanticaPortals;
import net.quartz563.AtlanticaPortals.menu.PortalCreationMenu;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.remain.CompMaterial;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PortalCreationTool extends Tool{

	@Getter
	private static final Tool instance = new PortalCreationTool();


	@Override
	public ItemStack getItem() {
		return ItemCreator.of(CompMaterial.BLAZE_ROD, "Portal Wand", "", "A Mysterious device...", "Click on a block to open a portal.").glow(true).build().make();
	}

	@Override
	protected void onBlockClick(PlayerInteractEvent event) {
		AtlanticaPortals.previousLocation = event.getPlayer().getLocation();
		new PortalCreationMenu().displayTo(event.getPlayer());
	}
	
}
