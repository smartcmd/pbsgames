package top.mcpbs.games.playerinfo;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;

import java.io.File;
import java.util.HashMap;

public class PlayerInfoTool {

    public static HashMap<Player, Config> playerdata = new HashMap<>();

    public static<T> T getInfo(Player p, String key, T definfo){
        if (!playerdata.containsKey(p)){
            joinPlayerDataMap(p);
        }
        Config config = playerdata.get(p);
        if (!config.exists(key)){
            config.set(key,definfo);
            config.save();
        }
        return (T)config.get(key);
    }

    public static<T> boolean setOfflinePlayerInfo(String p, String key,T info){
        if(isHasConfig(p) == false){
            return false;
        }
        Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + p + ".yml");
        config.set(key,info);
        config.save();
        return true;
    }

    public static boolean remOfflinePlayerInfo(String p,String key){
        if(isHasConfig(p) == false){
            return false;
        }
        Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + p + ".yml");
        config.remove(key);
        config.save();
        return true;
    }

    public static<T> T getOfflinePlayerInfo(String p, String key, T definfo){//can null
        if(isHasConfig(p) == false){
            return null;
        }
        Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + p + ".yml");
        if (!config.exists(key)){
            config.set(key,definfo);
            config.save();
        }
        return (T)config.get(key);
    }

    public static<T> void setInfo(Player p, String key,T info){
        if (!playerdata.containsKey(p)){
            joinPlayerDataMap(p);
        }
        Config config = playerdata.get(p);
        config.set(key,info);
        config.save();
    }

    public static void remInfo(Player p,String key){
        if (!playerdata.containsKey(p)){
            joinPlayerDataMap(p);
        }
        Config config = playerdata.get(p);
        config.remove(key);
        config.save();
    }


    public static void joinPlayerDataMap(Player player){
        Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + player.getName() + ".yml");
        playerdata.put(player, config);
    }

    public static boolean isHasConfig(String player){
        File f = new File(Main.plugin.getDataFolder() + "/playerdata/" + player + ".yml");
        return f.exists();
    }
}
