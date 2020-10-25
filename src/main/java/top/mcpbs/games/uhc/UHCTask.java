package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.SetSpawnPositionPacket;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.Main;
import top.mcpbs.games.util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UHCTask extends PluginTask {
    public UHCTask(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for (UHCRoom room : UHCRoom.uhcrooms.values()) {
            if (room.isPlaying == false && room.waiting.size() >= 2 && room.isStartChemical == false) {//暂时2
                Server.getInstance().getScheduler().scheduleRepeatingTask(new GameStartTask(owner, room), 20 * 1);
                room.isStartChemical = true;
            }
            if (room.isPlaying == false) {
                for (Player player : room.waiting) {
                    if (room.waiting.size() < 2) {
                        player.sendActionBar("§a等待其他玩家加入...");
                    }
                    player.setHealth(player.getMaxHealth());
                    player.getFoodData().setLevel(20);
                    ArrayList l = new ArrayList();
                    l.add("§7#" + DateUtil.getDate("yyyy/MM/dd"));
                    l.add(" ");
                    l.add("等待游戏开始...");
                    l.add("需要至少2玩家");
                    l.add("  ");
                    l.add("等待玩家数量");
                    l.add("§a" + room.waiting.size() + "/32");
                    l.add("   ");
                    l.add("§eplay.mcpbs.top");
                    Main.s.showScoreboard(player, "§l§e极限生存冠军", l);
                }
            }
            if (room.isPlaying == true) {
                for (Player player : room.playing) {
                    ArrayList l = new ArrayList();
                    l.add("§7#" + DateUtil.getDate("yyyy/MM/dd"));
                    l.add(" ");
                    l.add("剩余时间: §a" + room.gametime + "s");
                    int alive = 0;
                    for (boolean b : room.isdead.values()) {
                        if (b == false) {
                            alive += 1;
                        }
                    }
                    l.add("存活: §a" + alive + "/" + room.playing.size());
                    Team team = room.playerteam.get(player);
                    l.add("  ");
                    l.add("你的队伍: " + team.teamname);
                    l.add("队伍成员");
                    for (Player player1 : team.player){
                        if (room.isdead.get(player1) == true){
                            l.add(" §7>>(Dead) " + player1.getName());
                        }else{
                            l.add(team.color + " >>" + player1.getName());
                        }
                    }
                    l.add("   ");
                    l.add("世界边界");
                    l.add("§a±" + room.boundary);
                    l.add("    ");
                    l.add("§eplay.mcpbs.top");
                    Main.s.showScoreboard(player, "§l§e极限生存冠军", l);
                    if (player.getGamemode() == 0) {
                        double x = Math.abs(player.getX());
                        double z = Math.abs(player.getZ());
                        if (x > room.boundary || z > room.boundary) {
                            player.sendTitle("", "§c你超出边界了，快回来！");
                            Effect wither = Effect.getEffect(Effect.WITHER);
                            wither.setAmplifier(1);
                            wither.setDuration(40);
                            player.addEffect(wither);
                        }
                        if (room.boundary - x <= 25 || room.boundary - z <= 25){
                            player.sendTitle("","§c接近边界!");
                        }
                    }
                    if (player.getInventory().getItemInHand().getId() == 345) {
                        HashMap<Double, Player> m = new HashMap();
                        double min = 0;
                        for (Player player1 : room.playing) {
                            if (player1 != player && player1.getGamemode() == 0 && !team.player.contains(player1)) {
                                m.put(player.distance(player1.getLocation()), player1);
                                min = player.distance(player1.getLocation());
                            }
                        }
                        for (Map.Entry e : m.entrySet()) {
                            if (min > (double) e.getKey()) {
                                min = (double) e.getKey();
                            }
                        }
                        Player nearestplayer = m.get(min);
                        if (nearestplayer != null) {
                            player.sendActionBar("§e§l最近的敌对玩家: " + room.playerteam.get(nearestplayer).color + nearestplayer.getName() + "\n§a距离: §b" + String.format("%.3f", min) + " §am");
                            SetSpawnPositionPacket pk = new SetSpawnPositionPacket();
                            pk.spawnType = 1;
                            Position spawn = nearestplayer.getPosition();
                            pk.x = spawn.getFloorX();
                            pk.y = spawn.getFloorY();
                            pk.z = spawn.getFloorZ();
                            player.dataPacket(pk);
                        }
                    }
                }
            }
        }
    }
}
