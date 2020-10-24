package top.mcpbs.games.duel;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.Main;
import top.mcpbs.games.playerinfo.coin.Coin;
import top.mcpbs.games.playerinfo.score.Score;

public class TestWinTask extends PluginTask {
    public TestWinTask(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for (DuelRoom room : DuelRoom.duelrooms.values()){
            if (room.isPlaying == true && room.playing.size() == 2 && room.isend == false){
                int alive = 0;
                for (Player player : room.playing){
                    if (player.getGamemode() == 0){
                        alive += 1;
                    }
                }
                if (alive == 1){
                    Player winner = null;
                    Player loser = null;
                    for (Player player : room.playing){
                        if (player.getGamemode() == 0){
                            winner = player;
                        }
                        if (player.getGamemode() == 3){
                            loser = player;
                        }
                    }
                    if (winner != null && winner.isOnline()){
                        for (Player p : room.playing){
                            p.sendMessage("§c[Duel]§e玩家 §a" + winner.getName() + "§e胜利了!");
                        }
                        winner.sendTitle("§a你胜利了!");
                        winner.sendMessage("§6你的收益: §escore+10 §bcoin+2");
                        Score.addScore(winner,10);
                        Coin.addCoin(winner,2);
                        winner.getInventory().clearAll();
                        Item hub = Item.get(355,0,1);
                        hub.setCustomName("返回主城");
                        Item again = Item.get(339,0,1);
                        again.setCustomName("再来一局");
                        winner.getInventory().setItem(2,hub);
                        winner.getInventory().setItem(5,again);
                    }
                    if (loser != null && loser.isOnline()){
                        loser.sendTitle("§c你输了!","§e再接再厉!");
                        loser.sendMessage("§e你失去了5分数!");
                        Score.remScore(loser,5);
                        loser.getInventory().clearAll();
                        Item hub = Item.get(355,0,1);
                        hub.setCustomName("返回主城");
                        Item again = Item.get(339,0,1);
                        again.setCustomName("再来一局");
                        loser.getInventory().setItem(2,hub);
                        loser.getInventory().setItem(5,again);
                    }
                    room.isend = true;
                    Server.getInstance().getScheduler().scheduleDelayedTask(new top.mcpbs.games.duel.GameEndTask(Main.plugin, room),20 * 15);
                }
            }
            if (room.isPlaying == true && room.playing.size() == 1 && room.isend == false){
                Player player = room.playing.get(0);
                player.sendMessage("§e对方意外退出，本次游戏无收益...");
                player.getInventory().clearAll();
                Item hub = Item.get(355,0,1);
                hub.setCustomName("返回主城");
                Item again = Item.get(339,0,1);
                again.setCustomName("再来一局");
                player.getInventory().setItem(2,hub);
                player.getInventory().setItem(5,again);
                room.isend = true;
                Server.getInstance().getScheduler().scheduleDelayedTask(new top.mcpbs.games.duel.GameEndTask(Main.plugin, room),20 * 15);
            }
        }
    }
}
