package me.KrazyManJ.KrazyBungee.Listeners;

import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import me.KrazyManJ.KrazyBungee.Utils.ProxyUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class SwitchServer implements Listener {
    @EventHandler
    public void on(ServerSwitchEvent event){
        if (event.getPlayer().hasPermission("krazybungee.staffconnectstatus.gain")) {
            //Just connecting
            if (event.getFrom() == null) {
                String connectMsg = ConfigManager.getString("staff connect status.bungeecord connect")
                        .replace("{player}", event.getPlayer().getName())
                        .replace("{to}", event.getPlayer().getServer().getInfo().getName());
                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    if (!ConfigManager.getBoolean("staff connect status.send yourself status") && !player.equals(event.getPlayer())) {
                        List<String> bypass = ConfigManager.getList("staff connect status.bypass servers");
                        if (bypass.size() == 0 || !bypass.contains(player.getServer().getInfo().getName())) {
                            ProxyUtils.sendPermission(player, "krazybungee.staffconnectstatus.notification", Format.toBaseComponent(connectMsg));
                        }
                    }
                }
            }
            //Switching to other server
            else {
                String switchMsg = ConfigManager.getString("staff connect status.server change")
                        .replace("{player}", event.getPlayer().getName())
                        .replace("{from}", event.getFrom().getName())
                        .replace("{to}", event.getPlayer().getServer().getInfo().getName());
                List<String> bypass = ConfigManager.getList("staff connect status.bypass servers");
                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    if (!ConfigManager.getBoolean("staff connect status.send yourself status") && !player.equals(event.getPlayer())) {
                        if (bypass.size() == 0 || !bypass.contains(player.getServer().getInfo().getName())) {
                            ProxyUtils.sendPermission(player, "krazybungee.staffconnectstatus.notification", Format.toBaseComponent(switchMsg));
                        }
                    }
                }
            }
        }
    }
}
