package top.mcpbs.games.hpshow;

import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;

public class LR implements Listener {
    public void onPlayerQuit(PlayerQuitEvent event){
        HPShow.hp.remove(event.getPlayer());
    }
}
