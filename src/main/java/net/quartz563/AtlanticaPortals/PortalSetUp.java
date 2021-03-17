package net.quartz563.AtlanticaPortals;



import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.quartz563.AtlanticaPortals.runnable.MainTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.EndGateway;
import org.bukkit.entity.Player;

import java.util.Objects;

import static org.bukkit.ChatColor.RED;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PortalSetUp {

	@Getter
	private static final PortalSetUp instance = new PortalSetUp();

	public void makePortal(Location farPortalLocation, Player player) {
		//North = -Z
		//South = +Z
		//West = -X
		//East = +X
		Location loc = Objects.requireNonNull(player.getPlayer()).getLocation();

		PortalEast(farPortalLocation, player, loc);
		PortalWest(farPortalLocation, player, loc);
		PortalNorth(farPortalLocation, player, loc);
		PortalSouth(farPortalLocation, player, loc);
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_END_GATEWAY_SPAWN, 0.5F, 1F);
	}

	private void PortalSouth(Location farPortalLocation, Player player, Location loc) {
		if (player.getFacing() == BlockFace.SOUTH) {
			Block blocks[] = new Block[12];
			blocks[0] = loc.getBlock().getRelative(1, 0, 3);
			blocks[1] = loc.getBlock().getRelative(1, 1, 3);
			blocks[2] = loc.getBlock().getRelative(1, 2, 3);
			blocks[3] = loc.getBlock().getRelative(1, 3, 3);
			blocks[4] = loc.getBlock().getRelative(0, 0, 3);
			blocks[5] = loc.getBlock().getRelative(0, 1, 3);
			blocks[6] = loc.getBlock().getRelative(0, 2, 3);
			blocks[7] = loc.getBlock().getRelative(0, 3, 3);
			blocks[8] = loc.getBlock().getRelative(-1, 0, 3);
			blocks[9] = loc.getBlock().getRelative(-1, 1, 3);
			blocks[10] = loc.getBlock().getRelative(-1, 2, 3);
			blocks[11] = loc.getBlock().getRelative(-1, 3, 3);
			if (!blocks[0].getType().isAir() || !blocks[1].getType().isAir() || !blocks[2].getType().isAir() ||
					!blocks[3].getType().isAir() || !blocks[4].getType().isAir() || !blocks[5].getType().isAir() ||
					!blocks[6].getType().isAir() || !blocks[7].getType().isAir() || !blocks[8].getType().isAir() ||
					!blocks[9].getType().isAir() || !blocks[10].getType().isAir() || !blocks[11].getType().isAir()) {
				player.sendMessage(RED + "You cannot make a portal here.");
			} else {
				//Left Side
				loc.getBlock().getRelative(-1, 0, 3).setType(Material.END_GATEWAY);
				setPortal(player, -1, 0, 3, farPortalLocation);
				loc.getBlock().getRelative(-1, 1, 3).setType(Material.END_GATEWAY);
				setPortal(player, -1, 1, 3, farPortalLocation);
				loc.getBlock().getRelative(-1, 2, 3).setType(Material.END_GATEWAY);
				setPortal(player, -1, 2, 3, farPortalLocation);
				loc.getBlock().getRelative(-1, 3, 3).setType(Material.END_GATEWAY);
				setPortal(player, -1, 3, 3, farPortalLocation);
				//Middle
				loc.getBlock().getRelative(0, 0, 3).setType(Material.END_GATEWAY);
				setPortal(player, 0, 0, 3, farPortalLocation);
				loc.getBlock().getRelative(0, 1, 3).setType(Material.END_GATEWAY);
				setPortal(player, 0, 1, 3, farPortalLocation);
				loc.getBlock().getRelative(0, 2, 3).setType(Material.END_GATEWAY);
				setPortal(player, 0, 2, 3, farPortalLocation);
				loc.getBlock().getRelative(0, 3, 3).setType(Material.END_GATEWAY);
				setPortal(player, 0, 3, 3, farPortalLocation);
				//Right Side
				loc.getBlock().getRelative(1, 0, 3).setType(Material.END_GATEWAY);
				setPortal(player, 1, 0, 3, farPortalLocation);
				loc.getBlock().getRelative(1, 1, 3).setType(Material.END_GATEWAY);
				setPortal(player, 1, 1, 3, farPortalLocation);
				loc.getBlock().getRelative(1, 2, 3).setType(Material.END_GATEWAY);
				setPortal(player, 1, 2, 3, farPortalLocation);
				loc.getBlock().getRelative(1, 3, 3).setType(Material.END_GATEWAY);
				setPortal(player, 1, 3, 3, farPortalLocation);

				MainTask mainTask = new MainTask(blocks, 0);
				MainTask mainTask2 = new MainTask(blocks, 1);
				MainTask mainTask3 = new MainTask(blocks, 2);
				MainTask mainTask4 = new MainTask(blocks, 3);
				MainTask mainTask5 = new MainTask(blocks, 4);
				MainTask mainTask6 = new MainTask(blocks, 5);
				MainTask mainTask7 = new MainTask(blocks, 6);
				MainTask mainTask8 = new MainTask(blocks, 7);
				MainTask mainTask9 = new MainTask(blocks, 8);
				MainTask mainTask10 = new MainTask(blocks, 9);
				MainTask mainTask11 = new MainTask(blocks, 10);
				MainTask mainTask12 = new MainTask(blocks, 11);
				mainTask.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask2.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask3.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask4.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask5.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask6.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask7.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask8.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask9.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask10.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask11.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask12.runTaskLater(AtlanticaPortals.getInstance(), 600L);
			}
		}
	}

	private void PortalNorth(Location farPortalLocation, Player player, Location loc) {
		if (player.getFacing() == BlockFace.NORTH) {
			Block blocks[] = new Block[12];
			blocks[0] = loc.getBlock().getRelative(-1, 0, -3);
			blocks[1] = loc.getBlock().getRelative(-1, 1, -3);
			blocks[2] = loc.getBlock().getRelative(-1, 2, -3);
			blocks[3] = loc.getBlock().getRelative(-1, 3, -3);
			blocks[4] = loc.getBlock().getRelative(0, 0, -3);
			blocks[5] = loc.getBlock().getRelative(0, 1, -3);
			blocks[6] = loc.getBlock().getRelative(0, 2, -3);
			blocks[7] = loc.getBlock().getRelative(0, 3, -3);
			blocks[8] = loc.getBlock().getRelative(1, 0, -3);
			blocks[9] = loc.getBlock().getRelative(1, 1, -3);
			blocks[10] = loc.getBlock().getRelative(1, 2, -3);
			blocks[11] = loc.getBlock().getRelative(1, 3, -3);
			if (!blocks[0].getType().isAir() || !blocks[1].getType().isAir() || !blocks[2].getType().isAir() ||
					!blocks[3].getType().isAir() || !blocks[4].getType().isAir() || !blocks[5].getType().isAir() ||
					!blocks[6].getType().isAir() || !blocks[7].getType().isAir() || !blocks[8].getType().isAir() ||
					!blocks[9].getType().isAir() || !blocks[10].getType().isAir() || !blocks[11].getType().isAir()) {
				player.sendMessage(RED + "You cannot make a portal here.");
			} else {
				//Left Side
				loc.getBlock().getRelative(-1, 0, -3).setType(Material.END_GATEWAY);
				setPortal(player, -1, 0, -3, farPortalLocation);
				loc.getBlock().getRelative(-1, 1, -3).setType(Material.END_GATEWAY);
				setPortal(player, -1, 1, -3, farPortalLocation);
				loc.getBlock().getRelative(-1, 2, -3).setType(Material.END_GATEWAY);
				setPortal(player, -1, 2, -3, farPortalLocation);
				loc.getBlock().getRelative(-1, 3, -3).setType(Material.END_GATEWAY);
				setPortal(player, -1, 3, -3, farPortalLocation);
				//Middle
				loc.getBlock().getRelative(0, 0, -3).setType(Material.END_GATEWAY);
				setPortal(player, 0, 0, -3, farPortalLocation);
				loc.getBlock().getRelative(0, 1, -3).setType(Material.END_GATEWAY);
				setPortal(player, 0, 1, -3, farPortalLocation);
				loc.getBlock().getRelative(0, 2, -3).setType(Material.END_GATEWAY);
				setPortal(player, 0, 2, -3, farPortalLocation);
				loc.getBlock().getRelative(0, 3, -3).setType(Material.END_GATEWAY);
				setPortal(player, 0, 3, -3, farPortalLocation);
				//Right Side
				loc.getBlock().getRelative(1, 0, -3).setType(Material.END_GATEWAY);
				setPortal(player, 1, 0, -3, farPortalLocation);
				loc.getBlock().getRelative(1, 1, -3).setType(Material.END_GATEWAY);
				setPortal(player, 1, 1, -3, farPortalLocation);
				loc.getBlock().getRelative(1, 2, -3).setType(Material.END_GATEWAY);
				setPortal(player, 1, 2, -3, farPortalLocation);
				loc.getBlock().getRelative(1, 3, -3).setType(Material.END_GATEWAY);
				setPortal(player, 1, 3, -3, farPortalLocation);

				MainTask mainTask = new MainTask(blocks, 0);
				MainTask mainTask2 = new MainTask(blocks, 1);
				MainTask mainTask3 = new MainTask(blocks, 2);
				MainTask mainTask4 = new MainTask(blocks, 3);
				MainTask mainTask5 = new MainTask(blocks, 4);
				MainTask mainTask6 = new MainTask(blocks, 5);
				MainTask mainTask7 = new MainTask(blocks, 6);
				MainTask mainTask8 = new MainTask(blocks, 7);
				MainTask mainTask9 = new MainTask(blocks, 8);
				MainTask mainTask10 = new MainTask(blocks, 9);
				MainTask mainTask11 = new MainTask(blocks, 10);
				MainTask mainTask12 = new MainTask(blocks, 11);
				mainTask.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask2.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask3.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask4.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask5.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask6.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask7.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask8.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask9.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask10.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask11.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask12.runTaskLater(AtlanticaPortals.getInstance(), 600L);
			}
		}
	}

	private void PortalWest(Location farPortalLocation, Player player, Location loc) {
		if (player.getFacing() == BlockFace.WEST) {
			Block blocks[] = new Block[12];
			blocks[0] = loc.getBlock().getRelative(-3, 0, 1);
			blocks[1] = loc.getBlock().getRelative(-3, 1, 1);
			blocks[2] = loc.getBlock().getRelative(-3, 2, 1);
			blocks[3] = loc.getBlock().getRelative(-3, 3, 1);
			blocks[4] = loc.getBlock().getRelative(-3, 0, 0);
			blocks[5] = loc.getBlock().getRelative(-3, 1, 0);
			blocks[6] = loc.getBlock().getRelative(-3, 2, 0);
			blocks[7] = loc.getBlock().getRelative(-3, 3, 0);
			blocks[8] = loc.getBlock().getRelative(-3, 0, -1);
			blocks[9] = loc.getBlock().getRelative(-3, 1, -1);
			blocks[10] = loc.getBlock().getRelative(-3, 2, -1);
			blocks[11] = loc.getBlock().getRelative(-3, 3, -1);
			if (!blocks[0].getType().isAir() || !blocks[1].getType().isAir() || !blocks[2].getType().isAir() ||
					!blocks[3].getType().isAir() || !blocks[4].getType().isAir() || !blocks[5].getType().isAir() ||
					!blocks[6].getType().isAir() || !blocks[7].getType().isAir() || !blocks[8].getType().isAir() ||
					!blocks[9].getType().isAir() || !blocks[10].getType().isAir() || !blocks[11].getType().isAir()) {
				player.sendMessage(RED + "You cannot make a portal here.");
			} else {
				//Left Side
				loc.getBlock().getRelative(-3, 0, 1).setType(Material.END_GATEWAY);
				setPortal(player, -3, 0, 1, farPortalLocation);
				loc.getBlock().getRelative(-3, 1, 1).setType(Material.END_GATEWAY);
				setPortal(player, -3, 1, 1, farPortalLocation);
				loc.getBlock().getRelative(-3, 2, 1).setType(Material.END_GATEWAY);
				setPortal(player, -3, 2, 1, farPortalLocation);
				loc.getBlock().getRelative(-3, 3, 1).setType(Material.END_GATEWAY);
				setPortal(player, -3, 3, 1, farPortalLocation);
				//Middle
				loc.getBlock().getRelative(-3, 0, 0).setType(Material.END_GATEWAY);
				setPortal(player, -3, 0, 0, farPortalLocation);
				loc.getBlock().getRelative(-3, 1, 0).setType(Material.END_GATEWAY);
				setPortal(player, -3, 1, 0, farPortalLocation);
				loc.getBlock().getRelative(-3, 2, 0).setType(Material.END_GATEWAY);
				setPortal(player, -3, 2, 0, farPortalLocation);
				loc.getBlock().getRelative(-3, 3, 0).setType(Material.END_GATEWAY);
				setPortal(player, -3, 3, 0, farPortalLocation);
				//Right Side
				loc.getBlock().getRelative(-3, 0, -1).setType(Material.END_GATEWAY);
				setPortal(player, -3, 0, -1, farPortalLocation);
				loc.getBlock().getRelative(-3, 1, -1).setType(Material.END_GATEWAY);
				setPortal(player, -3, 1, -1, farPortalLocation);
				loc.getBlock().getRelative(-3, 2, -1).setType(Material.END_GATEWAY);
				setPortal(player, -3, 2, -1, farPortalLocation);
				loc.getBlock().getRelative(-3, 3, -1).setType(Material.END_GATEWAY);
				setPortal(player, -3, 3, -1, farPortalLocation);

				MainTask mainTask = new MainTask(blocks, 0);
				MainTask mainTask2 = new MainTask(blocks, 1);
				MainTask mainTask3 = new MainTask(blocks, 2);
				MainTask mainTask4 = new MainTask(blocks, 3);
				MainTask mainTask5 = new MainTask(blocks, 4);
				MainTask mainTask6 = new MainTask(blocks, 5);
				MainTask mainTask7 = new MainTask(blocks, 6);
				MainTask mainTask8 = new MainTask(blocks, 7);
				MainTask mainTask9 = new MainTask(blocks, 8);
				MainTask mainTask10 = new MainTask(blocks, 9);
				MainTask mainTask11 = new MainTask(blocks, 10);
				MainTask mainTask12 = new MainTask(blocks, 11);
				mainTask.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask2.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask3.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask4.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask5.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask6.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask7.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask8.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask9.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask10.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask11.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask12.runTaskLater(AtlanticaPortals.getInstance(), 600L);
			}
		}
	}

	private void PortalEast(Location farPortalLocation, Player player, Location loc) {
		if (player.getFacing() == BlockFace.EAST) {
			Block blocks[] = new Block[12];
			blocks[0] = loc.getBlock().getRelative(3, 0, -1);
			blocks[1] = loc.getBlock().getRelative(3, 1, -1);
			blocks[2] = loc.getBlock().getRelative(3, 2, -1);
			blocks[3] = loc.getBlock().getRelative(3, 3, -1);
			blocks[4] = loc.getBlock().getRelative(3, 0, 0);
			blocks[5] = loc.getBlock().getRelative(3, 1, 0);
			blocks[6] = loc.getBlock().getRelative(3, 2, 0);
			blocks[7] = loc.getBlock().getRelative(3, 3, 0);
			blocks[8] = loc.getBlock().getRelative(3, 0, 1);
			blocks[9] = loc.getBlock().getRelative(3, 1, 1);
			blocks[10] = loc.getBlock().getRelative(3, 2, 1);
			blocks[11] = loc.getBlock().getRelative(3, 3, 1);
			if (!blocks[0].getType().isAir() || !blocks[1].getType().isAir() || !blocks[2].getType().isAir() ||
					!blocks[3].getType().isAir() || !blocks[4].getType().isAir() || !blocks[5].getType().isAir() ||
					!blocks[6].getType().isAir() || !blocks[7].getType().isAir() || !blocks[8].getType().isAir() ||
					!blocks[9].getType().isAir() || !blocks[10].getType().isAir() || !blocks[11].getType().isAir() ) {
				player.sendMessage(RED + "You cannot make a portal here.");
			} else {
				//Left Side
				loc.getBlock().getRelative(3, 0, -1).setType(Material.END_GATEWAY);
				setPortal(player, 3, 0, -1, farPortalLocation);
				loc.getBlock().getRelative(3, 1, -1).setType(Material.END_GATEWAY);
				setPortal(player, 3, 1, -1, farPortalLocation);
				loc.getBlock().getRelative(3, 2, -1).setType(Material.END_GATEWAY);
				setPortal(player, 3, 2, -1, farPortalLocation);
				loc.getBlock().getRelative(3, 3, -1).setType(Material.END_GATEWAY);
				setPortal(player, 3, 3, -1, farPortalLocation);
				//Middle
				loc.getBlock().getRelative(3, 0, 0).setType(Material.END_GATEWAY);
				setPortal(player, 3, 0, 0, farPortalLocation);
				loc.getBlock().getRelative(3, 1, 0).setType(Material.END_GATEWAY);
				setPortal(player, 3, 1, 0, farPortalLocation);
				loc.getBlock().getRelative(3, 2, 0).setType(Material.END_GATEWAY);
				setPortal(player, 3, 2, 0, farPortalLocation);
				loc.getBlock().getRelative(3, 3, 0).setType(Material.END_GATEWAY);
				setPortal(player, 3, 3, 0, farPortalLocation);
				//Right Side
				loc.getBlock().getRelative(3, 0, 1).setType(Material.END_GATEWAY);
				setPortal(player, 3, 0, 1, farPortalLocation);
				loc.getBlock().getRelative(3, 1, 1).setType(Material.END_GATEWAY);
				setPortal(player, 3, 1, 1, farPortalLocation);
				loc.getBlock().getRelative(3, 2, 1).setType(Material.END_GATEWAY);
				setPortal(player, 3, 2, 1, farPortalLocation);
				loc.getBlock().getRelative(3, 3, 1).setType(Material.END_GATEWAY);
				setPortal(player, 3, 3, 1, farPortalLocation);

				MainTask mainTask = new MainTask(blocks, 0);
				MainTask mainTask2 = new MainTask(blocks, 1);
				MainTask mainTask3 = new MainTask(blocks, 2);
				MainTask mainTask4 = new MainTask(blocks, 3);
				MainTask mainTask5 = new MainTask(blocks, 4);
				MainTask mainTask6 = new MainTask(blocks, 5);
				MainTask mainTask7 = new MainTask(blocks, 6);
				MainTask mainTask8 = new MainTask(blocks, 7);
				MainTask mainTask9 = new MainTask(blocks, 8);
				MainTask mainTask10 = new MainTask(blocks, 9);
				MainTask mainTask11 = new MainTask(blocks, 10);
				MainTask mainTask12 = new MainTask(blocks, 11);
				mainTask.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask2.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask3.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask4.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask5.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask6.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask7.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask8.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask9.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask10.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask11.runTaskLater(AtlanticaPortals.getInstance(), 600L);
				mainTask12.runTaskLater(AtlanticaPortals.getInstance(), 600L);
			}
		}
	}

	private void setPortal(Player player, int X, int Y, int Z, Location exitLocation) {
		Location loc = player.getPlayer().getLocation();
		Block block = loc.getBlock().getRelative(X, Y, Z);
		BlockState blockState = block.getState();
		EndGateway gate = (EndGateway) blockState;
		gate.setExitLocation(exitLocation);
		blockState.update();
	}
	
}
