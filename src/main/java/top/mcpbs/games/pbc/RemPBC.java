package top.mcpbs.games.pbc;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;

public class RemPBC extends Command {

    public RemPBC(String name, String description) {
        super(name, description);

    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令");
            return true;
        }else {
            Config c = new Config(Main.plugin.getDataFolder() + "/屏蔽词.yml");
            c.remove(strings[0]);
            c.save();
            sender.sendMessage("§a已删除屏蔽词:§e " + strings[0]);
        }
        return true;
    }
}
