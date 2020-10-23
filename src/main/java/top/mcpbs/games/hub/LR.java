package top.mcpbs.games.hub;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import top.mcpbs.games.Main;

public class LR implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Server.getInstance().getScheduler().scheduleDelayedTask(new ReturnHubOnJoinTask(Main.plugin, event),5);
    }
}
