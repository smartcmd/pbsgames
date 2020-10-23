package top.mcpbs.games.duel;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

public class GameStartTask extends PluginTask {

    DuelRoom room;

    public GameStartTask(Plugin owner, DuelRoom room) {
        super(owner);
        this.room = room;
    }

    @Override
    public void onRun(int i) {
        room.waittime -= 1;
        for (Player player : room.waiting){
            player.sendTitle("","§e游戏即将开始！\n§a" + room.waittime);
        }
        this.test();
    }

    public void test(){
        if (room.waiting.size() < 2){
            for (Player player : room.waiting){
                player.sendTitle("§c人数不足");
                player.setSubtitle("§c已取消倒计时");
                room.waittime = 15;
                room.isStartChemical = false;
                this.cancel();
            }
        }
        if(room.waittime <= 0){
            room.gameStart();
            room.isStartChemical = false;
            this.cancel();
        }
    }
}
