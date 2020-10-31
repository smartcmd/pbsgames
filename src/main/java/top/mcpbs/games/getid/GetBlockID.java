package top.mcpbs.games.getid;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;

public class GetBlockID extends Command {
    public GetBlockID(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("此指令只能在游戏内使用");
        } else {
            if (strings[0].equals("u")) {
                Player player = (Player)sender;
                double x = player.getX();
                double y = player.getY() - 1;
                double z = player.getZ();
                Level level = ((Player) sender).getLevel();
                player.sendMessage("此方块的ID为：" + new Position(x, y, z, level).getLevelBlock().toString());

            }else{
                double x = Double.parseDouble(strings[0]);
                double y = Double.parseDouble(strings[1]);
                double z = Double.parseDouble(strings[2]);
                Level level = ((Player) sender).getLevel();
                sender.sendMessage("此方块的ID为：" + new Position(x, y, z, level).getLevelBlock().toString());
            }
        }
        return true;
    }
}
