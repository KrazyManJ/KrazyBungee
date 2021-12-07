package me.KrazyManJ.KrazyBungee.Commands;

import me.KrazyManJ.KrazyBungee.Main;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import me.KrazyManJ.KrazyBungee.Utils.ProxyUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class TrialStaffChatCommand extends Command {

    public TrialStaffChatCommand(String command, String[] aliases) {
        super(command,"krazybungee.trialstaffchat.write", aliases);
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer sender){
            if (strings.length > 0){
                String message = String.join(" ", strings);
                String staffmsg = ConfigManager.getString("trialstaffchat.format")
                        .replace("{player}", sender.getName())
                        .replace("{server}", sender.getServer().getInfo().getName())
                        .replace("{message}",message);
                List<String> bypass = ConfigManager.getList("trialstaffchat.bypass servers");
                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
                    if (bypass.size() == 0 || !bypass.contains(player.getServer().getInfo().getName())){
                        ProxyUtils.sendPermission(player, "krazybungee.trialstaffchat.see", Format.toBaseComponent(staffmsg));
                    }
                }
            }
            else commandSender.sendMessage(Format.toBaseComponent(ConfigManager.getString("language.trialstaffchat.no message")));
        }
    }
}
