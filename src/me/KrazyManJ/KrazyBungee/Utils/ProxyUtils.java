package me.KrazyManJ.KrazyBungee.Utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ProxyUtils {
    public static void sendPermission(ProxiedPlayer player, String permission, BaseComponent component){
        if (player.hasPermission(permission) || permission.isEmpty()) player.sendMessage(component);
    }
    public static void enableTab(ProxiedPlayer player){
        player.setTabHeader(
                Format.toBaseComponent(String.join("\n",ConfigManager.getList("tab.tab header"))),
                Format.toBaseComponent(String.join("\n",ConfigManager.getList("tab.tab footer"))));
    }
}
