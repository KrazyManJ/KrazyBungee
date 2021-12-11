package me.KrazyManJ.KrazyBungee.Commands;

import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

public class MessageCommand extends Command {
    private final String commandName;
    private final String[] commandArgs;
    public MessageCommand(String name,String... args){
        super(name, "", args);
        commandName = name;
        commandArgs = args;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer player){
            String nodeName = commandName + (commandArgs.length == 0 ? "" : Arrays.toString(commandArgs).replaceAll(" ", ""));
            String[] splittedText = String.join("\n", ConfigManager.getList("message commands.commands."+nodeName)).split("((?<=]})|(?=@(hoverbutton|linkbutton|linkhoverbutton|)\\{\\[))");
            BaseComponent result = new TextComponent("");
            for (String piece : splittedText){
                if (piece.startsWith("@") && piece.matches("^(?s:@(hoverbutton|linkbutton|linkhoverbutton)\\{\\[.+]})")){
                    String buttonType = piece.split("\\{\\[")[0].replaceFirst("@", "");
                    String[] buttonArgs = piece.replaceFirst("]}", "").split("\\{\\[")[1].split("\\(,\\)");
                    BaseComponent button = Format.toBaseComponent(buttonArgs[0]);
                    switch (buttonType) {
                        case "hoverbutton" -> button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Format.toBaseComponent(buttonArgs[1])).create()));
                        case "linkbutton" -> button.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, buttonArgs[1]));
                        case "linkhoverbutton" -> {
                            button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Format.toBaseComponent(buttonArgs[1])).create()));
                            button.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, buttonArgs[2]));
                        }
                    }
                    result.addExtra(button);
                }
                else{
                    result.addExtra(Format.toBaseComponent(piece));
                }
            }
            player.sendMessage(result);
        }
    }
}
