package top.mcpbs.games.duel;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.room.Room;

import java.util.HashMap;
import java.util.Map;

public class LoadCmd {
    public static void loadCmd(){
        Main.plugin.getLogger().info("正在注册决斗小游戏指令...");
        Config duelmode = new Config(Main.plugin.getDataFolder() + "/duelmode.yml");
        HashMap<String, Object> mode = (HashMap) duelmode.getAll();
        for (Map.Entry e : mode.entrySet()){
            String cmddescription = (String) e.getKey();
            if (((HashMap)e.getValue()).containsKey("cmddescription")) {
                cmddescription = (String) ((HashMap) e.getValue()).get("cmddescription");
            }
            Server.getInstance().getCommandMap().register("",new cmd((String) e.getKey(),cmddescription));
            Main.plugin.getLogger().info("成功注册指令: " + e.getKey());
        }
        Main.plugin.getLogger().info("决斗小游戏指令注册完成!");
    }
}

class cmd extends Command{

    public cmd(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        Player player = (Player)sender;
        player.sendMessage("§a正在匹配房间...");
        if (!Room.awaiting.containsKey(player) && !Room.aplaying.containsKey(player)){
            for (DuelRoom room : DuelRoom.duelrooms.values()){
                if (room.mode.equals(this.getName()) && room.canJoin() == true){
                    room.joinRoom(player);
                    return true;
                }
            }
            player.sendMessage("§a未找到可用房间，正在创建新的房间...");
            DuelRoom room = new DuelRoom(this.getLabel());
            room.joinRoom(player);
            return true;
        }
        player.sendMessage("§c你目前不能加入此游戏!");
        return true;
    }
}
