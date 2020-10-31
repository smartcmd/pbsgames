package top.mcpbs.games.playerinfo.score;

import cn.nukkit.Player;
import top.mcpbs.games.playerinfo.PlayerInfoTool;

import java.util.HashMap;

public class Score {
    public static HashMap<Player, Integer> score = new HashMap();

    public static void addScore(Player player, int num){
        PlayerInfoTool.setInfo(player,"main_economic.score",PlayerInfoTool.getInfo(player,"main_economic.score",0) + num);
    }

    public static void remScore(Player player, int num){
        PlayerInfoTool.setInfo(player,"main_economic.score",PlayerInfoTool.getInfo(player,"main_economic.score",0) - num);
    }

    public static int getScore(Player player){
        return PlayerInfoTool.getInfo(player,"main_economic.score",0);
    }
}
