package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import top.mcpbs.games.room.Room;

public class JoinRoom extends Command {
    public JoinRoom(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令!");
        }else{
            Player player = (Player)sender;
            if (!Room.awaiting.containsKey(player) && !Room.aplaying.containsKey(player)){
                for (UHCRoom room : UHCRoom.uhcrooms.values()){
                    if (room.canJoin()){
                        room.joinRoom(player);
                        return true;
                    }
                }
                player.sendMessage("§a未找到可用房间，正在创建新的房间...");
                UHCRoom room = new UHCRoom();
                room.joinRoom(player);
                return true;
            }
            player.sendMessage("§c你已经在一个房间里面了!");
            return true;
        }
        return true;
    }
}
