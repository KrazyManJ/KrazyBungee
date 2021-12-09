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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TabComplete implements Listener {
    @EventHandler
    public void on(TabCompleteEvent event){
        String[] values = event.getCursor().split(" ");
        String argsS = event.getCursor().replace(values[0], "");
        String[] args = Arrays.copyOfRange(values, 1, values.length);
        String command = values[0].replace("/", "");
        if (command.equals("bmsg")){
            if (numberOfOccurence(argsS, ' ') > 1) event.setCancelled(true);
            else{
                List<String> suggestions = new ArrayList<>();
                for (ProxiedPlayer player : Main.getInstance().getProxy().getPlayers())
                    suggestions.add(player.getName());
                event.getSuggestions().clear();
                event.getSuggestions().addAll(suggestByInput(1,args,suggestions));
            }
        }
        else if (command.equals("krazybungee") || command.equals("kb")){
            if (numberOfOccurence(argsS, ' ') == 1){
                event.getSuggestions().clear();
                event.getSuggestions().addAll(suggestByInput(1, args, new ArrayList<>(Arrays.asList("reload", "maintenance"))));
            }
            else if (numberOfOccurence(argsS, ' ') == 2){
                if (args[0].equals("maintenance")){
                    event.getSuggestions().clear();
                    event.getSuggestions().addAll(suggestByInput(2, args, new ArrayList<>(Arrays.asList("on", "off", "status"))));
                }
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
    public int numberOfOccurence(String string, char ch){
        int count = 0;
        for (int i = 0; i < string.length(); i++) if (string.charAt(i) == ch) count++;
        return count;
    }
}
