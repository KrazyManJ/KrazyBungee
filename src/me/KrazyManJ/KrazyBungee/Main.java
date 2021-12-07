package me.KrazyManJ.KrazyBungee;

import me.KrazyManJ.KrazyBungee.Commands.PrivateBungeeMessageCommand;
import me.KrazyManJ.KrazyBungee.Commands.StaffChatCommand;
import me.KrazyManJ.KrazyBungee.Commands.ReloadCommand;
import me.KrazyManJ.KrazyBungee.Commands.TrialStaffChatCommand;
import me.KrazyManJ.KrazyBungee.Listeners.ProxyDisconnect;
import me.KrazyManJ.KrazyBungee.Listeners.SwitchServer;
import me.KrazyManJ.KrazyBungee.Listeners.TabComplete;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;

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

        proxy.getPluginManager().registerListener(instance, new TabComplete());
    }


    @Override
    public void onDisable() {
        proxy.getPluginManager().unregisterListeners(this);
        proxy.getPluginManager().unregisterCommands(this);
    }
    public static Main getInstance(){ return instance; }
}
