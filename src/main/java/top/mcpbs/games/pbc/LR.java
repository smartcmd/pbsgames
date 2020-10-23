package top.mcpbs.games.pbc;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;

public class LR implements Listener {
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event){
        Config c = new Config(Main.plugin.getDataFolder() + "/屏蔽词.yml");
        for(Object ss : c.getKeys()){
            if(event.getMessage().contains((String)ss)){
                event.setMessage(event.getMessage().replace((String)ss,(String)c.get((String)ss)));
            }
        }
    }
}
