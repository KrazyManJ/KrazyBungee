package me.KrazyManJ.KrazyBungee.Utils;

import com.google.common.io.ByteStreams;
import me.KrazyManJ.KrazyBungee.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ConfigManager {
    private static File configContainer;
    private static Configuration configData;

    public static void createConfig() throws IOException {
        if (!Main.getInstance().getDataFolder().exists()) Main.getInstance().getDataFolder().mkdir();
        configContainer = new File(Main.getInstance().getDataFolder().getPath(), "config.yml");
        if (!configContainer.exists()){
            configContainer.createNewFile();
            copyDefaults("config.yml", configContainer);
        }
        configData = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configContainer);
    }
    private static void copyDefaults(String path, File file) throws IOException {
        InputStream in = Main.getInstance().getResourceAsStream(path);
        OutputStream out = new FileOutputStream(file);
        ByteStreams.copy(in, out);
    }
    private static void writeConfig(){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configData,configContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getString(String path) { return configData.getString(path); }
    public static List<String> getList(String path){
        return configData.getStringList(path);
    }
    public static boolean getBoolean(String path) { return configData.getBoolean(path);}
    public static Collection<String> getKeys(String path) {return configData.getSection(path).getKeys();}

}

