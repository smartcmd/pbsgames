package top.mcpbs.games.playerinfo.diamond;

import cn.nukkit.Player;
import top.mcpbs.games.playerinfo.PlayerInfoTool;

import java.util.HashMap;

public class Diamond {

    public static HashMap<Player, Integer>  diamond = new HashMap();

    public static void addDiamond(Player player,int num){
        PlayerInfoTool.setInfo(player,"main_economic.diamond",PlayerInfoTool.getInfo(player,"diamond",0) + num);
    }

    public static void remDiamond(Player player,int num){
        PlayerInfoTool.setInfo(player,"main_economic.diamond",PlayerInfoTool.getInfo(player,"diamond",0) - num);
    }

    public static int getDiamondNum(Player player){
        return PlayerInfoTool.getInfo(player,"main_economic.diamond",0);
    }
}
