package top.mcpbs.games.npc;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class GetNearestNPCIDCMD extends Command {
    public GetNearestNPCIDCMD(String name) {
        super(name);
        this.setPermission("op");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令");
        }else{
            NPC nearest = null;
            Player player = (Player)sender;
            HashMap<Double, Entity> m = new HashMap();
            double min = 0;
            for (Entity e : player.getLevel().getEntities()){
                if (e instanceof NPC) {
                    m.put(player.distance(e.getLocation()), e);
                    min = player.distance(e.getLocation());
                }
            }
            for (Map.Entry ee : m.entrySet()) {
                if (min > (double) ee.getKey()) {
                    min = (double) ee.getKey();
                }
            }
            nearest = (NPC) m.get(min);
            player.sendMessage("the nearest npc id is: " + nearest.id);
        }
        return true;
    }
}
