package top.mcpbs.games.duel;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.Main;
import top.mcpbs.games.room.Room;
import top.mcpbs.games.util.DateUtil;

import java.util.ArrayList;

public class DuelTask extends PluginTask {
    public DuelTask(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for (DuelRoom room : DuelRoom.duelrooms.values()) {
            if (room.isPlaying == false && room.waiting.size() == 2 && room.isStartChemical == false) {
                Server.getInstance().getScheduler().scheduleRepeatingTask(new GameStartTask(Main.plugin, room), 20 * 1);
                room.isStartChemical = true;
            }

            if (room.isPlaying == false) {
                for (Player player : room.waiting) {
                    if (room.waiting.size() == 1) {
                        player.sendActionBar("§a等待其他玩家加入...");
                    }
                    player.setHealth(player.getMaxHealth());
                    player.getFoodData().setLevel(20);
                    ArrayList l = new ArrayList();
                    l.add("§7#" + DateUtil.getDate("yyyy/MM/dd"));
                    l.add(" ");
                    l.add("地图名称");
                    l.add("§a" + room.mapname);
                    l.add("  ");
                    l.add("等待游戏开始...");
                    l.add("   ");
                    l.add("§eplay.mcpbs.top");
                    Main.s.showScoreboard(player, room.modename, l);
                }
            }

            if (room.isPlaying == true) {
                for (Player player : room.playing) {
                    ArrayList<Player> tmp = (ArrayList<Player>) room.playing.clone();
                    tmp.remove(player);
                    ArrayList l = new ArrayList();
                    l.add("§7#" + DateUtil.getDate("yyyy/MM/dd"));
                    l.add(" ");
                    l.add("地图名称");
                    l.add("§a" + Room.aplaying.get(player).mapname);
                    l.add("对手");
                    l.add("§a" + tmp.get(0).getName());
                    l.add("  ");
                    l.add("§eplay.mcpbs.top");
                    Main.s.showScoreboard(player, room.modename, l);
                }
            }
        }
    }
}
