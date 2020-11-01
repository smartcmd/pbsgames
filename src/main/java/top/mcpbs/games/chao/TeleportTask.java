package top.mcpbs.games.chao;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

import java.util.ArrayList;

public class TeleportTask extends PluginTask {

    Player player;
    public TeleportTask(Plugin owner, Player player) {
        super(owner);
        this.player = player;
    }

    @Override
    public void onRun(int i) {
        if (player.isOnline()) {
            Item hub = Item.get(355,0,1);
            hub.setCustomName("退出等待");
            player.getInventory().setItem(2,hub);
            player.setGamemode(0);
            Chao.players.put(player, false);
            player.teleport(Chao.spawn);
        }
    }
}
