package top.mcpbs.games.laba;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.plugin.Plugin;
import top.mcpbs.games.playerinfo.coin.Coin;

public class LabaCmd extends Command {

    Plugin plugin;

    public LabaCmd(String name, String description,Plugin plugin) {
        super(name, description);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令!");
            return true;
        }else{
            Player p = (Player)sender;
            if (strings == null){
                p.sendMessage("§c你还没输要说的话呢!");
                return true;
            }
            if (Coin.getCoinNum(p) < 1){
                p.sendMessage("§c硬币不足，无法完成支付!");
                return true;
            }
            if (Coin.getCoinNum(p) >= 1){
                Coin.remCoin(p,1);
                p.sendMessage("§a成功支付1硬币!");
                String ss = "";
                for (String sss : strings){
                    ss += (" " + sss);
                }
                Server.getInstance().broadcastMessage("§l§d小喇叭>> " + "§7#" + p.getLevel().getName() + " §f" + p.getName() + ":" + ss);
            }
        }
        return true;
    }
}
