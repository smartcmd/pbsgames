package top.mcpbs.games.playerinfo;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import top.mcpbs.games.playerinfo.coin.Coin;
import top.mcpbs.games.playerinfo.diamond.Diamond;
import top.mcpbs.games.playerinfo.score.Score;

public class LR implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Diamond.diamond.remove(event.getPlayer());
        Score.score.remove(event.getPlayer());
        Coin.coin.remove(event.getPlayer());
    }
}
