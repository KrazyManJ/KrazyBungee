package me.KrazyManJ.KrazyBungee.Commands;

import me.KrazyManJ.KrazyBungee.Main;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;
import java.util.logging.Level;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super("krazybungeestaffutils", "krazybungeestaffutils.admin", "kbsu");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        try {
            ConfigManager.createConfig();
            Main.reload();
            if (commandSender instanceof ProxiedPlayer)
                ((ProxiedPlayer)commandSender).sendMessage(Format.toBaseComponent("Plugin was successfully reloaded!"));
            ProxyServer.getInstance().getLogger().log(Level.INFO, "Plugin was successfully reloaded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
