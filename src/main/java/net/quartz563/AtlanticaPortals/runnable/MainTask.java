package net.quartz563.AtlanticaPortals.runnable;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class MainTask extends BukkitRunnable {

    private final Block[] block;
    private final int num;

    public MainTask(Block[] block, int num){
        this.block = block;
        this.num = num;
    }

    @Override
    public void run(){
        block[num].setType(Material.AIR);
    }
}

