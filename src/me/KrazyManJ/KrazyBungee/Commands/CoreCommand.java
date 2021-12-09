package me.KrazyManJ.KrazyBungee.Commands;

import me.KrazyManJ.KrazyBungee.Core.Maintenance;
import me.KrazyManJ.KrazyBungee.Main;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.DataManager;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;
import java.util.logging.Level;

import static me.KrazyManJ.KrazyBungee.Main.log;

public class CoreCommand extends Command {
    public CoreCommand() {
        super("krazybungee", "krazybungee.admin", "kb");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length != 0){
            if (strings[0].equals("reload")) {
                try {
                    ConfigManager.createConfig();
                } catch (IOException e) {
                    e.printStackTrace();
                    if (commandSender instanceof ProxiedPlayer player) player.sendMessage(Format.toBaseComponent("&cThere was problem with reloading!"));
                }
                DataManager.saveData();
                DataManager.readData();
                Main.reload();
                if (commandSender instanceof ProxiedPlayer player) player.sendMessage(Format.toBaseComponent("Plugin was successfully reloaded!"));
                log(Level.INFO, "Plugin was successfully reloaded!");
            }
            else if (strings[0].equals("maintenance")){
                if (ConfigManager.getBoolean("maintenance.enabled")){
                    if (strings.length > 1){
                        if (strings[1].equals("on") || strings[1].equals("off")){
                            if (strings[1].equals("on")){
                                if (!Maintenance.isMaintenance()) Maintenance.setMaintenance(true, true);
                                else commandSender.sendMessage(Format.toBaseComponent(ConfigManager.getString("language.maintenance.already on")));
                            }
                            else {
                                if (Maintenance.isMaintenance()) Maintenance.setMaintenance(false, true);
                                else commandSender.sendMessage(Format.toBaseComponent(ConfigManager.getString("language.maintenance.already off")));
                            }
                        }
                        else if (strings[1].equals("status")){
                            if (Maintenance.isMaintenance()) commandSender.sendMessage(Format.toBaseComponent("&cMaintenance status: on"));
                            else commandSender.sendMessage(Format.toBaseComponent("&cMaintenance status: off"));
                        }
                    } else commandSender.sendMessage(Format.toBaseComponent("&cYou have to provide some argument!"));
                }
                else commandSender.sendMessage(Format.toBaseComponent(ConfigManager.getString("language.not enabled")));
            }
        }
    }
}
