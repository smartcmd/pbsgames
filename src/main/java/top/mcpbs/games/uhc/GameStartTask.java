package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.designation.SetName;

public class GameStartTask extends PluginTask {

    UHCRoom room;
    public GameStartTask(Plugin owner, UHCRoom room) {
        super(owner);
        this.room = room;
    }

    @Override
    public void onRun(int i) {
        room.waittime -= 1;
        for (Player player : room.waiting){
            if (room.waittime > 15){
                player.sendTitle("","§e游戏即将开始！\n§a" + room.waittime);
            }
            if (room.waittime <= 15){
                if (room.waittime == 15){
                    room.Gameprestart();
                }
                player.sendTitle("","§e游戏即将开始！\n§a" + room.waittime + "\n§6加载世界中...");
            }
        }
        this.test();
    }

    public void test(){
        if (room.waiting.size() < 2){
            for (Player player : room.waiting){
                player.sendTitle("§c人数不足");
                player.setSubtitle("§c已取消倒计时");
                if (room.waittime <= 15){
                    room.waitRoom.joinWaitRoom(player);
                    SetName.ReloadToDefaultDesignation(player);
                    for (Team t : room.team){
                        t.player.clear();
                    }//清除队伍数据
                }
                room.waittime = 90;
                room.isStartChemical = false;
                this.cancel();
            }
        }
        if(room.waittime <= 0){
            room.isStartChemical = false;
            room.gameStart();
            this.cancel();
        }
    }
}

