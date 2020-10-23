package top.mcpbs.games.rush;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import top.mcpbs.games.room.Room;

public class JoinRoom extends Command {
    Plugin plugin;

    public JoinRoom(String name, String description,Plugin plugin) {
        super(name, description);
        this.plugin = plugin;
    }
    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        Player player = (Player)sender;
        if (!Room.awaiting.containsKey(player) && !Room.aplaying.containsKey(player)){
            for (RushRoom room : RushRoom.RushRooms.values()){
                player.sendMessage("§a匹配房间中...");
                if (room.canJoin() == true){
                    room.joinRoom(player);
                    return true;
                }
            }
            player.sendMessage("§a未找到可用房间，正在创建新的房间...");
            RushRoom room = new RushRoom();
            room.joinRoom(player);
            return true;
        }
        player.sendMessage("§c你已经在一个房间里面了!");
        return true;
    }
}
