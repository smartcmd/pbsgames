package top.mcpbs.games.designation;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;

import java.util.HashMap;

public class SetName {
    public static void ReloadToDefaultDesignation(Player player){
        Config c = new Config(Main.plugin.getDataFolder() + "/playerdata/" + player.getName() + ".yml");
        String sch = null;//重新读取数据
        HashMap ch = (HashMap) c.get("ch");
        for (Object s : ch.keySet()){
            if ((boolean)ch.get(s)){
                sch = (String)s;
            }
        }
        player.setNameTag("§f[" + sch + "§f]" + player.getName());
        player.setDisplayName("§f[" + sch + "§f]" + player.getName());
    }

    public static void setName(Player player,String name){
        player.setNameTag(name);
        player.setDisplayName(name);//重新封装的方法
    }
}
