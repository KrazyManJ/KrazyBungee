package me.KrazyManJ.KrazyBungee.Core;

import me.KrazyManJ.KrazyBungee.Main;
import me.KrazyManJ.KrazyBungee.Utils.ConfigManager;
import me.KrazyManJ.KrazyBungee.Utils.DataManager;
import me.KrazyManJ.KrazyBungee.Utils.Format;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Maintenance {
    private static boolean maintenance = false;

    public static void kickNotAllowed(){
        if (isMaintenance()){
            for (ProxiedPlayer p : Main.getInstance().getProxy().getPlayers())
                if (!p.hasPermission("krazybungee.maintenance.bypass")) p.disconnect(Format.toBaseComponent(kickMessage));
        }
    }
    public static boolean isMaintenance(){ return maintenance; }
    public static void setMaintenance(boolean value, boolean broadcast){
        maintenance = value;
        DataManager.setBoolean("maintenance", value);
        if (broadcast){
            String message = value ? ConfigManager.getString("language.maintenance.broadcast on") :
                    ConfigManager.getString("language.maintenance.broadcast off");
            Main.getInstance().getProxy().broadcast(Format.toBaseComponent(message));
        }
    }
    public static String kickMessage = String.join("\n", ConfigManager.getList("maintenance.kick message"));

}
