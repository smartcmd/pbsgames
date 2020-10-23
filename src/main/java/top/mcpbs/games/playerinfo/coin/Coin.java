package top.mcpbs.games.playerinfo.coin;

import cn.nukkit.Player;
import top.mcpbs.games.playerinfo.PlayerInfoTool;

import java.util.HashMap;

public class Coin {

    public static HashMap<Player, Integer>  coin = new HashMap();

    public static void addCoin(Player player,int num){
        PlayerInfoTool.setInfo(player,"coin",PlayerInfoTool.getInfo(player,"coin",0) + num);
        coin.put(player, PlayerInfoTool.getInfo(player,"coin",0));
    }

    public static void remCoin(Player player,int num){
        PlayerInfoTool.setInfo(player,"coin",PlayerInfoTool.getInfo(player,"coin",0) - num);
        coin.put(player, PlayerInfoTool.getInfo(player,"coin",0));
    }

    public static int getCoinNum(Player player){
        if (!coin.containsKey(player)){
            coin.put(player,PlayerInfoTool.getInfo(player,"coin",0));
        }
        return coin.get(player);
    }
}
