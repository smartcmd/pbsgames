package top.mcpbs.games.helloform;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;

import java.util.HashMap;

public class LR implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Config config = new Config(Main.plugin.getDataFolder() + "/helloform.yml");
        HashMap m = (HashMap)config.get("HelloForm");
        Server.getInstance().getScheduler().scheduleDelayedTask(new HelloFormTask(Main.plugin, event.getPlayer()),20 * (int)m.get("TaskTime"));
    }

}
