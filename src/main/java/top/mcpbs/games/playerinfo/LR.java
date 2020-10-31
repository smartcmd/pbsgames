package top.mcpbs.games.playerinfo;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;

public class LR implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        PlayerInfoTool.playerdata.remove(event.getPlayer());
    }
}
