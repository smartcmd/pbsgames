package top.mcpbs.games.rush;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.Main;
import top.mcpbs.games.room.Room;
import top.mcpbs.games.util.DateUtil;

import java.util.ArrayList;

public class RushTask extends PluginTask {
    public RushTask(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for (RushRoom room : RushRoom.RushRooms.values()){
            if (room.isPlaying == false && room.waiting.size() == 2 && room.isStartChemical == false){
                Server.getInstance().getScheduler().scheduleRepeatingTask(new GameStartTask(Main.plugin,room),20 * 1);
                room.isStartChemical = true;
            }

            if (room.isPlaying == false){
                for (Player player : room.waiting){
                    if (room.waiting.size() == 1) {
                        player.sendActionBar("§a等待其他玩家加入...");
                    }
                    player.setHealth(player.getMaxHealth());
                    player.getFoodData().setLevel(20);
                    ArrayList l = new ArrayList();
                    l.clear();
                    l.add("§7#" + DateUtil.getDate("yyyy/MM/dd"));
                    l.add(" ");
                    l.add("地图名称");
                    l.add("§a" + Room.awaiting.get(player).mapname);
                    l.add("  ");
                    l.add("等待游戏开始...");
                    l.add("   ");
                    l.add("§eplay.mcpbs.top");
                    Main.s.showScoreboard(player, "§l§6战桥", l);
                    if (player.getY() < 90) {
                        player.teleport(room.pos1);
                    }
                }
            }
            if (room.isPlaying) {
                for (Player player : room.playing) {
                    if (player.isOnline()) {
                        player.setHealth(player.getMaxHealth());
                        player.getFoodData().setLevel(20);
                        if (player.getY() < 90) {
                            EntityLightning l = new EntityLightning(player.getChunk(), EntityLightning.getDefaultNBT(player.getLocation()));
                            l.setEffect(false);
                            l.spawnToAll();
                            player.teleport(room.pos.get(player));
                            player.sendTitle("", "§c你掉进了虚空");
                        }
                        if (!player.getInventory().contains(Item.get(24))) {
                            player.getInventory().addItem(Item.get(24, 0, 256));
                        }
                    }
                    ArrayList l = new ArrayList();
                    l.clear();
                    l.add("§7#" + DateUtil.getDate("yyyy/MM/dd"));
                    l.add(" ");
                    l.add("地图名称");
                    l.add("§a" + Room.aplaying.get(player).mapname);
                    l.add("  ");
                    l.add("你的得分");
                    l.add("§a" + ((RushRoom) Room.aplaying.get(player)).scores.get(player));
                    l.add("   ");
                    ArrayList<Player> tmp = (ArrayList) Room.aplaying.get(player).playing.clone();
                    tmp.remove(player);
                    l.add("对手");
                    l.add("§a" + tmp.get(0).getName());
                    l.add("对手得分");
                    l.add("§a" + ((RushRoom) Room.aplaying.get(player)).scores.get(tmp.get(0)) + " ");
                    l.add("    ");
                    l.add("§eplay.mcpbs.top");
                    Main.s.showScoreboard(player, "§l§e战桥", l);
                }
            }
        }
    }
}


