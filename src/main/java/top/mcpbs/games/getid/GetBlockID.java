package top.mcpbs.games.getid;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;

public class GetBlockID extends Command {
    public GetBlockID(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("此指令只能在游戏内使用");
        } else {
            Player p = (Player)sender;
            double x = Double.parseDouble(strings[0]);
            double y = Double.parseDouble(strings[1]);
            double z = Double.parseDouble(strings[2]);
            Level level = ((Player) sender).getLevel();
            p.sendMessage("此方块的ID为：" + new Position(x,y,z,level).getLevelBlock().getId());
        }
        return true;
    }
}
