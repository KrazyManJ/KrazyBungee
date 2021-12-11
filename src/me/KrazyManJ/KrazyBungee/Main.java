package me.KrazyManJ.KrazyBungee;

import me.KrazyManJ.KrazyBungee.Commands.*;
import me.KrazyManJ.KrazyBungee.Core.Maintenance;
import me.KrazyManJ.KrazyBungee.Listeners.*;
import me.KrazyManJ.KrazyBungee.Listeners.Luckperms.*;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.DataManager;
import me.KrazyManJ.KrazyBungee.Utils.ProxyUtils;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Plugin {
    private static ProxyServer proxy;
    private static Main instance;
    @Override
    public void onEnable() {
        instance = this;
        proxy = getProxy();
        DataManager.readData();
        try { ConfigManager.createConfig(); } catch (IOException e) { e.printStackTrace(); }
        reload();
        super.onEnable();
    }

    public static void reload(){
        proxy.getPluginManager().unregisterListeners(instance);
        proxy.getPluginManager().unregisterCommands(instance);
        proxy.getPluginManager().registerCommand(instance, new CoreCommand());
        proxy.getPluginManager().registerListener(instance, new TabComplete());

        //COMMANDS
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

        if (ConfigManager.getBoolean("redirect server commands.enabled") && ConfigManager.getKeys("redirect server commands.commands").size() != 0){
            Collection<String> commands = ConfigManager.getKeys("redirect server commands.commands");
            for (String command : commands){
                Collection<String> commandDetails = ConfigManager.getKeys("redirect server commands.commands."+command);
                if (commandDetails.contains("permission") && commandDetails.contains("aliases") && commandDetails.contains("server")
                        && commandDetails.contains("message") && commandDetails.contains("denymessage")
                        && commandDetails.contains("clear chat before message")){
                    String[] aliases = ConfigManager.getList("redirect server commands.commands."+command+".permission").toArray(new String[0]);
                    proxy.getPluginManager().registerCommand(instance, new RedirectCommand(command,
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
        if (ConfigManager.getBoolean("message commands.enabled") && ConfigManager.getKeys("message commands.commands").size() != 0){
            for (String command : ConfigManager.getKeys("message commands.commands")){
                Matcher match = Pattern.compile("\\[([a-zA-Z]+,)*[a-zA-Z]+]").matcher(command);
                if (match.find()) {
                    String[] aliases = command.replace("]", "").split("\\[")[1].split(",");
                    command = command.split("\\[")[0];
                    Main.getInstance().getProxy().getPluginManager().registerCommand(Main.getInstance(), new MessageCommand(command, aliases));
                    Main.getInstance().getLogger().log(Level.INFO, "Command "+command+" with aliases "+ Arrays.toString(aliases) +" was successfully registered!");
                }
                else if (command.contains(" ")) Main.getInstance().getLogger().log(Level.WARNING, "Command "+command+" was not registered, because it contains space!");
                else {
                    Main.getInstance().getProxy().getPluginManager().registerCommand(Main.getInstance(), new MessageCommand(command));
                    Main.getInstance().getLogger().log(Level.INFO, "Command "+command+" was successfully registered!");
                }
            }
        }


        //LISTENERS
        if (ConfigManager.getBoolean("staff connect status.enabled")){
            proxy.getPluginManager().registerListener(instance, new SwitchServer());
            proxy.getPluginManager().registerListener(instance, new ProxyDisconnect());
        }

        if(ConfigManager.getBoolean("motd.enabled")){
            proxy.getPluginManager().registerListener(instance, new ProxyPing());
        }

        if(ConfigManager.getBoolean("maintenance.enabled")){
            Maintenance.setMaintenance((boolean)DataManager.getBoolean("maintenance"), false);
            proxy.getPluginManager().registerListener(instance, new ProxyLogin());
            if (proxy.getPluginManager().getPlugin("LuckPerms") != null){
                new PermissionCalculate(instance, LuckPermsProvider.get());
                log(Level.INFO,"Successfully hooked to Luckperms API through Maintenance!");
            }
        }
        else if (Maintenance.isMaintenance()) Maintenance.setMaintenance(false, false);

        if (ConfigManager.getBoolean("tab.enabled")) for (ProxiedPlayer player : proxy.getPlayers()) {
            player.resetTabHeader();
            ProxyUtils.enableTab(player);
        }
    }


    @Override
    public void onDisable() {
        proxy.getPluginManager().unregisterListeners(this);
        proxy.getPluginManager().unregisterCommands(this);
        DataManager.saveData();
    }
    public static Main getInstance(){ return instance; }
    public static void log(Level level, String text){
        instance.getLogger().log(level, text);
    }
}
