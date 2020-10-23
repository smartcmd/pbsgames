package top.mcpbs.games.playerinfo.score;

import cn.nukkit.Player;
import top.mcpbs.games.playerinfo.PlayerInfoTool;

import java.util.HashMap;

public class Score {
    public static HashMap<Player, Integer> score = new HashMap();

    public static void addScore(Player player, int num){
        PlayerInfoTool.setInfo(player,"score",PlayerInfoTool.getInfo(player,"score",0) + num);
        score.put(player, PlayerInfoTool.getInfo(player,"score",0));
    }

    public static void remScore(Player player, int num){
        PlayerInfoTool.setInfo(player,"score",PlayerInfoTool.getInfo(player,"score",0) - num);
        score.put(player, PlayerInfoTool.getInfo(player,"score",0));
    }

    public static int getScore(Player player){
        if (!score.containsKey(player)){
            score.put(player,PlayerInfoTool.getInfo(player,"score",0));
        }
        return score.get(player);
    }
}
