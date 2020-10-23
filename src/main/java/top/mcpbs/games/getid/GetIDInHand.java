package top.mcpbs.games.getid;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;

public class GetIDInHand extends Command {
    public GetIDInHand(String name, String description) {
        super(name, description);
    }

    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("此指令只能在游戏内使用");
            return true;
        } else {
            Player p = (Player)sender;
            p.sendMessage("此物品的ID为：" + p.getInventory().getItemInHand().getId());
        }
        return true;
    }
}
