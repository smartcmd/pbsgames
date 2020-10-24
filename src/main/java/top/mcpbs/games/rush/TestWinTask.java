package top.mcpbs.games.rush;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.Main;
import top.mcpbs.games.playerinfo.coin.Coin;
import top.mcpbs.games.playerinfo.score.Score;

import java.util.ArrayList;

public class TestWinTask extends PluginTask {
    public TestWinTask(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for(RushRoom room : RushRoom.RushRooms.values()) {
            for (Player player : room.playing) {
                if (room.playing.size() == 2 && room.scores.get(player) >= 5 && room.isend == false) {
                    for (Player p : room.playing) {
                        p.sendMessage("§c[战桥]§e玩家 §a" + player.getName() + "§e胜利了!");
                    }
                    player.sendTitle("§a你胜利了!");
                    player.sendMessage("§6你的收益: §escore+10 §bcoin+2");
                    Score.addScore(player, 10);
                    Coin.addCoin(player, 2);

                    Item hub = Item.get(355,0,1);
                    hub.setCustomName("返回主城");
                    Item again = Item.get(339,0,1);
                    again.setCustomName("再来一局");

                    player.getInventory().addItem(hub);
                    player.getInventory().addItem(again);

                    ArrayList<Player> tmp = (ArrayList<Player>) room.playing.clone();
                    tmp.remove(player);
                    tmp.get(0).sendTitle("§c你输了!", "§e再接再厉!");
                    tmp.get(0).sendMessage("§e你失去了5分数！");
                    Score.remScore(tmp.get(0), 5);
                    tmp.get(0).setGamemode(3);

                    tmp.get(0).getInventory().clearAll();
                    tmp.get(0).getInventory().addItem(hub);
                    tmp.get(0).getInventory().addItem(again);

                    Server.getInstance().getScheduler().scheduleDelayedTask(new GameEndTask(Main.plugin, room),20 * 15);
                    room.isend = true;
                }
            }
            if (room.playing.size() == 1 && room.isPlaying){
                Player player = room.playing.get(0);
                player.sendMessage("§e对方意外退出，本次游戏无收益...");
                room.isend = true;
                Server.getInstance().getScheduler().scheduleDelayedTask(new GameEndTask(Main.plugin, room),20 * 15);
            }
        }
    }
}
