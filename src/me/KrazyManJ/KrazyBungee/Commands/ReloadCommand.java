package me.KrazyManJ.KrazyBungee.Commands;

import me.KrazyManJ.KrazyBungee.Main;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;
import java.util.logging.Level;

import static me.KrazyManJ.KrazyBungee.Main.log;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super("krazybungees", "krazybungee.admin", "kb");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        try {
            ConfigManager.createConfig();
            Main.reload();
            if (commandSender instanceof ProxiedPlayer player) player.sendMessage(Format.toBaseComponent("Plugin was successfully reloaded!"));
            log(Level.INFO, "Plugin was successfully reloaded!");
        } catch (IOException e) {
            e.printStackTrace();
            if (commandSender instanceof ProxiedPlayer player) player.sendMessage(Format.toBaseComponent("&cThere was problem with reloading!"));
        }
    }
}
