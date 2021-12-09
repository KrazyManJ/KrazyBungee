package me.KrazyManJ.KrazyBungee.Listeners;

import me.KrazyManJ.KrazyBungee.Core.Maintenance;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyLogin implements Listener {
    @EventHandler
    public void on(PostLoginEvent event){
        if (!event.getPlayer().hasPermission("krazybungee.maintenance.bypass") && Maintenance.isMaintenance()){
            event.getPlayer().disconnect(Format.toBaseComponent(Maintenance.kickMessage));
        }
    }
}
