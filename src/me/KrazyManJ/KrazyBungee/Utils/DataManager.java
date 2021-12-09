package me.KrazyManJ.KrazyBungee.Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.KrazyManJ.KrazyBungee.Main;

import java.io.*;
import java.util.logging.Level;

public class DataManager {
    private static JsonObject data;
    private static File datafile = new File(Main.getInstance().getDataFolder()+"/data.json");

    private static JsonObject defaults(){
        JsonObject def = new JsonObject();
        def.addProperty("maintenance", false);

        return def;
    }

    public static void readData(){
        if (!datafile.exists()) {
            try (FileWriter writer = new FileWriter(datafile.getAbsoluteFile())){
                writer.write(defaults().toString());
                writer.flush();
            } catch (IOException e) { e.printStackTrace(); }
        }
        JsonParser parser = new JsonParser();
        try {
            data = (JsonObject) parser.parse(new FileReader(datafile.getAbsoluteFile()));
        } catch (FileNotFoundException e) { e.printStackTrace(); }
    }
    public static void saveData(){
        try (FileWriter writer = new FileWriter(datafile.getAbsoluteFile())){
            writer.write(data.toString());
            writer.flush();
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static void setString(String name, String value){
        data.remove(name);
        data.addProperty(name, value);
    }
    public static boolean getBoolean(String name) { return data.get(name).getAsBoolean(); }
    public static void setBoolean(String name, boolean value) {
        data.remove(name);
        data.addProperty(name, value);
    }
}
