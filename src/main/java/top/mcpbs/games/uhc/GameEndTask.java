package top.mcpbs.games.uhc;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

public class GameEndTask extends PluginTask {
    UHCRoom room;

    public GameEndTask(Plugin owner, UHCRoom room) {
        super(owner);
        this.room = room;
    }

    @Override
    public void onRun(int i) {
        room.gameEnd();
    }
}
