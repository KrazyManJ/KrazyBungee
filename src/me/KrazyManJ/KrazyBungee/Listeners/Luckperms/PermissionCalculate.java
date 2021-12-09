package me.KrazyManJ.KrazyBungee.Listeners.Luckperms;

import me.KrazyManJ.KrazyBungee.Core.Maintenance;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class PermissionCalculate implements Listener {
    public PermissionCalculate(Plugin plugin, LuckPerms luckPerms){
        EventBus eventBus = luckPerms.getEventBus();
        eventBus.subscribe(plugin,  UserDataRecalculateEvent.class, e -> {
            if (Maintenance.isMaintenance()) Maintenance.kickNotAllowed();
        });
    }
}
