package top.mcpbs.games.particle;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import top.mcpbs.games.playerinfo.PlayerInfoTool;

import java.util.HashMap;
import java.util.Map;

public class ParticleTool {

    public static HashMap<String, Boolean> getPlayerAllParticle(Player player){
        HashMap<String, Boolean> result = PlayerInfoTool.getInfo(player,"particle.list",new HashMap<>());
        return result;
    }

    public static void addPlayerParticle(Player player,String pname){
        HashMap<String, Boolean> plist = PlayerInfoTool.getInfo(player,"particle.list",new HashMap<>());
        plist.put(pname,false);
        PlayerInfoTool.setInfo(player,"particle.list",plist);
        player.sendMessage("§d粒子特效 §7» §a你获得了一个新粒子特效!");
    }

    @Deprecated//this method is not used very often
    public static void remPlayerParticle(Player player,String pname){
        PlayerInfoTool.remInfo(player,"pparticle.list." + pname);
    }

    public static int getPlayerParticleSpeed(Player player){
        return PlayerInfoTool.getInfo(player,"particle.speed",2);
    }

    public static void setPlayerParticleSpeed(Player player,int speed){
        PlayerInfoTool.setInfo(player,"particle.speed",speed);
    }

    public static String getPlayerUseParticle(Player player){
        HashMap p = PlayerInfoTool.getInfo(player,"particle.list",new HashMap());
        String up = null;
        for (Object s : p.keySet()){
            if ((boolean)p.get(s)){
                up = (String)s;
            }
        }
        return up;
    }

    public static boolean setPlayerUseParticle(Player player,String use){
        Config c = PlayerInfoTool.playerdata.get(player);
        HashMap<String, Boolean> p = (HashMap) c.get("particle.list");
        if (!p.containsKey(use)){
            return false;
        }
        for (Map.Entry<String, Boolean> e : p.entrySet()){
            p.put(e.getKey(),false);
        }
        p.put(use,true);
        return true;
    }
}
