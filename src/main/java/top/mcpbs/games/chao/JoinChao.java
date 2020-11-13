package top.mcpbs.games.chao;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.item.Item;
import top.mcpbs.games.name.NameTool;
import top.mcpbs.games.playerinfo.PlayerInfoTool;
import top.mcpbs.games.room.Room;

public class JoinChao extends Command {

    public JoinChao(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("控制台不能使用此指令!");
            return true;
        } else {
            Player player = (Player) sender;
            if (!Room.awaiting.containsKey(player) && !Room.aplaying.containsKey(player)) {
                player.sendMessage("§a»成功加入大乱斗");
                player.teleport(Chao.spawn);//tp

                NameTool.setPlayerDisplayName(player,"§7" + "/name/");
                NameTool.setPlayerNameTag(player,"§7" + "/name/" + "\n" + "/health/" + " §c❤");

                player.setHealth(PlayerInfoTool.getInfo(player, "chao.health", 20));//sethealth

                player.getInventory().clearAll();
                Item hub = Item.get(355, 0, 1);
                hub.setCustomName("退出等待");
                player.getInventory().setItem(2, hub);//giveitem

                Chao.players.put(player, false);
                Room.aplaying.put(player, null);

                return true;
            }
            player.sendMessage("§c»你目前不能加入大乱斗");
            return true;
        }
    }
}
