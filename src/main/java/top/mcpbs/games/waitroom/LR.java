package top.mcpbs.games.waitroom;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import top.mcpbs.games.room.Room;

public class LR implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (Room.awaiting.containsKey(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (Room.awaiting.containsKey(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (Room.awaiting.containsKey(event.getPlayer()) && event.getPlayer().getInventory().getItemInHand().getId() == 355) {
            Server.getInstance().getCommandMap().dispatch(event.getPlayer(),"hub");
            Server.getInstance().getPluginManager().callEvent(new PlayerCommandPreprocessEvent(event.getPlayer(), "/hub"));
        }
    }
}
