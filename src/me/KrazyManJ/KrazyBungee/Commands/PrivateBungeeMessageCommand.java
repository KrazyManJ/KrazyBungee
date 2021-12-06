package me.KrazyManJ.KrazyBungee.Commands;

import me.KrazyManJ.KrazyBungee.Main;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import me.KrazyManJ.KrazyBungee.Utils.ProxyUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;
import java.util.List;

public class PrivateBungeeMessageCommand extends Command {
    private static Command command;

    public PrivateBungeeMessageCommand(String command, String[] aliases){
        super(command,"",aliases);
        PrivateBungeeMessageCommand.command = this;
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer sender){
            if (strings.length >= 2){
                List<String> args = Arrays.stream(strings).toList();
                ProxiedPlayer receiver = Main.getInstance().getProxy().getPlayer(args.remove(0));
                String message = String.join(" ", args);
                String bmsg = ConfigManager.getString("private bungee message.format")
                        .replace("{sender}", sender.getName())
                        .replace("{receiver}", receiver.getName())
                        .replace("{message}",message);
                List<String> bypass = ConfigManager.getList("private bungee message.bypass servers");
                if (Main.getInstance().getProxy().getPlayers().contains(receiver)){
                    if (bypass.size() == 0 || !bypass.contains(sender.getServer().getInfo().getName())){
                        if (bypass.size() == 0 || !bypass.contains(receiver.getServer().getInfo().getName())){
                            ProxyUtils.sendPermission(receiver, "krazybungeestaffutils.trialstaffchat.see", Format.toBaseComponent(bmsg));
                            ProxyUtils.sendPermission(sender, "krazybungeestaffutils.trialstaffchat.see", Format.toBaseComponent(bmsg));
                        }
                    }
                }
            }
        }
    }
    public static void unregister(){
        if (command != null) Main.getInstance().getProxy().getPluginManager().unregisterCommand(command);
    }
}
