package top.mcpbs.games.name;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;

public class LR implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        NameTool.playernametag.remove(event.getPlayer());
        NameTool.playerdisplayname.remove(event.getPlayer());
    }
}
