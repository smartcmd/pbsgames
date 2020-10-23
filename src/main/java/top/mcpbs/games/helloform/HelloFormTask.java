package top.mcpbs.games.helloform;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

public class HelloFormTask extends PluginTask {

    private final Player player;

    public HelloFormTask(Plugin owner,Player player) {
        super(owner);
        this.player = player;
    }

    @Override
    public void onRun(int i) {
        HelloForm.showForm(owner,player);
    }
}
