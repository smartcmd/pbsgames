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
                            p.sendMessage("§b游戏 §7» §c[Duel]§e玩家 §a" + winner.getName() + "§e胜利了!");
                        }
                        winner.sendTitle("§a你胜利了!");
                        Score.addScore(winner,10);
                        Coin.addCoin(winner,2);
                        winner.getInventory().clearAll();
                    }
                    if (loser != null && loser.isOnline()){
                        loser.sendTitle("§c你输了!","§e再接再厉!");
                        Score.remScore(loser,5);
                        loser.getInventory().clearAll();
                    }
                    room.isend = true;
                    Server.getInstance().getScheduler().scheduleDelayedTask(new top.mcpbs.games.duel.GameEndTask(Main.plugin, room),20 * 5);
                }
            }
            if (room.isPlaying == true && room.playing.size() == 1 && room.isend == false){
                Player player = room.playing.get(0);
                player.sendMessage("§b游戏 §7» §e对方意外退出，本次游戏无收益...");
                player.getInventory().clearAll();
                room.isend = true;
                Server.getInstance().getScheduler().scheduleDelayedTask(new top.mcpbs.games.duel.GameEndTask(Main.plugin, room),20 * 5);
            }
        }
    }
}
