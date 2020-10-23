package top.mcpbs.games.hub;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.Main;

public class ReturnHubOnJoinTask extends PluginTask {

    PlayerJoinEvent event;

    public ReturnHubOnJoinTask(Plugin owner, PlayerJoinEvent event) {
        super(owner);
        this.event = event;
    }

    @Override
    public void onRun(int i) {
        this.r2(event.getPlayer());
    }

    public void r2(Player player){
        player.setGamemode(0);
        player.teleport(Main.lobby);
    }
}
