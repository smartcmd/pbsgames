package top.mcpbs.games.rush;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.math.Vector3;
import top.mcpbs.games.duel.DuelRoom;
import top.mcpbs.games.room.Room;

import java.util.ArrayList;

public class LR implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(Room.aplaying.containsKey(player) && Room.aplaying.get(player) instanceof RushRoom){
            Room.aplaying.get(player).playerAccidentQuit(player);
        }
        if (Room.awaiting.containsKey(player) && Room.awaiting.get(player) instanceof RushRoom){
            Room.awaiting.get(player).playerAccidentQuit(player);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (Room.awaiting.containsKey(event.getPlayer()) && Room.awaiting.get(event.getPlayer()) instanceof RushRoom){
            event.setCancelled();
        }
        if (Room.aplaying.get(event.getPlayer()) instanceof RushRoom && event.getBlock().getX() == ((RushRoom)Room.aplaying.get(event.getPlayer())).pos.get(event.getPlayer()).x && event.getBlock().getId() == 92){
            event.getPlayer().sendMessage("§c不可以挖自己的蛋糕哦!");
            event.setCancelled();
        }else if (event.getBlock().getId() == 92 && Room.aplaying.containsKey(event.getPlayer()) && Room.aplaying.get(event.getPlayer()) instanceof RushRoom){
            event.getPlayer().sendTitle("","§a你获得了一分!");
            ArrayList tmpp = (ArrayList) Room.aplaying.get(event.getPlayer()).playing.clone();
            tmpp.remove(event.getPlayer());
            ((Player)tmpp.get(0)).sendTitle("","§e对手获得了一分!");
            for (Player player : Room.aplaying.get(event.getPlayer()).playing){
                EntityLightning l = new EntityLightning(player.getChunk(), EntityLightning.getDefaultNBT(event.getBlock().getLocation()));
                l.setEffect(false);
                l.spawnToAll();
                player.sendMessage("§a恭喜玩家 §e" + event.getPlayer().getName() + " §a获得一分!");
                player.teleport(((RushRoom) Room.aplaying.get(player)).pos.get(player));
                Room.aplaying.get(event.getPlayer()).roomlevel.setBlock(((RushRoom) Room.aplaying.get(player)).pos.get(player), Block.get(92));
            }
            ((RushRoom) Room.aplaying.get(event.getPlayer())).scores.put(event.getPlayer(),((RushRoom) Room.aplaying.get(event.getPlayer())).scores.get(event.getPlayer()) + 1);
            ClearBlocks.clearBlocks(((RushRoom) Room.aplaying.get(event.getPlayer())));
        }
        if (Room.aplaying.containsKey(event.getPlayer()) && Room.aplaying.get(event.getPlayer()) instanceof RushRoom && !event.getBlock().getName().equals("Sandstone")){
            event.setCancelled();
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (Room.aplaying.containsKey(player) && Room.aplaying.get(player) instanceof RushRoom){
            ((RushRoom) Room.aplaying.get(player)).rushblocks.put(new Vector3(event.getBlock().x,event.getBlock().y,event.getBlock().z),0);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player && Room.aplaying.get(event.getEntity()) instanceof RushRoom){
            Player player = (Player)event.getEntity();
            Player damager = (Player)event.getDamager();
            if (damager.getInventory().getItemInHand().getId() == 280){
                event.setKnockBack(0.5F);
            }
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        if (event.getMessage().equals("/hub") && Room.aplaying.containsKey(event.getPlayer()) && Room.aplaying.get(event.getPlayer()) instanceof RushRoom){
            Room.aplaying.get(player).playerAccidentQuit(player);
        }
        if (event.getMessage().equals("/hub") && Room.awaiting.containsKey(event.getPlayer()) && Room.awaiting.get(event.getPlayer()) instanceof RushRoom){
            Room.awaiting.get(player).playerAccidentQuit(player);
        }
    }

    @EventHandler
    public void onPlayerFallDamage(EntityDamageEvent event) {
        if (Room.aplaying.get(event.getEntity()) instanceof RushRoom && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled();
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (Room.aplaying.containsKey(event.getPlayer()) && Room.aplaying.get(event.getPlayer()) instanceof RushRoom && Room.aplaying.get(event.getPlayer()).isend == true){
            if (event.getPlayer().getInventory().getItemInHand().getId() == 355){
                Server.getInstance().getCommandMap().dispatch(event.getPlayer(),"hub");
                Server.getInstance().getPluginManager().callEvent(new PlayerCommandPreprocessEvent(event.getPlayer(), "/hub"));
            }
            if (event.getPlayer().getInventory().getItemInHand().getId() == 339){
                Server.getInstance().getCommandMap().dispatch(event.getPlayer(),"hub");
                Server.getInstance().getPluginManager().callEvent(new PlayerCommandPreprocessEvent(event.getPlayer(), "/hub"));
                Server.getInstance().getPluginManager().callEvent(new PlayerCommandPreprocessEvent(event.getPlayer(), "/jonrush"));
            }
        }
    }
}
