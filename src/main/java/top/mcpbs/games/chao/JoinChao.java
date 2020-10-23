package top.mcpbs.games.chao;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.room.Room;

import java.util.ArrayList;
import java.util.HashMap;

public class JoinChao extends Command {

    public JoinChao(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令!");
            return true;
        }else{
            ArrayList l = (ArrayList) ChaoTask.chaoconfig.get("spawn");
            Player player = (Player)sender;
            if (!Room.awaiting.containsKey(player) && !Room.aplaying.containsKey(player)) {
                if (!Server.getInstance().isLevelLoaded((String) ChaoTask.chaoconfig.get("world"))){
                    Server.getInstance().loadLevel((String) ChaoTask.chaoconfig.get("world"));
                }
                player.sendMessage("§a成功加入大乱斗");
                player.teleport(new Position((double) l.get(0), (double) l.get(1), (double) l.get(2), Server.getInstance().getLevelByName((String) ChaoTask.chaoconfig.get("world"))));
                player.getInventory().clearAll();
                Config pc = new Config(Main.plugin.getDataFolder() + "/playerdata/" + player.getName() + ".yml");
                HashMap<String, Number> pi = new HashMap();
                pi.put("coin", (double)((HashMap)pc.get("chao")).get("coin"));
                pi.put("kill", (int) ((HashMap)pc.get("chao")).get("kill"));
                pi.put("death", (int) ((HashMap)pc.get("chao")).get("death"));
                pi.put("draw rate", (double) ((HashMap)pc.get("chao")).get("draw rate"));
                pi.put("health", (int) ((HashMap)pc.get("chao")).get("health"));
                player.setMaxHealth((int)pi.get("health"));
                player.setHealth(player.getMaxHealth());
                ChaoTask.chaoplayerinfo.put(player,pi);
                ChaoTask.players.put(player, false);
                Room.aplaying.put(player,null);
                Item hub = Item.get(355,0,1);
                hub.setCustomName("退出等待");
                player.getInventory().setItem(2,hub);
                return true;
            }
            player.sendMessage("§c你已经在一个房间里面了");
        }
        return true;
    }
}
