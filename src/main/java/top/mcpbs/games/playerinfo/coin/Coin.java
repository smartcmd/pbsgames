package top.mcpbs.games.playerinfo.coin;

import cn.nukkit.Player;
import top.mcpbs.games.playerinfo.PlayerInfoTool;

public class Coin {

    public static void addCoin(Player player,int num){
        PlayerInfoTool.setInfo(player,"main_economic.coin",PlayerInfoTool.getInfo(player,"main_economic.coin",0) + num);
        player.sendMessage("§e硬币 §7» §a你增加了" + num + "个硬币");
    }

    public static void remCoin(Player player,int num){
        PlayerInfoTool.setInfo(player,"main_economic.coin",PlayerInfoTool.getInfo(player,"main_economic.coin",0) - num);
        player.sendMessage("§e硬币 §7» §6你扣除了" + num + "个硬币");
    }

    public static int getCoinNum(Player player){
        return PlayerInfoTool.getInfo(player,"main_economic.coin",0);
    }
}
