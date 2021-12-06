package me.KrazyManJ.KrazyBungee.Listeners;

import me.KrazyManJ.KrazyBungee.Main;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements Listener {
    @EventHandler
    public void on(TabCompleteEvent event){
        if (event.getCursor().startsWith("/"+"bmsg ")){
            List<String> suggestions = new ArrayList<>();
            for (ProxiedPlayer player : Main.getInstance().getProxy().getPlayers()) suggestions.add(player.getName());
            event.getSuggestions().clear();
            event.getSuggestions().addAll(suggestions);
        }
        else{
            for (String alias : ConfigManager.getList("staffchat.aliases")){
                if (event.getCursor().startsWith("/"+alias)) event.setCancelled(true);
            }
        }
    }
}
