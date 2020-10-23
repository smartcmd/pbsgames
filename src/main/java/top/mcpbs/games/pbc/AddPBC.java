package top.mcpbs.games.pbc;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;

public class AddPBC extends Command {

    Plugin plugin;

    public AddPBC(String name, String description,Plugin plugin) {
        super(name, description);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令");
            return true;
        }else {
            Config c = new Config(plugin.getDataFolder() + "/屏蔽词.yml");
            c.set(strings[0], strings[1]);
            c.save();
            sender.sendMessage("§a已添加屏蔽词:§e " + strings[0] + " §areplace to§e " + strings[1]);
        }
        return true;
    }
}
