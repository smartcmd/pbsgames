package top.mcpbs.games.name;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import top.mcpbs.games.playerinfo.PlayerInfoTool;

import java.util.HashMap;
import java.util.Map;

public class NameTool {

    public static HashMap<Player,String> playernametag = new HashMap<>();
    public static HashMap<Player,String> playerdisplayname = new HashMap<>();

    public static String getPlayerUseDesignation(Player player){
        HashMap<String,Boolean> tmp = new HashMap();
        tmp.put("§ePlayer",true);
        HashMap<String,Boolean> ch = PlayerInfoTool.getInfo(player,"designation",tmp);
        String sch = null;
        for (String s : ch.keySet()){
            if (ch.get(s)){
                sch = s;
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
        return true;
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
        player.sendMessage("§e称号 §7» §a你获得了一个新称号!");
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

    public static void setPlayerNameTag(Player player,String nametag){
        playernametag.put(player,nametag);
    }

    public static void setPlayerDisplayName(Player player,String displayname){
        playerdisplayname.put(player,displayname);
    }
    /**
     * /coin/
     * /score/
     * /diamond/
     * /health/
     * /ch/
     * /name/
     * /maxhealth/
     */
}
