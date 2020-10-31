package top.mcpbs.games.playerinfo;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;

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
}
