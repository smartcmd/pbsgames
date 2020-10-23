package top.mcpbs.games.playerinfo.diamond;

import cn.nukkit.Player;
import top.mcpbs.games.playerinfo.PlayerInfoTool;

import java.util.HashMap;

public class Diamond {

    public static HashMap<Player, Integer>  diamond = new HashMap();

    public static void addDiamond(Player player,int num){
        PlayerInfoTool.setInfo(player,"diamond",PlayerInfoTool.getInfo(player,"diamond",0) + num);
        diamond.put(player, PlayerInfoTool.getInfo(player,"diamond",0));
    }

    public static void remDiamond(Player player,int num){
        PlayerInfoTool.setInfo(player,"diamond",PlayerInfoTool.getInfo(player,"diamond",0) - num);
        diamond.put(player, PlayerInfoTool.getInfo(player,"diamond",0));
    }

    public static int getDiamondNum(Player player){
        if (!diamond.containsKey(player)){
            diamond.put(player,PlayerInfoTool.getInfo(player,"diamond",0));
        }
        return diamond.get(player);
    }
}
