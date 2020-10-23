package top.mcpbs.games.playerinfo;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;

import java.io.File;

public class PlayerInfoTool {
    public static<T> T getInfo(Player p, String key, T definfo){
        testFolder();
        Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + p.getName() + ".yml");
        if (!config.exists(key)){
            config.set(key,definfo);
        }
        return (T)config.get(key);
    }

    public static<T> void setInfo(Player p, String key,T info){
        testFolder();
        Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + p.getName() + ".yml");
        config.set(key,info);
        config.save();
    }

    public static void testFolder(){
        File f = new File(Main.plugin.getDataFolder() + "/playerdata");
        if (!f.exists()){
            f.mkdir();
        }
    }
}
