package top.mcpbs.games.rush;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

public class GameEndTask extends PluginTask {

    RushRoom room;

    public GameEndTask(Plugin owner, RushRoom room) {
        super(owner);
        this.room = room;
    }

    @Override
    public void onRun(int i) {
        room.gameEnd();
    }
}
