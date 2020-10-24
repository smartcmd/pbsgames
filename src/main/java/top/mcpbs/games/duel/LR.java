package top.mcpbs.games.duel;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import top.mcpbs.games.room.Room;

public class LR implements Listener {
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event){
        if (event.getEntity() instanceof Player && Room.aplaying.containsKey(event.getEntity()) && Room.aplaying.get(event.getEntity()) instanceof DuelRoom){
            Player player = (Player)event.getEntity();
            if ((player.getHealth() - event.getFinalDamage()) < 1)
            {
                event.setCancelled();
                player.setGamemode(3);
                EntityLightning l = new EntityLightning(player.getChunk(), EntityLightning.getDefaultNBT(player.getPosition()));
                l.setEffect(false);
                l.spawnToAll();
            }
        }
    }

    @EventHandler
    public void onPLayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(Room.aplaying.containsKey(player) && Room.aplaying.get(player) instanceof DuelRoom){
            Room.aplaying.get(player).playerAccidentQuit(player);
        }
        if (Room.awaiting.containsKey(player) && Room.awaiting.get(player) instanceof DuelRoom){
            Room.awaiting.get(player).playerAccidentQuit(player);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (Room.aplaying.containsKey(event.getPlayer()) && Room.aplaying.get(event.getPlayer()) instanceof DuelRoom && !event.getBlock().getName().equals("Sandstone")){
            event.setCancelled();
        }
        if (Room.awaiting.containsKey(event.getPlayer()) && Room.awaiting.get(event.getPlayer()) instanceof DuelRoom ){
            event.setCancelled();
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        if(event.getMessage().equals("/hub") && Room.aplaying.containsKey(player) && Room.aplaying.get(player) instanceof DuelRoom && Room.aplaying.get(player).isend == false){
            Room.aplaying.get(player).playerAccidentQuit(player);
        }
        if (event.getMessage().equals("/hub") && Room.awaiting.containsKey(player) && Room.awaiting.get(player) instanceof DuelRoom){
            Room.awaiting.get(player).playerAccidentQuit(player);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (Room.aplaying.containsKey(event.getPlayer()) && Room.aplaying.get(event.getPlayer()) instanceof DuelRoom && Room.aplaying.get(event.getPlayer()).isend == true){
            if (event.getPlayer().getInventory().getItemInHand().getId() == 355){
                Server.getInstance().getCommandMap().dispatch(event.getPlayer(),"hub");
                Server.getInstance().getPluginManager().callEvent(new PlayerCommandPreprocessEvent(event.getPlayer(), "/hub"));
            }
            if (event.getPlayer().getInventory().getItemInHand().getId() == 339){
                Server.getInstance().getCommandMap().dispatch(event.getPlayer(),"hub");
                Server.getInstance().getPluginManager().callEvent(new PlayerCommandPreprocessEvent(event.getPlayer(), "/hub"));
                Server.getInstance().getCommandMap().dispatch(event.getPlayer(),((DuelRoom) Room.aplaying.get(event.getPlayer())).mode);
            }
        }
    }
}
