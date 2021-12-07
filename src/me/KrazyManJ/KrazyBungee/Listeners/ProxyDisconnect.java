package me.KrazyManJ.KrazyBungee.Listeners;

import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import me.KrazyManJ.KrazyBungee.Utils.ProxyUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class ProxyDisconnect implements Listener {
    @EventHandler
    public void on(PlayerDisconnectEvent event){
        if (event.getPlayer().hasPermission("krazybungee.staffconnectstatus.gain")){
            String disconnectMsg = ConfigManager.getString("staff connect status.bungeecord disconnect")
                    .replace("{player}", event.getPlayer().getName())
                    .replace("{from}", event.getPlayer().getServer().getInfo().getName());
            List<String> bypass = ConfigManager.getList("staff connect status.bypass servers");
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (bypass.size() == 0 || !bypass.contains(event.getPlayer().getServer().getInfo().getName())){
                    ProxyUtils.sendPermission(player, "krazybungee.staffconnectstatus.notification", Format.toBaseComponent(disconnectMsg));
                }
            }
        }
    }
}
