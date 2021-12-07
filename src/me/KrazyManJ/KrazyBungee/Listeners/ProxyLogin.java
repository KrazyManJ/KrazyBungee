package me.KrazyManJ.KrazyBungee.Listeners;

import me.KrazyManJ.KrazyBungee.Main;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyLogin implements Listener {
    @EventHandler (priority = 80)
    public void on(PostLoginEvent event){
        if (!event.getPlayer().hasPermission("maintenance.bypass")){
            Main.getInstance().getProxy().broadcast(Format.toBaseComponent("Kicked!"));
            event.getPlayer().disconnect(Format.toBaseComponent("Nejsi na listině údržby!"));
        }
    }
}
