package top.mcpbs.games.hub;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.plugin.Plugin;
import top.mcpbs.games.Main;

public class Hub extends Command {

    Plugin plugin;

    public Hub(String name, String description, Plugin plugin) {
        super(name, description);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("控制台不能使用/hub指令");
            return true;
        } else if (sender.isPlayer()) {
            Player player = (Player) sender;
            player.teleport(Main.lobby);
            player.getInventory().clearAll();
        }
        return true;
    }
}