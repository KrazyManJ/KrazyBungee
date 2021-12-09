package me.KrazyManJ.KrazyBungee.Commands;

import me.KrazyManJ.KrazyBungee.Main;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SlashServerCommands extends Command {
    private final ServerInfo server;
    private final boolean clear;
    private final String message;
    private final String denymessage;

    public SlashServerCommands(String name, String permission, String server, String message, String denymessage,boolean clear, String[] aliases) {
        super(name, permission, aliases);
        this.server = Main.getInstance().getProxy().getServerInfo(server);
        this.message = message;
        this.denymessage = denymessage;
        this.clear = clear;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer player){
            if (!player.getServer().getInfo().equals(server)){
                if (clear) for (int i = 0; i < 100; i++) player.sendMessage(new TextComponent(""));
                player.sendMessage(Format.toBaseComponent(message));
                player.connect(server);
            }
            else{
                player.sendMessage(Format.toBaseComponent(denymessage));
            }

        }
    }
}
