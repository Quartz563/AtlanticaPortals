package net.quartz563.AtlanticaPortals.configs;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FileCreationEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private String filename;

    public FileCreationEvent(String filename) {
        this.filename = filename;
    }

    public String getFileName() {
        return filename;
    }

}
