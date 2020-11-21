package top.mcpbs.games.pbc;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;

public class AddPBC extends Command {

    public AddPBC(String name, String description) {
        super(name, description);
        this.setPermission("op");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令");
            return true;
        }else {
            Config c = new Config(Main.plugin.getDataFolder() + "/屏蔽词.yml");
            c.set(strings[0], strings[1]);
            c.save();
            sender.sendMessage("§a已添加屏蔽词:§e " + strings[0] + " §areplace to§e " + strings[1]);
        }
        return true;
    }
}
