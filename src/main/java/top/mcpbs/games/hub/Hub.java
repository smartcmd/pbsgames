package top.mcpbs.games.hub;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import top.mcpbs.games.lobby.LobbyTool;

public class Hub extends Command {

    public Hub(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("控制台不能使用/hub指令");
            return true;
        } else if (sender.isPlayer()) {
            Player player = (Player) sender;
            LobbyTool.returnToLobby(player);
        }
        return true;
    }
}