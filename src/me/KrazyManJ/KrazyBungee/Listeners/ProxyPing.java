package me.KrazyManJ.KrazyBungee.Listeners;

import me.KrazyManJ.KrazyBungee.Core.Maintenance;
import me.KrazyManJ.KrazyBungee.Main;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

public class ProxyPing implements Listener {
    @EventHandler
    public void on(ProxyPingEvent event){
        ServerPing ping = event.getResponse();
        if (Maintenance.isMaintenance() && ConfigManager.getBoolean("maintenance.motd.enabled")){
            if (ConfigManager.getList("maintenance.motd.motd").size() > 0){
                List<String> motds = ConfigManager.getList("maintenance.motd.motd");
                if (motds.size() > 1) ping.setDescriptionComponent(Format.toBaseComponent(motds.get(new Random().nextInt(motds.size()))));
                else ping.setDescriptionComponent(Format.toBaseComponent(motds.get(0)));
            }
            else Main.log(Level.WARNING, "There was problem with showing MOTD!");
            if (ConfigManager.getBoolean("maintenance.motd.custom player text.enabled")){
                ping.getVersion().setProtocol(0);
                ping.getVersion().setName(ChatColor.translateAlternateColorCodes('&', ConfigManager.getString("maintenance.motd.custom player text.text")));
            }
            if (ConfigManager.getBoolean("maintenance.motd.player hover message.enabled")){
                List<ServerPing.PlayerInfo> info = new ArrayList<>();
                for (String text : ConfigManager.getList("maintenance.motd.player hover message.text"))
                    info.add(new ServerPing.PlayerInfo(ChatColor.translateAlternateColorCodes('&',text),UUID.randomUUID()));
                ping.getPlayers().setSample(info.toArray(new ServerPing.PlayerInfo[0]));
            }
        }
        else{
            if (ConfigManager.getList("motd.motd").size() > 0){
                List<String> motds = ConfigManager.getList("motd.motd");
                if (motds.size() > 1) ping.setDescriptionComponent(Format.toBaseComponent(motds.get(new Random().nextInt(motds.size()))));
                else ping.setDescriptionComponent(Format.toBaseComponent(motds.get(0)));
            }
            else Main.log(Level.WARNING, "There was problem with showing MOTD!");
            if (ConfigManager.getBoolean("motd.custom player text.enabled")){
                ping.getVersion().setProtocol(0);
                ping.getVersion().setName(ChatColor.translateAlternateColorCodes('&', ConfigManager.getString("motd.custom player text.text")));
            }
            if (ConfigManager.getBoolean("motd.player hover message.enabled")){
                List<ServerPing.PlayerInfo> info = new ArrayList<>();
                for (String text : ConfigManager.getList("motd.player hover message.text"))
                    info.add(new ServerPing.PlayerInfo(ChatColor.translateAlternateColorCodes('&',text),UUID.randomUUID()));
                ping.getPlayers().setSample(info.toArray(new ServerPing.PlayerInfo[0]));
            }
        }
    }
}
