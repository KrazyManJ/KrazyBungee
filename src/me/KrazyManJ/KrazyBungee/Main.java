package me.KrazyManJ.KrazyBungee;

import me.KrazyManJ.KrazyBungee.Commands.*;
import me.KrazyManJ.KrazyBungee.Listeners.*;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.ProxyUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;

public class Main extends Plugin {
    private static ProxyServer proxy;
    private static Main instance;
    @Override
    public void onEnable() {
        instance = this;
        proxy = getProxy();
        try { ConfigManager.createConfig(); } catch (IOException e) { e.printStackTrace(); }

        reload();
        super.onEnable();
    }

    public static void reload(){
        proxy.getPluginManager().unregisterListeners(instance);
        proxy.getPluginManager().unregisterCommands(instance);

        proxy.getPluginManager().registerCommand(instance, new ReloadCommand());

        if (ConfigManager.getBoolean("staffchat.enabled")) {
            String[] aliases = ConfigManager.getList("staffchat.aliases").toArray(new String[0]);
            proxy.getPluginManager().registerCommand(instance, new StaffChatCommand(ConfigManager.getString("staffchat.command"),aliases));
        }

        if (ConfigManager.getBoolean("trialstaffchat.enabled")) {
            String[] aliasesT = ConfigManager.getList("trialstaffchat.aliases").toArray(new String[0]);
            proxy.getPluginManager().registerCommand(instance, new TrialStaffChatCommand(ConfigManager.getString("trialstaffchat.command"),aliasesT));
        }

        if (ConfigManager.getBoolean("private bungee message.enabled")) {
            String[] aliasesT = ConfigManager.getList("private bungee message.aliases").toArray(new String[0]);
            proxy.getPluginManager().registerCommand(instance, new PrivateBungeeMessageCommand(ConfigManager.getString("private bungee message.command"),aliasesT));
        }

        if (ConfigManager.getBoolean("staff connect status.enabled")){
            proxy.getPluginManager().registerListener(instance, new SwitchServer());
            proxy.getPluginManager().registerListener(instance, new ProxyDisconnect());
        }

        if (ConfigManager.getBoolean("redirect server commands.enabled")
                && ConfigManager.getKeys("redirect server commands.commands").size() != 0){
            Collection<String> commands = ConfigManager.getKeys("redirect server commands.commands");
            for (String command : commands){
                Collection<String> commandDetails = ConfigManager.getKeys("redirect server commands.commands."+command);
                if (commandDetails.contains("permission") && commandDetails.contains("aliases") && commandDetails.contains("server")
                        && commandDetails.contains("message") && commandDetails.contains("denymessage")
                        && commandDetails.contains("clear chat before message")){
                    String[] aliases = ConfigManager.getList("redirect server commands.commands."+command+".permission").toArray(new String[0]);
                    proxy.getPluginManager().registerCommand(instance, new SlashServerCommands(command,
                            ConfigManager.getString("redirect server commands.commands."+command+".permission"),
                            ConfigManager.getString("redirect server commands.commands."+command+".server"),
                            ConfigManager.getString("redirect server commands.commands."+command+".message"),
                            ConfigManager.getString("redirect server commands.commands."+command+".denymessage"),
                            ConfigManager.getBoolean("redirect server commands.commands."+command+".clear chat before message"),
                            aliases));
                }
                else log(Level.WARNING, "There was some error with registering command \""+command+"\"! Canceling registration!");
            }

        }

        proxy.getPluginManager().registerListener(instance, new TabComplete());
        //proxy.getPluginManager().registerListener(instance, new ProxyPing());
        //proxy.getPluginManager().registerListener(instance, new ProxyLogin());

        if (ConfigManager.getBoolean("tab.enabled")) for (ProxiedPlayer player : proxy.getPlayers()) {
            player.resetTabHeader();
            ProxyUtils.enableTab(player);
        }

    }


    @Override
    public void onDisable() {
        proxy.getPluginManager().unregisterListeners(this);
        proxy.getPluginManager().unregisterCommands(this);
    }
    public static Main getInstance(){ return instance; }
    public static void log(Level level, String text){
        proxy.getLogger().log(level, text);
    }
}
