package me.KrazyManJ.KrazyBungee.Utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Format {
    public static BaseComponent toBaseComponent(String text){
        String rg = "(&#[0-9a-f]{6}|&x(&[0-9a-f]){6})";
        if (!Pattern.compile(rg).matcher(text).find()) return new TextComponent(ChatColor.translateAlternateColorCodes('&',text));
        List<String> S = new LinkedList<>(Arrays.asList(text.split("(?i)(?="+rg+")")));
        BaseComponent r;
        if (Pattern.compile(rg).matcher(S.get(0)).find()) r = new TextComponent("");
        else r = new TextComponent(ChatColor.translateAlternateColorCodes('&',S.remove(0)));
        for (String s:S){
            String[] c = s.split("(?i)(?<="+rg+")");
            BaseComponent C = new TextComponent(ChatColor.translateAlternateColorCodes('&',c[1]));
            C.setColor(ChatColor.of(c[0].replaceAll("&","").replace("x","#")));
            r.addExtra(C);
        }
        return r;
    }
}
