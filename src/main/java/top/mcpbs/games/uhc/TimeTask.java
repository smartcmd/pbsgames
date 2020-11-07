package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

public class TimeTask extends PluginTask {

    public TimeTask(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for (UHCRoom room : UHCRoom.uhcrooms.values()){
            if (room.isPlaying == true && room.gametime > 0){
                room.gametime -= 1;
            }
            if (room.isPlaying == true && room.gametime <= (15 * 60 + 75) && room.gametime != 0){
                if (room.gametime == (15 * 60 + 75)){
                    for (Player player : room.roomlevel.getPlayers().values()){
                        player.sendTitle("§l§c争斗阶段§e已开启!","§l§6击杀你看到的敌人！" + "\n§l§c世界边界开始缩小！");
                        player.sendMessage("§c争斗阶段§e已开启!§6击杀你看到的敌人！§c世界边界开始缩小！");
                    }
                }
                room.boundary -= 1;
            }
        }
    }
}
