package me.KrazyManJ.KrazyBungee.Listeners;

import me.KrazyManJ.KrazyBungee.Main;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TabComplete implements Listener {
    @EventHandler
    public void on(TabCompleteEvent event){
        String[] values = event.getCursor().split(" ");
        String[] args = Arrays.copyOfRange(values, 1, values.length);
        String command = values[0].replace("/", "");

        if (command.equals("bmsg")){
            if (args.length >= 1) event.setCancelled(true);
            else{
                List<String> suggestions = new ArrayList<>();
                for (ProxiedPlayer player : Main.getInstance().getProxy().getPlayers())
                    suggestions.add(player.getName());
                event.getSuggestions().clear();
                event.getSuggestions().addAll(suggestByInput(1,args,suggestions));
            }
        }
        else{
            for (String alias : ConfigManager.getList("staffchat.aliases")){
                if (event.getCursor().startsWith("/"+alias)) event.setCancelled(true);
            }
        }
    }

    public List<String> suggestByInput(int pos, String[] args, List<String> suggestions){
        return (args != null && args.length >= pos)
                ? suggestions.stream().filter(f -> StringUtils.containsIgnoreCase(f, args[pos-1])).collect(Collectors.toList())
                : suggestions;

    }
}
