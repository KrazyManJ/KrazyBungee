package me.KrazyManJ.KrazyBungee.Listeners;

import me.KrazyManJ.KrazyBungee.Utils.Format;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProxyPing implements Listener {
    @EventHandler
    public void on(ProxyPingEvent event){
        ServerPing ping = event.getResponse();
        ping.setDescriptionComponent(Format.toBaseComponent("&#ff0000Hello &#ff00ffhow are you?"));
        ping.getVersion().setProtocol(4);
        ping.getVersion().setName(ChatColor.translateAlternateColorCodes('&', "&4&lNope"));
        List<ServerPing.PlayerInfo> info = new ArrayList<>();
        info.add(new ServerPing.PlayerInfo("Hello!",UUID.randomUUID()));
        ping.getPlayers().setSample(info.toArray(new ServerPing.PlayerInfo[0]));
    }
}
