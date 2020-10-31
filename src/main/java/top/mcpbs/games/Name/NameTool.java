package top.mcpbs.games.Name;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import top.mcpbs.games.playerinfo.PlayerInfoTool;

import java.util.HashMap;
import java.util.Map;

public class NameTool {

    public static void returnToDefaultName(Player player){
        String ch = getPlayerUseDesignation(player);
        player.setNameTag("§f[" + ch + "§f]" + player.getName());
        player.setDisplayName("§f[" + ch + "§f]" + player.getName());
    }

    public static String getPlayerUseDesignation(Player player){
        HashMap tmp = new HashMap();
        tmp.put("§ePlayer",true);
        HashMap ch = PlayerInfoTool.getInfo(player,"designation",tmp);
        String sch = null;
        for (Object s : ch.keySet()){
            if ((boolean)ch.get(s)){
                sch = (String)s;
            }
        }
        return sch;
    }

    public static boolean setPlayerUseDesignation(Player player,String designation){
        Config c = PlayerInfoTool.playerdata.get(player);
        HashMap<String, Boolean> ch = (HashMap) c.get("designation");
        if (!ch.containsKey(designation)){
            return false;
        }
        for (Map.Entry<String, Boolean> e : ch.entrySet()){
            ch.put(e.getKey(),false);
        }
        ch.put(designation,true);
        returnToDefaultName(player);
        return true;
    }

    public static void setName(Player player,String name){
        player.setNameTag(name);
        player.setDisplayName(name);//重新封装的方法
    }

    public static HashMap<String,Boolean> getPlayerAllDesignation(Player player){
        HashMap tmp = new HashMap();
        tmp.put("§ePlayer",true);
        return PlayerInfoTool.getInfo(player,"designation",tmp);
    }

    public static void PlayerAddDesignation(Player player,String d){
        HashMap tmp = PlayerInfoTool.getInfo(player,"designation",new HashMap());
        tmp.put(d,false);
        PlayerInfoTool.setInfo(player,"designation",tmp);
    }

    @Deprecated//this method is not used very often~
    public static boolean PlayerRemDesignation(Player player,String d){
        HashMap<String, Boolean> tmp = PlayerInfoTool.getInfo(player,"designation",new HashMap());
        if (!tmp.get(d)){
            return false;
        }
        tmp.remove(d);
        PlayerInfoTool.setInfo(player,"designation",tmp);
        return true;
    }
}
