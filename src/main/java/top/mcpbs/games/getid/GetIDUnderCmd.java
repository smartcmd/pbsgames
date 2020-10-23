package top.mcpbs.games.getid;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;

public class GetIDUnderCmd extends Command {
    public GetIDUnderCmd(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("此指令只能在游戏内使用");
        } else {
            Player p = (Player)sender;
            p.sendMessage("此物品的ID为：" + p.getLevel().getBlock(p.getLocation().add(0,-1,0)));
        }
        return true;
    }
}
