package top.mcpbs.games.duel;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

public class GameEndTask extends PluginTask {

    DuelRoom room;

    public GameEndTask(Plugin owner, DuelRoom room) {
        super(owner);
        this.room = room;
    }

    @Override
    public void onRun(int i) {
        room.gameEnd();
    }
}
