package net.quartz563.AtlanticaPortals;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class MainTask extends BukkitRunnable {

    private Block[] block;
    private int num;

    public MainTask(Block[] block, int num){
        this.block = block;
        this.num = num;
    }

    @Override
    public void run(){
        block[num].setType(Material.AIR);
    }
}

